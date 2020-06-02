package cn.libv.todo.ui.login.ui.login;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.libv.todo.R;

/*
* 登录页
* */
public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private LoginController loginController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginController = new LoginController(loginViewModel,getContext());
        final EditText usernameEditText = view.findViewById(R.id.username);
        final EditText passwordEditText = view.findViewById(R.id.password);
        final Button loginButton = view.findViewById(R.id.login);
        final TextView id = view.findViewById(R.id.id);
        final Button loginOut = view.findViewById(R.id.btn_login_out);

        loginViewModel.setData(getContext());

        loginViewModel.getIsLogin().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                //当用户已经登录，则进行界面切换
                if (aBoolean){//已经登录
                    usernameEditText.setVisibility(View.GONE);
                    passwordEditText.setVisibility(View.GONE);
                    loginButton.setVisibility(View.GONE);
                    loginOut.setVisibility(View.VISIBLE);
                    id.setVisibility(View.VISIBLE);
                } else {//未登录
                    loginOut.setVisibility(View.GONE);
                    id.setVisibility(View.GONE);
                    usernameEditText.setVisibility(View.VISIBLE);
                    passwordEditText.setVisibility(View.VISIBLE);
                    loginButton.setVisibility(View.VISIBLE);
                }
            }
        });

        //退出登录
        loginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginController.loginOut();
                loginViewModel.setIsLogin(false);
            }
        });

        loginViewModel.getName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                usernameEditText.setText(s);
            }
        });
        loginViewModel.getPassword().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                passwordEditText.setText(s);
            }
        });
        loginViewModel.getID().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                id.setText("Your ID:"+s);
            }
        });
        //登录
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginController.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }
}