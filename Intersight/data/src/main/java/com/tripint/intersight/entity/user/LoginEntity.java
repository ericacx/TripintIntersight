package com.tripint.intersight.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by Eric on 16/9/24.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginEntity implements Serializable {


    /**
     * toke : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjUyfQ.1a2ry-hMsyIy1Ylcotq0rm6xp9KsK7f00iCqEsAIPpw
     * nickname : 刘进52
     * avatar : http://oc153j0jh.bkt.clouddn.com/1472448319003R52335-17.jpg
     */

    private UserInfoBean userInfo;

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfoBean {
        private String toke;
        private String nickname;
        private String avatar;

        public String getToke() {
            return toke;
        }

        public void setToke(String toke) {
            this.toke = toke;
        }

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
    }
}
