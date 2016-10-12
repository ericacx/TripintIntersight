package com.tripint.intersight.common;

import java.util.List;

/**
 * Created by liukun on 16/3/5.
 */
public class BasePageableResponse<T> {

    private int total;
    private List<T> lists;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getLists() {
        return lists;
    }

    public void setLists(List<T> lists) {
        this.lists = lists;
    }
}
