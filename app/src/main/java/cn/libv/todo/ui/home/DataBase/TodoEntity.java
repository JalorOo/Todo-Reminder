package cn.libv.todo.ui.home.DataBase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//数据库中Todo实体类
@Entity
public class TodoEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String content;

    private String time;

    private long uid;

    private long cid;//该数据在服务器中的ID（Cloud ID）

    public TodoEntity(String content, String time, long uid, long cid){
        this.content = content;
        this.time = time;
        this.uid = uid;
        this.cid = cid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }
}
