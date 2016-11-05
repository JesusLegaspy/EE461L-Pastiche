package com.pastiche.pastiche;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.Consumer;

/**
 * Created by jesus on 10/25/2016.
 */

class ServerRequestHandler {
    @SuppressLint("StaticFieldLeak") // Okay as long as this.getApplicationContext is passed as a parameter in getInstance.
    private static ServerRequestHandler mInstance;
    @SuppressLint("StaticFieldLeak") // https://stackoverflow.com/questions/40094020/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memor
    private static Context mCtx;
    private static String baseURL = "http://api.pastiche.staging.jacobingalls.rocks:8080";

    private ServerRequestHandler (Context context){
        mCtx = context;
    }

    public static ServerRequestHandler getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ServerRequestHandler.class) {
                if (mInstance == null) {
                    mInstance = new ServerRequestHandler(context);
                }
            }
        }
        return mInstance;
}

    public void jsonPost(String url, JSONObject body, Consumer<JSONObject> data, Consumer<VolleyError> errorData) throws JSONException {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, baseURL + url, body, data::accept, errorData::accept);
        ServerRequestQueue pQueue = ServerRequestQueue.getInstance(mCtx);
        pQueue.addToRequestQueue(jsObjRequest);
    }

    public void jsonGet(String url, JSONObject body, Consumer<JSONObject> data, Consumer<VolleyError> errorData) throws JSONException{
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, baseURL + url, body, data::accept, errorData::accept);
        ServerRequestQueue pQueue = ServerRequestQueue.getInstance(mCtx);
        pQueue.addToRequestQueue(jsObjRequest);
    }

    public void stringPost(String url, String body, Consumer<String> data, Consumer<VolleyError> errorData) {
        StringRequest strRequest = new StringRequest
                (Request.Method.POST, baseURL + url, data::accept, errorData::accept);
        ServerRequestQueue pQueue = ServerRequestQueue.getInstance(mCtx);
        pQueue.addToRequestQueue(strRequest);
    }

    public void stringGet(String url, String body, Consumer<String> data, Consumer<VolleyError> errorData) {
        StringRequest strRequest = new StringRequest
                (Request.Method.GET, baseURL + url, data::accept, errorData::accept);
        ServerRequestQueue pQueue = ServerRequestQueue.getInstance(mCtx);
        pQueue.addToRequestQueue(strRequest);
    }

// getPicture
// postPicture
}
