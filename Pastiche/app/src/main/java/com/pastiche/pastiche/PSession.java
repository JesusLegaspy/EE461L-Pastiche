package com.pastiche.pastiche;

import com.google.gson.annotations.SerializedName;

import java.sql.ResultSet;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by jacobingalls on 9/29/16.
 */
public class PSession {

    int id;
    @SerializedName("user_id")
    int userId;

    Date expires;

    public PSession(int id, int userId, Date expires) {
        this.id = id;
        this.userId = userId;
        this.expires = expires;
    }

    public PSession(ResultSet rs) throws java.sql.SQLException{
        this(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getDate("expires")
        );
    }

    public boolean isValid() {
        if(expires == null)
            return false;

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        return expires.after(new Date());
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

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof PSession) {
            PSession other = (PSession)o;
            return getId() == other.getId();
        } else return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
