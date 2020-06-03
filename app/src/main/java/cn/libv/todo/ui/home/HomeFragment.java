package cn.libv.todo.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.libv.todo.R;

/*
* 主界面
* */
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;//保存这个界面所需要的数据

    private TodoGetController controller;//数据接收器

    private TodoAdapter adapter;//recyclerView界面的适配器

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.Todo_recyclerView);
        adapter = new TodoAdapter();
        recyclerView.setAdapter(adapter);//界面与适配器进行绑定
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//设置方向
        ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {//设置手势

            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                //首先回调的方法,返回int表示是否监听该方向
                int dragFlag = 0;//拖拽
                int swipeFlag = ItemTouchHelper.LEFT;//侧滑删除
                return makeMovementFlags(dragFlag, swipeFlag);
            }

            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                controller.deleteData(viewHolder.getAdapterPosition());//删除数据
                adapter.delete(viewHolder.getAdapterPosition());
            }

            @Override
            public void onMoved(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, int fromPos, @NonNull RecyclerView.ViewHolder target, int toPos, int x, int y) {
                super.onMoved(recyclerView, viewHolder, fromPos, target, toPos, x, y);
            }
        };
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);//手势与界面关联
        homeViewModel.getTodoList().observe(this, new Observer<List<Todo>>() {
            @Override
            public void onChanged(List<Todo> todos) {
                adapter.addAllData(todos);//监听数据变化并加载数据
            }
        });
        controller = new TodoGetController(homeViewModel,getContext());
        controller.getTodoData();//获得数据
        return root;
    }
}