package com.pastiche.pastiche;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.function.Consumer;

/**
 * Created by Khalid on 10/22/2016.
 */

public class ServerHandler {
    @SuppressLint("StaticFieldLeak") // Okay as long as this.getApplicationContext is passed as a parameter in getInstance.
    private static ServerHandler mInstance;
    @SuppressLint("StaticFieldLeak") // https://stackoverflow.com/questions/40094020/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memor
    private static Context mCtx;

    private ServerHandler (Context context){
        mCtx = context;
    }

    public static ServerHandler getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ServerHandler.class) {
                if (mInstance == null) {
                    mInstance = new ServerHandler(context);
                }
            }
        }
        return mInstance;
    }

    //-----------------------------[Khalid's Code]----------------------

    public PUser createAccountRequest(){
        //check if username is in use
        return null;
    }

    /*
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
    }*/

    //-----------------------------[End of Khalid' Code]----------------------

    public void login (String username, String password, Consumer<PSession> data, Consumer<String> error){
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);
        JSONObject body = new JSONObject();
        try {
            body.put("username", username);
            body.put("password", password);
            handle.jsonPost("/users/login", body,
                    x -> data.accept(new GsonBuilder().setDateFormat("MMM d',' yyyyy").create().fromJson(getResponse(x), PSession.class)),
                    x -> error.accept(onErrorResponse(x)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getResponse(JSONObject x) {
        JSONObject dataObj = null;
        try {
            dataObj = (JSONObject) x.get("response");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataObj.toString();
    }

//Credit from "Submersed" on https://stackoverflow.com/questions/21867929/android-how-handle-message-error-from-the-server-using-volley

    public String onErrorResponse(VolleyError error) {
        String json = null;

        NetworkResponse response = error.networkResponse;
        if(response != null && response.data != null){
            switch(response.statusCode){
                case 400:
                    json = new String(response.data);
                    json = trimMessage(json, "error");
                    if(json != null) return json;
                default:
                    return "Unhandled error code " + response.statusCode;
            }
            //Additional cases
        }
        return "Unexpected VolleyError";
    }

    public String trimMessage(String json, String key){
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }
}