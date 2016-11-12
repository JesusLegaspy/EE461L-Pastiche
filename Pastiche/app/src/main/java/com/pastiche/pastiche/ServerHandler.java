package com.pastiche.pastiche;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.function.Consumer;

import static android.R.attr.x;

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
    public void login (String username, String password, Consumer<PSession> data, Consumer<String> error){
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
        }
    }

    //call from UI for new user request
    public void createUser(String username, String password, String email, Consumer<PSession> data, Consumer<String> error) {
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);
        JSONObject body = new JSONObject();
        try{
            body.put("username", username);
            body.put("password", password);
            body.put("email", email);
            handle.jsonPost("/users", body,
                    x -> { data.accept(getGsonDeserializedDate().fromJson(getResponse(x), PSession.class)); },
                    x -> error.accept(onErrorResponse(x)));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //call from UI to upload an image
    public void postImg(Bitmap bmp, Consumer<Integer> response, Consumer<String> error) {
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);

        Consumer<NetworkResponse> myResponse = new Consumer<NetworkResponse>() {
            @Override
            public void accept(NetworkResponse x) {
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
            }
        };

        Consumer<VolleyError> myError = new Consumer<VolleyError>() {
            @Override
            public void accept(VolleyError x) {
                String errorMsg = onErrorResponse(x);
                error.accept(errorMsg);
            }
        };

        handle.imagePost("/photos", bmp, myResponse , myError);
    }


    public void postImg(String filepath, Consumer<Integer> response, Consumer<String> error) {
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);

        Consumer<NetworkResponse> myResponse = new Consumer<NetworkResponse>() {
            @Override
            public void accept(NetworkResponse x) {
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
            }
        };

        Consumer<VolleyError> myError = new Consumer<VolleyError>() {
            @Override
            public void accept(VolleyError x) {
                String errorMsg = onErrorResponse(x);
                error.accept(errorMsg);
            }
        };

        handle.imagePost("/photos", filepath, myResponse , myError);
    }



    //call from UI to download an image

    public void getImg(int photoID, ImageView.ScaleType scaleType, Consumer<Bitmap> img, Consumer<String> error){
        ServerRequestHandler handle = ServerRequestHandler.getInstance(mCtx);
        String tmp = "/photos/";
        Integer id = photoID;
        String url = tmp.concat(id.toString());

        Consumer<VolleyError> myError = new Consumer<VolleyError>() {
            @Override
            public void accept(VolleyError x) {
                String errorMsg = onErrorResponse(x);
                error.accept(errorMsg);
            }
        };
        handle.imageGet(url, scaleType, img::accept, myError);
    }

    @NonNull
    private Gson getGsonDeserializedDate() {
        return new GsonBuilder().setDateFormat("MMM d',' yyyyy").create();
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
                case 401:
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