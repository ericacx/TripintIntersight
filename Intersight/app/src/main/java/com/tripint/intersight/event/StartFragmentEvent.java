package com.tripint.intersight.event;


import com.tripint.intersight.common.fragmentation.SupportFragment;

/**
 * Created by YoKeyword on 16/6/30.
 */
public class StartFragmentEvent {
    public SupportFragment targetFragment;

    public StartFragmentEvent(SupportFragment targetFragment) {
        this.targetFragment = targetFragment;
    }
}
