package com.pastiche.pastiche;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.Consumer;

/**
 * Created by jesus on 10/25/2016.
 */

public class ServerRequestHandler {
    private static ServerRequestHandler mInstance;
    private static Context mCtx;
    private static String baseURL = "http://api.pastiche.staging.jacobingalls.rocks";

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

    public static void getJson(String url, JSONObject body) throws JSONException {
        Consumer<String> c = (x) -> System.out.println(x.toLowerCase());

        //JsonObjectRequest jsObjRequest = new JsonObjectRequest
        //        (Request.Method.POST, baseURL + url, body);

        //ServerRequestQueue test = ServerRequestQueue.getInstance(mCtx);
        //test.addToRequestQueue(jsObjRequest);
    }
// postJson
// getPicture
// postPicture
}
