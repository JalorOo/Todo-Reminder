package cn.libv.todo.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.libv.todo.Constant;
import cn.libv.todo.net.CallBack;
import cn.libv.todo.ui.home.DataBase.TodoEntity;
import cn.libv.todo.ui.home.DataBase.TodoEntityDao;
import cn.libv.todo.ui.home.DataBase.TodoEntityDataBase;
import cn.libv.todo.utils.SharePrefenrenceUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*从服务器中获取数据*/
class TodoGetController {

    private SharePrefenrenceUtils sharePrefenrenceUtils;//缓存

    private HomeViewModel homeViewModel;

    private final int SHOW = 1;//展示数据标志

    private final int INFO = 2;//标志

    private String info;//服务器返回的信号

    private List<Todo> list;//数据链表

    private TodoEntityDataBase todoEntityDataBase;

    private TodoEntityDao todoEntityDao;

    private Context context;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==SHOW){
                homeViewModel.setTodoList(list);
            }else if (msg.what==INFO){
                Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
            }
        }
    };

    TodoGetController(HomeViewModel homeViewModel, final Context context){
        this.context = context.getApplicationContext();
        sharePrefenrenceUtils = new SharePrefenrenceUtils(context, Constant.USER);
        this.homeViewModel = homeViewModel;
        new Thread(){
            @Override
            public void run() {
                super.run();
                todoEntityDataBase = TodoEntityDataBase.getDataBase(context);//获得数据库实例
                todoEntityDao = todoEntityDataBase.getTodoEntityDao();//获得操作接口
            }
        }.start();
    }

    //从本地数据库获得数据
    void getTodoDataFromDataBase(){
        List<TodoEntity> todoList = todoEntityDao.getAllTodoEntity();
        list.clear();
        for (TodoEntity todo: todoList) {
            Todo t = new Todo(todo.getCid(),todo.getContent(),todo.getTime(),todo.getUid(),todo.getCid());
            list.add(t);
        }
        handler.sendEmptyMessage(SHOW);//展示数据
    }

    //从服务器上获取数据
    void getTodoData(){
        String id = sharePrefenrenceUtils.QueryShare("id","");
        String name = sharePrefenrenceUtils.QueryShare(Constant.NAME,"");
        String password = sharePrefenrenceUtils.QueryShare(Constant.PASSWORD,"");
        if (id.equals("")){//若用户不存在
            info = "login again please";
            handler.sendEmptyMessage(SHOW);
            return;
        }
        //1.创建OkHttpClient对象
        String url = "https://sunshinesudio.club:8020/todo/query?";
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("name",name)
                .add("password",password)
                .add("id", id)
                .build();

        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)//默认就是GET请求，可以不写
                .build();
        //3.创建一个call对象,参数就是Request请求对象
        final Call call = okHttpClient.newCall(request);//发送post请求
        //4.请求加入调度，重写回调方法
        new Thread(){
            @Override
            public void run() {
                super.run();
                call.enqueue(new Callback() {
                    //请求失败执行的方法
                    @Override
                    public void onFailure(Call call, IOException e) {
                        list = new ArrayList<>();
                        getTodoDataFromDataBase();//链接失败则从本地数据库中获取
                    }
                    //请求成功执行的方法
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String data = response.body().string();
                        Gson gson = new Gson();//创建Gson对象
                        JsonParser jsonParser = new JsonParser();
                        todoEntityDao.deleteAllTodoEntity();//清空本地数据库
                        try {
                            JsonArray jsonElements = jsonParser.parse(data).getAsJsonArray();//获取JsonArray对象
                            list = new ArrayList<>();
                            for (JsonElement bean : jsonElements) {
                                Todo todo = gson.fromJson(bean, Todo.class);//解析
                                TodoEntity todoEntity = new TodoEntity(todo.getContent()+" fromUserID:"+todo.getOid(),todo.getTime(),todo.getUid(),todo.getId());
                                todoEntityDao.InsertTodoEntity(todoEntity);//向数据库中插入数据
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getTodoDataFromDataBase();//获得本地数据
                    }
                });
            }
        }.start();
    }

    //删除服务器上的数据
    void deleteData(final int idx){
        String id = String.valueOf(list.get(idx).getId());
        //获取删除数据人的信息
        String name = sharePrefenrenceUtils.QueryShare(Constant.NAME,"");
        String password = sharePrefenrenceUtils.QueryShare(Constant.PASSWORD,"");
        if (name.equals("")||password.equals("")){
            info = "fail,please login again";
            handler.sendEmptyMessage(INFO);//若用户不存在则进行提醒
            return;
        }
        //1.创建OkHttpClient对象
        String url = "https://sunshinesudio.club:8020/todo/delete?";
        OkHttpClient okHttpClient = new OkHttpClient();

        final RequestBody requestBody = new FormBody.Builder()
                .add("id", id)
                .add("name",name)
                .add("password",password)
                .build();

        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)//默认就是GET请求，可以不写
                .build();
        //3.创建一个call对象,参数就是Request请求对象
        final Call call = okHttpClient.newCall(request);
        //4.请求加入调度，重写回调方法
        new Thread(){
            @Override
            public void run() {
                super.run();
                call.enqueue(new Callback() {
                    //请求失败执行的方法
                    @Override
                    public void onFailure(Call call, IOException e) {
                        info = e.toString();
                        handler.sendEmptyMessage(INFO);//展示错误信息
                    }
                    //请求成功执行的方法
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String data = response.body().string();
                        Gson gson = new Gson();//创建Gson对象
                        CallBack callBack = gson.fromJson(data, CallBack.class);//解析
                        if (callBack.getCode()==200){
                            info = "delete success";
                        } else {
                            info = "fail,please check your network or refresh";
                        }
                        handler.sendEmptyMessage(INFO);//展示信息
                    }
                });
            }
        }.start();
    }
}
