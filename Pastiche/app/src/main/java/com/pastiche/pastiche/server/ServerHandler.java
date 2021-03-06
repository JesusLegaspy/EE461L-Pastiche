package com.pastiche.pastiche.server;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pastiche.pastiche.utils.PConsumer;
import com.pastiche.pastiche.pObject.PEvent;
import com.pastiche.pastiche.pObject.PPhoto;
import com.pastiche.pastiche.pObject.PSession;
import com.pastiche.pastiche.pObject.PUser;
import org.json.JSONException;
import org.json.JSONObject;

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

    //call from UI for a login to existing account
    public void login (String username, String password, PConsumer<PSession> data, PConsumer<String> error){
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);
        JSONObject body = new JSONObject();
        try {
            body.put("username", username);
            body.put("password", password);
            handle.jsonPost("/users/login", body,
                    x -> data.accept(getGsonDeserializedDate().fromJson(getResponse(x), PSession.class)),
                    x -> error.accept(onErrorResponse(x)));
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error within the login() method of ServerHandler.");
        }
    }

    public void logout (PConsumer<String> data, PConsumer<String> error) {
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);
        //JSONObject body = new JSONObject();
        handle.stringGet("/users/logout", "",
                x -> data.accept("SERVER_HANDLER: Logout successful."),
                x -> error.accept("SERVER_HANDLER: Error in logging out."));
    }

    //call from UI for new user request
    public void createUser(String username, String password, String email, PConsumer<PSession> data, PConsumer<String> error) {
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);
        JSONObject body = new JSONObject();
        try{
            body.put("username", username);
            body.put("password", password);
            body.put("email", email);
            handle.jsonPost("/users", body,
                    x -> data.accept(getGsonDeserializedDate().fromJson(getResponse(x), PSession.class)),
                    x -> error.accept(onErrorResponse(x)));

        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Errow within the createUser() method of ServerHandler.");
        }
    }

    //call from UI to upload an image when you have the bitmap
    public void postImg(Bitmap bmp, PConsumer<Integer> response, PConsumer<String> error) {
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);

        PConsumer<NetworkResponse> myResponse = x -> {
            String jsonAsStringObj = new String(x.data);
            String test = null;
            try {
                JSONObject tmp = new JSONObject(jsonAsStringObj);
                test = getResponse(tmp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String IDNum = trimMessage(test, "id");
            Integer result = Integer.decode(IDNum);
            response.accept(result);
        };

        PConsumer<VolleyError> myError = x -> {
            String errorMsg = onErrorResponse(x);
            error.accept(errorMsg);
        };

        handle.imagePost("/photos", bmp, myResponse , myError);
    }

    //call from UI to upload an image when you have the filepath
    public void postImg(String filepath, PConsumer<Integer> response, PConsumer<String> error) {
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);

        PConsumer<NetworkResponse> myResponse = x -> {
            String jsonAsStringObj = new String(x.data);
            String test = null;
            try {
                JSONObject tmp = new JSONObject(jsonAsStringObj);
                test = getResponse(tmp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String IDNum = trimMessage(test, "id");
            Integer result = Integer.decode(IDNum);
            response.accept(result);
        };

        PConsumer<VolleyError> myError = x -> {
            String errorMsg = onErrorResponse(x);
            error.accept(errorMsg);
        };

        handle.imagePost("/photos", filepath, myResponse , myError);
    }

    //call from UI to upload an image when you have the filepath
    public void postImg(int event, String filepath, PConsumer<Integer> response, PConsumer<String> error) {
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);

        PConsumer<NetworkResponse> myResponse = x -> {
            String jsonAsStringObj = new String(x.data);
            String test = null;
            try {
                JSONObject tmp = new JSONObject(jsonAsStringObj);
                test = getResponse(tmp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String IDNum = trimMessage(test, "id");
            Integer result = Integer.decode(IDNum);
            response.accept(result);
        };

        PConsumer<VolleyError> myError = x -> {
            String errorMsg = onErrorResponse(x);
            error.accept(errorMsg);
        };

        handle.imagePost("/events/"+event+"/photos", filepath, myResponse , myError);
    }

    //Uploads an image to an event when you have the bitmap-- a general post image manager is coming soon
    public void postImg(PEvent event, Bitmap bmp, PConsumer<Integer> response, PConsumer<String> error) {
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);

        PConsumer<NetworkResponse> myResponse = x -> {
            String jsonAsStringObj = new String(x.data);
            String test = null;
            try {
                JSONObject tmp = new JSONObject(jsonAsStringObj);
                test = getResponse(tmp);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String IDNum = trimMessage(test, "id");
            Integer result = Integer.decode(IDNum);
            response.accept(result);
        };

        PConsumer<VolleyError> myError = x -> {
            String errorMsg = onErrorResponse(x);
            error.accept(errorMsg);
        };
        int id = event.getEventId();
        String url = "/events/" + id + "/photos";
        handle.imagePost(url, bmp, myResponse , myError);
    }

    //call from UI to download an image
    public void getImg(int photoID, ImageView.ScaleType scaleType, PConsumer<Bitmap> img, PConsumer<String> error){
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);
        String tmp = "/photos/";
        Integer id = photoID;
        String url = tmp.concat(id.toString());

        PConsumer<VolleyError> myError = x -> {
            String errorMsg = onErrorResponse(x);
            error.accept(errorMsg);
        };
        handle.imageGet(url, scaleType, img, myError);
    }

    @NonNull
    private Gson getGsonDeserializedDate() {
        return new GsonBuilder().setDateFormat("MMM d',' yyyyy").create();
    }

    private String getResponse(JSONObject x) {
        try {
            return x.get("response").toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "null";
        }
    }

    //Credit from "Submersed" on https://stackoverflow.com/questions/21867929/android-how-handle-message-error-from-the-server-using-volley
    public String onErrorResponse(VolleyError error) {

        NetworkResponse response = error.networkResponse;
        if(response != null && response.data != null){
            switch(response.statusCode){
                case 400:
                case 401:
                case 404:
                    return ("HTTP error code " + response.statusCode +": Malformed request from client.");
                case 500:
                case 504:
                    return ("HTTP error code " + response.statusCode + ": No response from server.");
                default:
                    return "Unhandled error code " + response.statusCode;
            }
            //Additional cases
        }
        return "Unexpected VolleyError: "+error.toString();
    }

    public String trimMessage(String json, String key){
        String trimmedString;
        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }
        return trimmedString;
    }

    public void listEvents(PConsumer<PEvent[]> data, PConsumer<String> error) {
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);
        JSONObject body = new JSONObject();
        try{
            handle.jsonGet("/events", body,
                    x -> data.accept(getGsonDeserializedDate().fromJson(getResponse(x), PEvent[].class)),
                    x -> error.accept(onErrorResponse(x)));

        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error within the listEvents() method of ServerHandler.");
        }
    }

    public void listPhotosForAnEvent(int id, PConsumer<PPhoto[]> data, PConsumer<String> error) {
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);
        JSONObject body = new JSONObject();
        try{
            handle.jsonGet("/events/"+id+"/photos", body,
                    x -> data.accept(getGsonDeserializedDate().fromJson(getResponse(x), PPhoto[].class)),
                    x -> error.accept(onErrorResponse(x)));

        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error within the listPhotosForAnEvent() function.");
        }
    }

    public void listPhotosForAnUser(int id, PConsumer<PPhoto[]> data, PConsumer<String> error) {
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);
        JSONObject body = new JSONObject();
        try{
            handle.jsonGet("/users/"+id+"/photos", body,
                    x -> data.accept(getGsonDeserializedDate().fromJson(getResponse(x), PPhoto[].class)),
                    x -> error.accept(onErrorResponse(x)));

        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error within the listPhotosForAnUser() function.");
        }
    }

    //call from UI for new event request
    public void createEvent(String eventName, PConsumer<PEvent> data, PConsumer<String> error) {
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);
        JSONObject body = new JSONObject();
        try{
            body.put("name", eventName);
            handle.jsonPost("/events", body,
                    x -> data.accept(getGsonDeserializedDate().fromJson(getResponse(x), PEvent.class)),
                    x -> error.accept(onErrorResponse(x)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void removePhotoFromEvent(String photoID, String eventID, PConsumer<String> data, PConsumer<String> error){
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);
        handle.delete("/photos/" + photoID + "/events/" + eventID,
                x -> data.accept(getResponse(x)),
                x -> error.accept(onErrorResponse(x))
        );
    }

    public void removePhotoFromEvent(PPhoto photo, PEvent event, PConsumer<String> data, PConsumer<String> error){
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);
        handle.delete("/photos/" + photo.getId() + "/events/" + event.getEventId(),
                x -> data.accept(getResponse(x)),
                x -> error.accept(onErrorResponse(x))
        );
    }
    public void searchEvents(String query, PConsumer<PEvent[]> data, PConsumer<String> error) {
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);
        String url = "/search?q=";
        url = url.concat(query);
        PConsumer<JSONObject> myData = x -> {
            String info = x.toString();
            String events = trimMessage(info, "response");   //strips "response"
            events = trimMessage(events, "events");    //strips "users"

            data.accept(getGsonDeserializedDate().fromJson(events, PEvent[].class));
        };
        try{
            handle.jsonGet(url, new JSONObject(),myData,
                   x -> error.accept(onErrorResponse(x)));
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error within the searchEvents() method of ServerHandler.");
        }
    }

    public void searchUsers(String query, PConsumer<PUser[]> data, PConsumer<String> error) {
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);
        String url = "/search?q=";
        url = url.concat(query);
        PConsumer<JSONObject> myData = x -> {
            String info = x.toString();
            String users = trimMessage(info, "response");   //strips "response"
            users = trimMessage(users, "users");    //strips "users"

            data.accept(getGsonDeserializedDate().fromJson(users, PUser[].class));
        };
        try{
            handle.jsonGet(url, new JSONObject(), myData,
                    x -> error.accept(onErrorResponse(x)));
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error within the searchUsers() method of ServerHandler.");
        }
    }

    public void getEvent(int id, PConsumer<PEvent> event, PConsumer<String> error) {
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);
        String url = "/events/";
        url = url.concat(Integer.toString(id));
        try {
            handle.jsonGet(url, new JSONObject(),
                    x -> event.accept(getGsonDeserializedDate().fromJson(getResponse(x), PEvent.class)),
                    x -> error.accept(onErrorResponse(x)));
        } catch(JSONException e) {
            e.printStackTrace();
            System.out.println("Error within the getEvent() method of ServerHandler.");
        }
    }

    public void getUser(int id, PConsumer<PUser> user, PConsumer<String> error) {
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);
        String url = "/users/";
        url = url.concat(Integer.toString(id));
        try {
            handle.jsonGet(url, new JSONObject(),
                    x -> user.accept(getGsonDeserializedDate().fromJson(getResponse(x), PUser.class)),
                    x -> error.accept(onErrorResponse(x)));
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error within the getUser() method of ServerHandler.");
        }
    }

    public void getUserSession(PConsumer<PSession> user, PConsumer<String> error) {
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);
        String url = "/users/session";
        try {
            handle.jsonGet(url, new JSONObject(),
                    x -> user.accept(getGsonDeserializedDate().fromJson(getResponse(x), PSession.class)),
                    x -> error.accept(onErrorResponse(x)));
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("Error within the getUser() method of ServerHandler.");
        }
    }
}