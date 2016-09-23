package com.tripint.intersight.event;

import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by lirichen on 2016/9/23.
 */

public class ShareLoginEvent {
    public boolean isLoginSuccess;
    public SHARE_MEDIA platform;

    public ShareLoginEvent(boolean isLoginSuccess) {
        this.isLoginSuccess = isLoginSuccess;
    }

    public ShareLoginEvent(SHARE_MEDIA platform) {
        this.platform = platform;
    }

}
