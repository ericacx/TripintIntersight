package com.tripint.intersight.model;

import java.io.Serializable;

/**
 * Created by lirichen on 2016/9/21.
 */

public class SearchRequestModel implements Serializable {
    private String searchKeyword;
    private String type;

    public SearchRequestModel(String searchKeyword, String type) {
        this.searchKeyword = searchKeyword;
        this.type = type;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
