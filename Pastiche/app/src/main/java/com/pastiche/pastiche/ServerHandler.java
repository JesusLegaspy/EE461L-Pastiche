package com.pastiche.pastiche;

import com.android.volley.Request;
import com.google.gson.Gson;

/**
 * Created by Khalid on 10/22/2016.
 */

public class ServerHandler {
    public PUser createAccountRequest(){
        //check if username is in use
        return null;
    }

    public void loginRequest(String usern, String passw, ResponseUI respUI) {
        Gson gson = new Gson();
        String tmp = gson.toJson(new login(usern, passw));
        System.out.println(tmp);
        Request myrequest = create(resp);
        JesusTools.login(json,myrequest);
        return;
    }

    private Response create(ResponseUI){
        new response(){
            @override
            Public void onResponse(Json returnedData){
                //make a user object with returned data
                respUI.runthis(Puserobjecct);
            }
        }
    }
    //boolean for now-- request successful?
    public boolean postRequest() {
        return false;
    }
    //boolean for now-- request successful?
    public boolean getRequest() {

    }

}
class login{
    private String username;
    private String password;

    public login(String usern, String passw){
        this.username = usern;
        this.password = passw;
    }
}