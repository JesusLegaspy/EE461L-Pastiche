package com.pastiche.pastiche;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.test.InstrumentationRegistry;
import com.android.volley.VolleyError;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.function.Consumer;

import com.pastiche.pastiche.PObject.PSession;
import com.pastiche.pastiche.Server.ServerHandler;
import org.junit.runner.RunWith;

/**
 * Created by Khalid on 11/26/2016.
 */

public class ServerHandlerUnitTests {

    @Test
    public void checkGet() {
        ServerHandler handle = ServerHandler.getInstance(InstrumentationRegistry.getContext());
        assert(handle != null);
    }

    //checks to see if creating a user with the same name errors out.
    @Test
    public void loginTest() {
        ServerHandler handle = ServerHandler.getInstance(InstrumentationRegistry.getContext());
        Consumer<PSession> mySession = new Consumer<PSession>() {
            @Override
            public void accept(PSession pSession) {
                assert(true);
            }
        };
        Consumer<String> myError = new Consumer<String>() {
            @Override
            public void accept(String s) {
                assert(false);
            }
        };
        try {
            handle.login("kqtest", "mypassword", mySession, myError);
        } catch (Exception e) {
            assert(false);
        }
    }

    @Test
    public void falseLoginTest() {
        ServerHandler handle = ServerHandler.getInstance(InstrumentationRegistry.getContext());
        Consumer<PSession> mySession = new Consumer<PSession>() {
            @Override
            public void accept(PSession pSession) {
                assert(false);
            }
        };
        Consumer<String> myError = new Consumer<String>() {
            @Override
            public void accept(String s) {
                assert(true);
            }
        };
        try {
            handle.login("kqtest", "mypassword1", mySession, myError);
        } catch (Exception e) {
            assert(false);
        }
    }

    @Test
    public void nullPostBitmapTest() {
        ServerHandler handle = ServerHandler.getInstance(InstrumentationRegistry.getContext());
        Consumer<Integer> myResponse = new Consumer<Integer>() {
            @Override
            public void accept(Integer i) {
                assert(false);
            }
        };
        Consumer<String> myError = new Consumer<String>() {
            @Override
            public void accept(String s) {
                assert(false);
            }
        };
        Bitmap mybmp = null;
        try {
            handle.postImg(mybmp, myResponse, myError);
        } catch (Exception e) {
            assert(true);
        }
    }

    @Test
    public void nullPostImgByFilePathTest() {
        ServerHandler handle = ServerHandler.getInstance(InstrumentationRegistry.getContext());
        Consumer<Integer> myResponse = new Consumer<Integer>() {
            @Override
            public void accept(Integer i) {
                assert(false);
            }
        };
        Consumer<String> myError = new Consumer<String>() {
            @Override
            public void accept(String s) {
                assert(false);
            }
        };
        String myFilePath = null;
        try {
            handle.postImg(myFilePath, myResponse, myError);
        } catch (Exception e) {
            assert(true);
        }
    }
}