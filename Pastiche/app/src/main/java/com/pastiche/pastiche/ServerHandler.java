package com.pastiche.pastiche;

        import android.util.Log;

        import com.android.volley.NetworkResponse;
        import com.android.volley.VolleyError;

        import org.json.JSONException;
        import org.json.JSONObject;

/**
 * Created by Khalid on 10/22/2016.
 */

public class ServerHandler {
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

//Credit from "Submersed" on https://stackoverflow.com/questions/21867929/android-how-handle-message-error-from-the-server-using-volley

    public void onErrorResponse(VolleyError error) {
        String json = null;

        NetworkResponse response = error.networkResponse;
        if(response != null && response.data != null){
            switch(response.statusCode){
                case 400:
                    json = new String(response.data);
                    json = trimMessage(json, "error");
                    if(json != null) displayMessage(json);
                    break;
            }
            //Additional cases
        }
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

    //Somewhere that has access to a context
    public void displayMessage(String toastString){
        //Toast.makeText(context, toastString, Toast.LENGTH_LONG).show();
        Log.d("main", toastString);
    }
}