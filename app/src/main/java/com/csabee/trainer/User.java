package com.csabee.trainer;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.media.Image;

public class User extends BaseObservable {

    private String name;
    private Image profileImage;
    private String weight;
    private String height;
    private String intensity;

    public User(String userName){
        this.name = userName;
    }

    @Bindable
    public String getName(){
        return name;
    }

    public void setName(String userName){
        this.name = userName;
        notifyPropertyChanged(BR.name);
    }
}
