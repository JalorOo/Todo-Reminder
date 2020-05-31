package cn.libv.todo.ui.send;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.io.IOException;

import cn.libv.todo.net.CallBack;
import cn.libv.todo.ui.home.Todo;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class SendController {

    Todo todo;

    int SEND = 1;

    String s;

    SendFragment sendFragment;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==SEND){
                sendFragment.Toast(s);
            }
        }
    };

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    void send(SendFragment sendFragment){
        this.sendFragment = sendFragment;
        //1.创建OkHttpClient对象
        String url = "https://sunshinesudio.club:8020/todo/add?";
        OkHttpClient okHttpClient = new OkHttpClient();

        RequestBody requestBody = new FormBody.Builder()
                .add("uid", String.valueOf(todo.getUid()))
                .add("content",todo.getContent())
                .add("time",todo.getTime())
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
                        CallBack callBack = gson.fromJson(data, CallBack.class);//解析
                        if (callBack.getCode()==200){
                            s = "success";
                        } else {
                            s = callBack.getInfo();
                        }
                        handler.sendEmptyMessage(SEND);
                    }
                });
            }
        }.start();
    }
}
