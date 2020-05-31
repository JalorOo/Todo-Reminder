package cn.libv.todo.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePrefenrenceUtils {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;//编辑器

    public SharePrefenrenceUtils(Context context, String name){
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String QueryShare(String what,String type){
        String result = sharedPreferences.getString(what,type);
        if (result!=null)
            return result;
        else
            return "";
    }

    public boolean QueryShare(String what,boolean type){
        return sharedPreferences.getBoolean(what,type);
    }

    public int QueryShare(String what,int type){
        return sharedPreferences.getInt(what,type);
    }

    public void setShare(String what,String type){
        editor.putString(what,type);
        editor.commit();
    }

    public void setShare(String what,int type){
        editor.putInt(what,type);
        editor.commit();
    }

    public void setShare(String what,boolean type){
        editor.putBoolean(what,type);
        editor.commit();
    }
}
