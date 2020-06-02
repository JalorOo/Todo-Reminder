package cn.libv.todo.ui.home;

//数据类（从服务器中解析Json变为JavaBean）
public class Todo {
    private long id;

    private String content;//内容

    private String time;//提醒时间

    private long uid;//被提醒用户的ID（User ID）

    private long oid;//提醒用户的ID（original ID）

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
