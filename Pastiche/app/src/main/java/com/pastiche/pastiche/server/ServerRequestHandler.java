package com.pastiche.pastiche.server;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.pastiche.pastiche.utils.PConsumer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jesus on 10/25/2016.
 */

public class ServerRequestHandler {
    @SuppressLint("StaticFieldLeak")
    // Okay as long as this.getApplicationContext is passed as a parameter in getInstance.
    private static ServerRequestHandler mInstance;
    @SuppressLint("StaticFieldLeak")
    // https://stackoverflow.com/questions/40094020/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memor
    private static Context mCtx;
    public static String baseURL = "http://api.pastiche.staging.jacobingalls.rocks:8080";
    public static String TAG = "SvrReqHandlr";

    private ServerRequestHandler(Context context) {
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

    public void jsonPost(String url, JSONObject body, PConsumer<JSONObject> data, PConsumer<VolleyError> errorData) throws JSONException, NullPointerException {
        if (data == null || errorData == null) {
            Log.d(TAG, "PConsumer is null");
            throw new NullPointerException();
        }
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, baseURL + url, body, data::accept, errorData::accept) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");

                return params;
            }
        };
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, 2));
        ServerRequestQueue pQueue = ServerRequestQueue.getInstance(mCtx);
        pQueue.addToRequestQueue(jsObjRequest);
    }

    public void jsonGet(String url, JSONObject body, PConsumer<JSONObject> data, PConsumer<VolleyError> errorData) throws JSONException {
        if (data == null || errorData == null) {
            Log.d(TAG, "PConsumer is null");
            throw new NullPointerException();
        }
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, baseURL + url, body, data::accept, errorData::accept);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 10, 2));
        ServerRequestQueue pQueue = ServerRequestQueue.getInstance(mCtx);
        pQueue.addToRequestQueue(jsObjRequest);
    }

    public void stringPost(String url, String body, PConsumer<String> data,
                           PConsumer<VolleyError> errorData) {
        StringRequest strRequest = new StringRequest
                (Request.Method.POST, baseURL + url, data::accept, errorData::accept);
        ServerRequestQueue pQueue = ServerRequestQueue.getInstance(mCtx);
        pQueue.addToRequestQueue(strRequest);
    }

    public void stringGet(String url, String body, PConsumer<String> data,
                          PConsumer<VolleyError> errorData) {
        String logError = "error: stringGet null parameter";
        if (url == null || body == null || data == null) {
            Log.d(TAG, logError);
            errorData.accept(new VolleyError(logError));
        }
        if (errorData == null) {
            Log.d(TAG, logError);
            return;
        }
        StringRequest strRequest = new StringRequest
                (Request.Method.GET, baseURL + url, data::accept, errorData::accept);
        ServerRequestQueue pQueue = ServerRequestQueue.getInstance(mCtx);
        pQueue.addToRequestQueue(strRequest);
    }

    void imageGet(String url, ImageView.ScaleType scaleType, PConsumer<Bitmap> data,
                  PConsumer<VolleyError> errorData) {
        ImageRequest imgRequest = new ImageRequest
                (baseURL + url, data::accept, 0, 0, scaleType, null, errorData::accept);
        ServerRequestQueue pQueue = ServerRequestQueue.getInstance(mCtx);
        pQueue.addToRequestQueue(imgRequest);
    }

    void imagePost(String url, Bitmap bitmapPhoto, PConsumer<NetworkResponse> data,
                   PConsumer<VolleyError> errorData) {
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                                                                             baseURL + url,
                                                                             data::accept,
                                                                             errorData::accept) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("content-type", "multipart/form-data; boundary=---011000010111000001101001");
                params.put("cache-control", "no-cache");
                params.put("jesuslegaspy-token", "a1bda35a-48c7-dde3-17e0-c092d78db16a");
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("photo", new DataPart(".jpg", getBytesFromBitmap(bitmapPhoto), "image/jpeg"));
                return params;
            }
        };
        ServerRequestQueue pQueue = ServerRequestQueue.getInstance(mCtx);
        pQueue.addToRequestQueue(multipartRequest);
    }

    void imagePost(String url, String imagePath, PConsumer<NetworkResponse> data,
                   PConsumer<VolleyError> errorData) {
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                                                                             baseURL + url,
                                                                             data::accept,
                                                                             errorData::accept) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("content-type", "multipart/form-data; boundary=---011000010111000001101001");
                params.put("cache-control", "no-cache");
                params.put("jesuslegaspy-token", "a1bda35a-48c7-dde3-17e0-c092d78db16a");
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                params.put("photo", new DataPart(".jpg",
                                                 getBytesFromBitmap(BitmapFactory.decodeFile(imagePath)),
                                                 "image/jpeg"));

                return params;
            }
        };
        ServerRequestQueue pQueue = ServerRequestQueue.getInstance(mCtx);
        pQueue.addToRequestQueue(multipartRequest);
    }

    public void delete(String url, PConsumer<JSONObject> data, PConsumer<VolleyError> errorData) {
        JsonObjectRequest deleteRequest = new JsonObjectRequest
                (Request.Method.DELETE,
                 baseURL + url,
                 new JSONObject(),
                 data::accept,
                 errorData::accept);
        ServerRequestQueue pQueue = ServerRequestQueue.getInstance(mCtx);
        pQueue.addToRequestQueue(deleteRequest);
    }

    // convert from bitmap to byte array
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            return stream.toByteArray();
        }
        Log.d("ServerRequestHandler", "Bitmap null");
        return null;
    }

}
