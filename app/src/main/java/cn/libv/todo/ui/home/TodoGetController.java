package cn.libv.todo.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.libv.todo.Constant;
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

    private SharePrefenrenceUtils sharePrefenrenceUtils;

    HomeViewModel homeViewModel;

    int SEND = 1;

    int code;

    String s;

    List<Todo> list;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==SEND){
                if (code==200){
                    homeViewModel.setTodoList(list);
                }
            }
        }
    };

    TodoGetController(HomeViewModel homeViewModel, Context context){
        sharePrefenrenceUtils = new SharePrefenrenceUtils(context, Constant.name);
        this.homeViewModel = homeViewModel;
    }

    //从服务器上获取数据
    void getTodoData(){
        String id = sharePrefenrenceUtils.QueryShare("id","");
        if (id.equals("")){
            s = "login again please";
            handler.sendEmptyMessage(SEND);
            return;
        }
        //1.创建OkHttpClient对象
        String url = "https://sunshinesudio.club:8020/todo/query?";
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("id", id)
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
                        s = e.toString();
                        handler.sendEmptyMessage(SEND);
                    }
                    //请求成功执行的方法
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String data = response.body().string();
                        Gson gson = new Gson();//创建Gson对象
                        JsonParser jsonParser = new JsonParser();
                        try {
                            JsonArray jsonElements = jsonParser.parse(data).getAsJsonArray();//获取JsonArray对象
                            list = new ArrayList<>();
                            for (JsonElement bean : jsonElements) {
                                Todo todo = gson.fromJson(bean, Todo.class);//解析
                                list.add(todo);
                            }
                            code = 200;
                        } catch (Exception e) {
                            code = 404;
                            s = e.toString();
                        }
                        handler.sendEmptyMessage(SEND);
                    }
                });
            }
        }.start();
    }
}
