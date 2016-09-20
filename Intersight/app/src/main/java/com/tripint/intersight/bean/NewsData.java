package com.tripint.intersight.bean;

/**
 * Created by Eric on 16/9/14.
 */
public class NewsData {

    private String title;
    private String publicName;
    private String name;
    private int agree;
    private int agreeNum;
    private int comment;
    private int commentNum;
    private int report;
    private String content;

    public NewsData(String title, String publicName, String name, int agree, int agreeNum,
                    int comment, int commentNum, int report, String content) {
        this.title = title;
        this.publicName = publicName;
        this.name = name;
        this.agree = agree;
        this.agreeNum = agreeNum;
        this.comment = comment;
        this.commentNum = commentNum;
        this.report = report;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublicName() {
        return publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAgree() {
        return agree;
    }

    public void setAgree(int agree) {
        this.agree = agree;
    }

    public int getAgreeNum() {
        return agreeNum;
    }

    public void setAgreeNum(int agreeNum) {
        this.agreeNum = agreeNum;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
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

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NewsData{" +
                "title='" + title + '\'' +
                ", publicName='" + publicName + '\'' +
                ", name='" + name + '\'' +
                ", agree=" + agree +
                ", agreeNum=" + agreeNum +
                ", comment=" + comment +
                ", commentNum=" + commentNum +
                ", report=" + report +
                ", content='" + content + '\'' +
                '}';
    }
}
