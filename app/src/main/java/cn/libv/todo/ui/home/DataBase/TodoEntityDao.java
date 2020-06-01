package cn.libv.todo.ui.home.DataBase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//数据库插入
@Dao
public interface TodoEntityDao {
    @Insert
    void InsertTodoEntity(TodoEntity... todoEntities);

    @Update
    void updateTodoEntity(TodoEntity... todoEntities);

    @Query("DELETE FROM TODOENTITY")
    void deleteAllTodoEntity();

    @Query("SELECT * FROM TODOENTITY ORDER BY TIME DESC")
    List<TodoEntity> getAllTodoEntity();
}
