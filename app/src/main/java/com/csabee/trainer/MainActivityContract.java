package com.csabee.trainer;

public interface MainActivityContract {

    interface Presenter {
        void onMenuClick();
    }

    interface View {
        void menuClick();
    }

}
