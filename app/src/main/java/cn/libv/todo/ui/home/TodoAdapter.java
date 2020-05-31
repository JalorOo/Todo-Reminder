package cn.libv.todo.ui.home;

import android.annotation.SuppressLint;
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
    private boolean isDeep;

    public TodoAdapter(boolean isDeep){
        this.isDeep = isDeep;
    }

    public void addAllData(List<Todo> dataList) {
        this.dataList.addAll(dataList);
        Collections.sort(this.dataList, new Comparator<Todo>() {
            @Override
            public int compare(Todo t1, Todo t2) {//按时间先后排序，已经完成的排后面
                return Long.compare(getStringToDate(t1.getTime(),"yyyy-MM-dd HH:mm")/1000000,getStringToDate(t2.getTime(),"yyyy-MM-dd HH:mm")/1000000);
            }
        });
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return TODO;
    }

    /*将字符串转为时间戳*/
    private static long getStringToDate(String dateString, String pattern) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try{
            date = dateFormat.parse(dateString);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public void clearData() {
        this.dataList.clear();
        notifyDataSetChanged();
    }


//    public void delete(int adapterPosition) {
//        if (adapterPosition >= dataList.size()||adapterPosition<0){
//            return;
//        }
//        MarkDate feedback = dataList.get(adapterPosition);
//        try {
//            CalendarReminderUtils.deleteCalendarEvent(context, feedback.getContent());
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        EventBus.getDefault().post(new Msg("TodoDelete",adapterPosition,feedback.getId(),"",feedback.getContent(),feedback.getTime(),"","",0,""));
//        ShowToast(context,context.getResources().getString(R.string.delete_success));
//        dataList.remove(adapterPosition);
//        notifyDataSetChanged();
//    }

//    public void changeData(int position, int ID,String title, String content, String data,String onlineID,int isOnline) {
//        /*通过查询道ID所在的位置进行更新数据*/
//        for (int i=0;i<dataList.size();i++) {
//            if (dataList.get(i).getId() == ID) {//找到了ID
//                MarkDate entity = dataList.get(i);
//                entity.setContent(content);
//                entity.setTime(data);
//                entity.setOnline(isOnline);
//                notifyDataSetChanged();
//                break;
//            }
//        }
//    }

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
        if (holder instanceof TodoHolder){
            ((TodoHolder) holder).checkBox.setChecked(false);
            ((TodoHolder) holder).content.setText(feedback.getContent());
            ((TodoHolder) holder).time.setText(feedback.getTime());
            ((TodoHolder) holder).content.invalidate();
//            ((TodoHolder) holder).checkBox.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(final View v) {
//                    if (feedback.isOnline() == 0) {//若是未完成的，则现在是完成了需要删除
//                        try {
//                            CalendarReminderUtils.deleteCalendarEvent(context, feedback.getContent());
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        SpannableString spannableString = new SpannableString(feedback.getContent());
//                        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
//                        spannableString.setSpan(strikethroughSpan, 0, feedback.getContent().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//                        ((TodoHolder) holder).content.setText(spannableString); //中间横线（删除线
//                    } else {//恢复为未完成，恢复添加
//                        try {
//                            CalendarReminderUtils.addCalendarEvent(context, feedback.getContent() + "「" + context.getString(R.string.app_name) + " Todo" + "」", feedback.getContent(), TimeUtil.INSTANCE.getStringToDate(feedback.getTime(), "yyyy-MM-dd HH:mm"), 0, finalIsRe);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        ((TodoHolder) holder).content.setText(feedback.getContent());
//                    }
//                    ((TodoHolder) holder).content.invalidate();
//                }
//            });
        }

//        holder.itemView.setOnClickListener(view -> {
//            Fragment fragment = new todoEditFragment();
//            Bundle args = new Bundle();
//            args.putString(Constants.Todo.COLUMN_CONTENT, feedback.getContent());
//            args.putString(Constants.Todo.COLUMN_TIME, feedback.getTime());
//            args.putInt(Constants.Todo.COLUMN_ID,
//                    feedback.getId());
//            if (feedback.getOnlineID().contentEquals("1")){
//                args.putInt(Constants.Todo.COLUMN_ISREPEAT,
//                        1);
//            } else {
//                args.putInt(Constants.Todo.COLUMN_ISREPEAT,
//                        0);
//            }
//            fragment.setArguments(args);
//            context.getSupportFragmentManager().beginTransaction()
//                    .setCustomAnimations(
//                            R.anim.slide_bottom_in,
//                            R.anim.slide_up_out,
//                            R.anim.slide_up_in,
//                            R.anim.slide_bottom_out
//                    )
//                    .add(R.id.fragment_containerx, fragment)
//                    .addToBackStack(null)
//                    .commit();
//        });

//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {//长按删除
//                //delete(position);
//                return true;
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}