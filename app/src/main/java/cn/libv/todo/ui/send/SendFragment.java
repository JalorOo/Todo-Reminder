package cn.libv.todo.ui.send;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import cn.libv.todo.R;
import cn.libv.todo.ui.home.Todo;

public class SendFragment extends Fragment {

    private SendController sendController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        final EditText mContent = root.findViewById(R.id.et_content);
        final EditText mTime = root.findViewById(R.id.et_time);
        final EditText mPerson = root.findViewById(R.id.et_person);
        final FloatingActionButton mSend = root.findViewById(R.id.send);
        sendController = new SendController(getContext());
        final Todo todo = new Todo();
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                todo.setTime(mTime.getText().toString());
                todo.setContent(mContent.getText().toString());
                todo.setUid(Long.valueOf(mPerson.getText().toString()));
                sendController.setTodo(todo);
                sendController.send(SendFragment.this);
            }
        });

        return root;
    }

    void Toast(String s){
        Toast.makeText(getContext(),s,Toast.LENGTH_SHORT).show();
    }
}