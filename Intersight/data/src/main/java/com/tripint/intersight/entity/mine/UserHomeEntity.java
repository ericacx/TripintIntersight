package com.tripint.intersight.entity.mine;

import java.io.Serializable;

/**
 * Created by lirichen on 2016/9/27.
 */

public class UserHomeEntity implements Serializable {

    private String nickname;
    private String avatar;
    private String balance;


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
