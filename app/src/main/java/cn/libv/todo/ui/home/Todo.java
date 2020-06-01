package cn.libv.todo.ui.home;

//{"content":"1","id":1,"time":"2020-05-01","uid":1}

public class Todo {
    private long id;

    private String content;

    private String time;

    private long uid;

    private long oid;

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Todo(){}

    public Todo(long id, String content, String time, long uid,long oid) {
        this.id = id;
        this.content = content;
        this.time = time;
        this.uid = uid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }
}
