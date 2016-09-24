package com.tripint.intersight.bean;

import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;

/**
 * Created by Eric on 16/9/23.
 */

public class InterestedDataEntity implements Serializable {

    private int icon;
    private String trade;

    public InterestedDataEntity() {
    }

    public InterestedDataEntity(int icon, String trade) {
        this.icon = icon;
        this.trade = trade;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }
}
