package com.pastiche.pastiche.pObject;

import java.sql.ResultSet;
import java.util.Date;


/**
 * Created by jacobingalls on 10/17/16.
 */
public class PEvent {
    int eventId;
    int userId;
    Date created;
    String name;

    public PEvent(int eventId, int userId, Date created, String name) {
        this.eventId = eventId;
        this.created = created;
        this.userId = userId;
        this.name = name;
    }

    public PEvent(ResultSet rs) throws java.sql.SQLException{
        this(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getDate("created"),
                rs.getString("name")
        );
    }

    public int getEventId() { return eventId;}
    public void setEventId(int eventId) { this.eventId = eventId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Date getCreated() { return created; }
    public void setCreated(Date created) { this.created = created; }

    public String getName() { return  name; }
    public void  setName(String name) { this.name = name; }
}
