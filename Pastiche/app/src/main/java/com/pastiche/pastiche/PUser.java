package com.pastiche.pastiche;

/**
 * Created by Khalid on 10/22/2016.
 */

public class PUser {
    private int id;
    private String username;
    private String email;

    public PUser(int id, String username, String email) {
        //make singleton
        this.id = id;
        this.username = username;
        this.email = email;
    }

    //create a deserializer

    public int getID() { return this.id; }
    public String getUsername() { return this.username; }
    public String getEmail() { return this.email; }

}
