package cn.libv.todo.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

import cn.libv.todo.R;


/*Todo数据的适配器*/
public class TodoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private AppCompatActivity context;
    private List<Todo> dataList = new ArrayList<>();

    public void addAllData(List<Todo> dataList) {//加载数据
        try {
            this.dataList.addAll(dataList);
        }catch (Exception e){
            e.printStackTrace();
        }
        notifyDataSetChanged();//刷新界面
    }

    //item接界面
    public static class  TodoHolder extends RecyclerView.ViewHolder {
        TextView time,content;
        CardView cardView;
        ImageView imageView;

        TodoHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.TodoDetail);
            time = itemView.findViewById(R.id.todo_time);
            cardView = itemView.findViewById(R.id.card);
            imageView = itemView.findViewById(R.id.clock);
        }
    }


    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = (AppCompatActivity) parent.getContext();
        }
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo_list, parent, false);
        return new TodoHolder(v);
    }


    @Override
    public void onBindViewHolder(@NotNull final RecyclerView.ViewHolder holder, final int position) {
        //在界面中数据
        final Todo feedback = dataList.get(position);
        ((TodoHolder) holder).content.setText(feedback.getContent());
        ((TodoHolder) holder).time.setText(feedback.getTime());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}