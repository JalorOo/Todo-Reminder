package cn.libv.todo.ui.login.ui.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.content.Context;

import cn.libv.todo.Constant;
import cn.libv.todo.utils.SharePrefenrenceUtils;

/*
* 登录界面数据类
* */
public class LoginViewModel extends ViewModel {

    private MutableLiveData<String> name ;
    private MutableLiveData<String> password ;
    private MutableLiveData<String> id ;
    private SharePrefenrenceUtils sharePrefenrenceUtils;
    private MutableLiveData<Boolean> isLogin;

    public void setIsLogin(Boolean isLogin) {
        if (this.isLogin==null){
            this.isLogin = new MutableLiveData<>();
        }
        this.isLogin.setValue(isLogin);
    }

    void setData(Context context){
        sharePrefenrenceUtils = new SharePrefenrenceUtils(context, Constant.USER);
        name = new MutableLiveData<>();
        password = new MutableLiveData<>();
        id = new MutableLiveData<>();
        name.setValue(sharePrefenrenceUtils.QueryShare(Constant.NAME,""));
        password.setValue(sharePrefenrenceUtils.QueryShare(Constant.PASSWORD,""));
        String i = sharePrefenrenceUtils.QueryShare(Constant.ID,"");
        id.setValue(i);
        if (i.contentEquals("")){
            setIsLogin(false);
        } else {
            setIsLogin(true);
        }
    }

    MutableLiveData<Boolean> getIsLogin(){
        return isLogin;
    }

    MutableLiveData<String> getName() {
        return name;
    }

    MutableLiveData<String> getPassword() {
        return password;
    }

    MutableLiveData<String> getID() {
        return id;
    }
}