package com.genius.iconified.utils;

import java.io.Serializable;

/**
 * Created by manjeet on 2/3/18.
 */

public class SearchRecordHolder implements Serializable {

    private String iconName;
    private String date;

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
