package com.pastiche.pastiche;

        import android.annotation.SuppressLint;
        import android.content.Context;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.util.Log;
        import android.widget.ImageView;

        import com.android.volley.NetworkResponse;
        import com.android.volley.Request;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.ImageRequest;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.StringRequest;

        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.ByteArrayOutputStream;
        import java.util.HashMap;
        import java.util.Map;
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

    public void imageGet(String url, ImageView.ScaleType scaleType, Consumer<Bitmap> data, Consumer<VolleyError> errorData){
        ImageRequest imgRequest = new ImageRequest
                (baseURL + url, data::accept, 0,0, scaleType,null, errorData::accept);
        ServerRequestQueue pQueue = ServerRequestQueue.getInstance(mCtx);
        pQueue.addToRequestQueue(imgRequest);
    }

    public void bitmapPost(String url, Bitmap bitmapPhoto, Consumer<NetworkResponse> data, Consumer<VolleyError> errorData){
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, baseURL + url, data::accept, errorData::accept) {
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
    }

    public void imagePost(String url, String imagePath, Consumer<NetworkResponse> data, Consumer<VolleyError> errorData){
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, baseURL + url, data::accept, errorData::accept) {
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
                params.put("photo", new DataPart(".jpg", getBytesFromBitmap(BitmapFactory.decodeFile(imagePath)), "image/jpeg"));

                return params;
            }
        };
    }

    // convert from bitmap to byte array
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        if(bitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            return stream.toByteArray();
        }
        Log.d("ServerRequestHandler", "Bitmap null");
        return null;
    }

}
