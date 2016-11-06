package com.pastiche.pastiche.model;

/**
 * Java representation of data which will be displayed in recycler view
 * Created by Aria Pahlavan on 11/5/16.
 */

public class EventlistItem {

    private String title;

    public EventlistItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
