package cn.libv.todo.ui.home.DataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//数据库事例
@Database(entities = {TodoEntity.class},version = 1,exportSchema = false)
public abstract class TodoEntityDataBase extends RoomDatabase {

    private static TodoEntityDataBase INSTANCE;

    public static synchronized TodoEntityDataBase getDataBase(Context context){
        if(INSTANCE==null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TodoEntityDataBase.class,"todo_database").build();
        }
        return INSTANCE;
    }

    public abstract TodoEntityDao getTodoEntityDao();
}
