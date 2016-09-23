package com.tripint.intersight.event;


import com.tripint.intersight.common.fragmentation.SupportFragment;

/**
 * Created by lirichen on 16/6/30.
 */
public class StartFragmentForResultEvent {

    public static final int REQ_LOGIN_FRAGMENT = 100;

    public SupportFragment targetFragment;
    public int requestCode;

    public StartFragmentForResultEvent(SupportFragment targetFragment, int requestCode) {
        this.targetFragment = targetFragment;
        this.requestCode = requestCode;
    }
}
