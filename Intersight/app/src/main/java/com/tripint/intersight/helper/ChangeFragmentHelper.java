package com.tripint.intersight.helper;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Eric on 16/8/31.
 */
public class ChangeFragmentHelper {
    /**
     * 用于帮助跳转携带一些信息的工具类
     */

    //目标跳转Fragment
    private Fragment targetFragment;

    //是否清空回退栈
    private boolean isClearStackBack;

    //是否加入到回退栈
    private String targetFragmentTag;

    //给目标Fragment传递Bundle值
    private Bundle bundle;

    /**
     * setter and getter
     */
    public Fragment getTargetFragment() {
        return targetFragment;
    }
    public void setTargetFragment(Fragment targetFragment) {
        this.targetFragment = targetFragment;
    }

    public boolean isClearStackBack() {
        return isClearStackBack;
    }

    public void setIsClearStackBack(boolean isClearStackBack) {
        this.isClearStackBack = isClearStackBack;
    }

    public String getTargetFragmentTag() {
        return targetFragmentTag;
    }

    public void setTargetFragmentTag(String targetFragmentTag) {
        this.targetFragmentTag = targetFragmentTag;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }
}
