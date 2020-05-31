package cn.libv.todo.ui.send;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SendViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<String> mTime;
    private MutableLiveData<String> mPerson;



    public LiveData<String> getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText.setValue(mText);
    }

    public MutableLiveData<String> getTime() {
        return mTime;
    }

    public void setTime(String mTime) {
        this.mTime.setValue(mTime);
    }

    public MutableLiveData<String> getPerson() {
        return mPerson;
    }

    public void setPerson(String mPerson) {
        this.mPerson.setValue(mPerson);
    }
}