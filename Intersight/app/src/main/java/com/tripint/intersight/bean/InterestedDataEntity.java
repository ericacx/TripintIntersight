package com.tripint.intersight.bean;

import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/9/23.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InterestedDataEntity implements Checkable, Serializable {

    private int icon;
    private String trade;

    private boolean checked = false;


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

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void setChecked(boolean b) {
        this.checked = b;
    }

    @Override
    public void toggle() {
        setChecked(!checked);
    }
}
