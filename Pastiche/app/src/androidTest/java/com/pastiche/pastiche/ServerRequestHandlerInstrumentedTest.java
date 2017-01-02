package com.pastiche.pastiche;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.pastiche.pastiche.server.ServerRequestHandler;

import junit.framework.Assert;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.function.Consumer;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ServerRequestHandlerInstrumentedTest {
    @Test
    public void jsonPostNullConsumerTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        ServerRequestHandler sRH = ServerRequestHandler.getInstance(appContext);
        try {
            sRH.jsonPost("https://jsonplaceholder.typicode.com/posts", new JSONObject(), null, null);
        }
        catch (Exception e){
            assertEquals(NullPointerException.class, e.getClass());
        }
    }
    @Test
    public void jsonPostNullJSONTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        ServerRequestHandler sRH = ServerRequestHandler.getInstance(appContext);
        try {
            sRH.jsonPost("https://jsonplaceholder.typicode.com/posts", null, new Consumer<JSONObject>() {
                @Override
                public void accept(JSONObject jsonObject) {

                }
            }, new Consumer<VolleyError>() {
                @Override
                public void accept(VolleyError volleyError) {

                }
            });
        }
        catch (Exception e){
            assertEquals(JsonObjectRequest.class, e.getClass());
        }
    }
    @Test
    public void jsonPostNullURLTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        ServerRequestHandler sRH = ServerRequestHandler.getInstance(appContext);
        try {
            sRH.jsonPost(null, new JSONObject(), new Consumer<JSONObject>() {
                @Override
                public void accept(JSONObject jsonObject) {

                }
            }, new Consumer<VolleyError>() {
                @Override
                public void accept(VolleyError volleyError) {

                }
            });
        }
        catch (Exception e){
            assertEquals(NullPointerException.class, e.getClass());
        }
    }
    @Test
    public void jsonGetNullConsumerTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        ServerRequestHandler sRH = ServerRequestHandler.getInstance(appContext);
        try {
            sRH.jsonGet("https://jsonplaceholder.typicode.com/posts", new JSONObject(), null, null);
        }
        catch (Exception e){
            assertEquals(NullPointerException.class, e.getClass());
        }
    }
    @Test
    public void jsonGetNullJSONTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        ServerRequestHandler sRH = ServerRequestHandler.getInstance(appContext);
        try {
            sRH.jsonGet("https://jsonplaceholder.typicode.com/posts", null, new Consumer<JSONObject>() {
                @Override
                public void accept(JSONObject jsonObject) {

                }
            }, new Consumer<VolleyError>() {
                @Override
                public void accept(VolleyError volleyError) {

                }
            });
        }
        catch (Exception e){
            assertEquals(JsonObjectRequest.class, e.getClass());
        }
    }
    @Test
    public void jsonGetNullURLTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        ServerRequestHandler sRH = ServerRequestHandler.getInstance(appContext);
        try {
            sRH.jsonGet(null, new JSONObject(), new Consumer<JSONObject>() {
                @Override
                public void accept(JSONObject jsonObject) {

                }
            }, new Consumer<VolleyError>() {
                @Override
                public void accept(VolleyError volleyError) {

                }
            });
        }
        catch (Exception e){
            assertEquals(NullPointerException.class, e.getClass());
        }
    }
    @Test
    public void stringPostNullTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        ServerRequestHandler sRH = ServerRequestHandler.getInstance(appContext);
        try {
            sRH.stringPost("https://jsonplaceholder.typicode.com/posts", null, new Consumer<String>() {
                @Override
                public void accept(String jsonObject) {

                }
            }, new Consumer<VolleyError>() {
                @Override
                public void accept(VolleyError volleyError) {

                }
            });
        }
        catch (Exception e){
            Assert.fail();
        }
    }
    @Test
    public void stringGetNullTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        ServerRequestHandler sRH = ServerRequestHandler.getInstance(appContext);
        try {
            sRH.stringGet("https://jsonplaceholder.typicode.com/posts", null, new Consumer<String>() {
                @Override
                public void accept(String jsonObject) {

                }
            }, new Consumer<VolleyError>() {
                @Override
                public void accept(VolleyError volleyError) {

                }
            });
        }
        catch (Exception e){
            Assert.fail();
        }
    }
}
