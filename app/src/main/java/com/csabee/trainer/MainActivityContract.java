package com.csabee.trainer;

public interface MainActivityContract {

    interface Presenter {
        void onShowData(TemperatureData temperatureData);
    }

    interface View {
        void showData(TemperatureData temperatureData);
    }

}
