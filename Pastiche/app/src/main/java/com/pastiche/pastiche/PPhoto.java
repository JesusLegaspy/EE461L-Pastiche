package com.pastiche.pastiche;

import java.util.Date;

/**
 * Created by jacobingalls on 10/8/16.
 */
public class PPhoto {

    int id;
    int userId;
    Date uploaded;
    String contentType;

    public PPhoto(int id, int userId, Date uploaded, String contentType) {
        this.id = id;
        this.userId = userId;
        this.uploaded = uploaded;
        this.contentType = contentType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getUploaded() {
        return uploaded;
    }

    public void setUploaded(Date uploaded) {
        this.uploaded = uploaded;
    }

    public String getContentType() {
        return contentType;
    }

    public void  setContentType(String contentType) {
        this.contentType = contentType;
    }
}
