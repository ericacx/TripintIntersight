package com.tripint.intersight.bean;

/**
 * Created by Eric on 16/9/14.
 */
public class OpinionData {

    private int photo;
    private String title;
    private String name ;
    private String trade;
    private String position;
    private int agree;
    private String agreeNum;
    private int talk;
    private String talkNum;
    private int report;
    private String content;

    public OpinionData(String content, int photo, String title, String name, String trade,
                       String position, int agree, String agreeNum, int talk, String talkNum, int report) {
        this.content = content;
        this.photo = photo;
        this.title = title;
        this.name = name;
        this.trade = trade;
        this.position = position;
        this.agree = agree;
        this.agreeNum = agreeNum;
        this.talk = talk;
        this.talkNum = talkNum;
        this.report = report;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getAgree() {
        return agree;
    }

    public void setAgree(int agree) {
        this.agree = agree;
    }

    public String getAgreeNum() {
        return agreeNum;
    }

    public void setAgreeNum(String agreeNum) {
        this.agreeNum = agreeNum;
    }

    public int getTalk() {
        return talk;
    }

    public void setTalk(int talk) {
        this.talk = talk;
    }

    public String getTalkNum() {
        return talkNum;
    }

    public void setTalkNum(String talkNum) {
        this.talkNum = talkNum;
    }

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
        this.report = report;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String  content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "OpinionData{" +
                "photo=" + photo +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", trade='" + trade + '\'' +
                ", position='" + position + '\'' +
                ", agree=" + agree +
                ", agreeNum='" + agreeNum + '\'' +
                ", talk=" + talk +
                ", talkNum='" + talkNum + '\'' +
                ", report=" + report +
                ", content='" + content + '\'' +
                '}';
    }
}
