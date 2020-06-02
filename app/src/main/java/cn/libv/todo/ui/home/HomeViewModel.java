package cn.libv.todo.ui.home;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Todo>> todoList;//todo数据

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