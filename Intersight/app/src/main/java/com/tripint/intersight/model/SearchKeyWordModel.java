package com.tripint.intersight.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lirichen on 2016/9/21.
 */

public class SearchKeyWordModel implements Serializable {
    private String groupName;
    private List<String> keywords;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}
