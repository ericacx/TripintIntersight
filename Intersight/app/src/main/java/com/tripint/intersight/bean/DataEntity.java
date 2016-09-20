package com.tripint.intersight.bean;

/**
 * Created by Eric on 16/9/13.
 */
public class DataEntity {

    private int icon;//头像
    private String name;//名字
    private String workTime;//工作年限
    private String title;//职位
    private String company;//公司
    private String trade;//行业

    public DataEntity(int icon, String name, String workTime, String title, String company, String trade) {
        this.icon = icon;
        this.name = name;
        this.workTime = workTime;
        this.title = title;
        this.company = company;
        this.trade = trade;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    @Override
    public String toString() {
        return "DataEntity{" +
                "icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", workTime='" + workTime + '\'' +
                ", title='" + title + '\'' +
                ", company='" + company + '\'' +
                ", trade='" + trade + '\'' +
                '}';
    }
}
