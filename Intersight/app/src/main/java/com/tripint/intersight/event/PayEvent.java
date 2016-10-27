package com.tripint.intersight.event;

/**
 * Created by lirichen on 2016/9/22.
 */

public class PayEvent {
    private boolean result;

    public PayEvent(boolean success) {
        this.result = success;
    }


    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
