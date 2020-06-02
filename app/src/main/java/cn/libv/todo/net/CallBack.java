package cn.libv.todo.net;

//接受服务器返回信息的反馈类
public class CallBack {
    private String info;
    private int code;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
