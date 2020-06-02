package cn.libv.todo.ui.home.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/*
* 数据库插入接口
* 使用了Room提供的注解
*/
@Dao
public interface TodoEntityDao {
    @Insert
    void InsertTodoEntity(TodoEntity... todoEntities);

    @Update
    void updateTodoEntity(TodoEntity... todoEntities);

    @Query("DELETE FROM TODOENTITY")
    void deleteAllTodoEntity();//删除所有数据

    @Query("SELECT * FROM TODOENTITY ORDER BY TIME DESC")
    List<TodoEntity> getAllTodoEntity();//查找数据
}
