package cn.libv.todo.ui.home;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import cn.libv.todo.R;


/*Todo数据的适配器*/
public class TodoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private AppCompatActivity context;
    private List<Todo> dataList = new ArrayList<>();
    private static final int TODO = 1;

    public void addAllData(List<Todo> dataList) {
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return TODO;
    }

    public void clearData() {
        this.dataList.clear();
        notifyDataSetChanged();
    }

    //Todo类型数据的绑定界面
    public static class  TodoHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView time,content;
        CardView cardView;
        ImageView imageView;

        TodoHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.Todo);
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Todo feedback = dataList.get(position);
        ((TodoHolder) holder).checkBox.setChecked(false);
        ((TodoHolder) holder).content.setText(feedback.getContent());
        ((TodoHolder) holder).time.setText(feedback.getTime());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}