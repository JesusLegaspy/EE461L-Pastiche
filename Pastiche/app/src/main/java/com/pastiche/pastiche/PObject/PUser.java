package com.pastiche.pastiche.PObject;

/**
 * Created by Khalid on 10/22/2016.
 */

public class PUser {
    private int id;
    private String username;
    private String email;
    private String password;

    public PUser(int id, String username, String email, String password) {
        //make singleton
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    //create a deserializer

    public int getID() { return this.id; }
    public String getUsername() { return this.username; }
    public String getEmail() { return this.email; }
    public String getPassword() {return  this.password;}
}
