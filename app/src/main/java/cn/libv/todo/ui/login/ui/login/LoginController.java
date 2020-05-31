package cn.libv.todo.ui.login.ui.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;

import cn.libv.todo.Constant;
import cn.libv.todo.net.CallBack;
import cn.libv.todo.utils.SharePrefenrenceUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class LoginController {

    SharePrefenrenceUtils sharePrefenrenceUtils;

    Context context;

    LoginController(Context context){
        this.context = context;
        sharePrefenrenceUtils = new SharePrefenrenceUtils(context, Constant.name);
    }

    int SEND = 1;
    String s;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==SEND){
                Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
            }
        }
    };

    void login(final String name , final String password){
        //1.创建OkHttpClient对象
        String url = "https://sunshinesudio.club:8020/user/login?";
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("name", name)
                .add("password", password)
                .build();

        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)//默认就是GET请求，可以不写
                .build();
        //3.创建一个call对象,参数就是Request请求对象
        final Call call = okHttpClient.newCall(request);
        Log.v(getClass().getName(),"login");
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
                        Log.v(getClass().getName(),"e:"+e.toString());
                        handler.sendEmptyMessage(SEND);
                    }
                    //请求成功执行的方法
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String data = response.body().string();
                        Gson gson = new Gson();//创建Gson对象
                        CallBack callBack = gson.fromJson(data, CallBack.class);//解析
                        Log.v(getClass().getName(),"info:"+callBack.getCode()+"/"+callBack.getInfo());
                        if (callBack.getCode()==200){
                            s = "success";
                            sharePrefenrenceUtils.setShare("name",name);
                            sharePrefenrenceUtils.setShare("password",password);
                            sharePrefenrenceUtils.setShare("id",callBack.getInfo());
                            handler.sendEmptyMessage(SEND);
                        } else if (callBack.getCode()==402) {
                            register(name,password);
                        } else {
                            s = callBack.getInfo();
                            handler.sendEmptyMessage(SEND);
                        }
                    }
                });
            }
        }.start();
    }

    void register(final String name , final String password){
        //1.创建OkHttpClient对象
        String url = "https://sunshinesudio.club:8020/user/register?";
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("name", name)
                .add("password", password)
                .build();

        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)//默认就是GET请求，可以不写
                .build();
        //3.创建一个call对象,参数就是Request请求对象
        final Call call = okHttpClient.newCall(request);
        //4.请求加入调度，重写回调方法
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
                CallBack callBack = gson.fromJson(data, CallBack.class);//解析
                s = callBack.getInfo();
                if (callBack.getCode()==200){
                    sharePrefenrenceUtils.setShare("name",name);
                    sharePrefenrenceUtils.setShare("password",password);
                }
                handler.sendEmptyMessage(SEND);
            }
        });
    }
}
