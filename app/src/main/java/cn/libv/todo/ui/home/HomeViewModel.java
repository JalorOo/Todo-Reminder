package cn.libv.todo.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Todo>> todoList;

    public HomeViewModel() {
        todoList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Todo>> getTodoList() {
        return todoList;
    }

    public void setTodoList(List<Todo> todoList) {
        this.todoList.setValue(todoList);
    }
}