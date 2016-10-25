package com.pastiche.pastiche;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jesus on 10/25/2016.
 */

public class ServerRequestHandler {

    public static void createAccountRequest(Context applicationContext, JSONObject body, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) throws JSONException {
        String url = "http://api.pastiche.staging.jacobingalls.rocks/users/login";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, body, listener, errorListener);

        ServerRequestQueue test = ServerRequestQueue.getInstance(applicationContext);
        test.addToRequestQueue(jsObjRequest);
    }
    //loginRequest
    //logoutRequest
    //postRequest
    //getRequest
}
