package com.tripint.intersight.event;

/**
 * Created by lirichen on 2016/9/23.
 */

public class UserLoginEvent {
    private boolean isLoginSuccess;

    public UserLoginEvent(boolean isLoginSuccess) {
        this.isLoginSuccess = isLoginSuccess;
    }
}
