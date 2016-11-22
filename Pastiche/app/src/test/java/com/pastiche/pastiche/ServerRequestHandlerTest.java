package com.pastiche.pastiche;

import android.test.mock.MockContext;
import android.util.Log;

import com.pastiche.pastiche.Server.ServerRequestHandler;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by jesus on 11/21/2016.
 */

public class ServerRequestHandlerTest {
    private ServerRequestHandler sRH;
    private final static String TAG = "SvrReqHdlrTest";

    @Before
    public void initialize(){
        sRH = ServerRequestHandler.getInstance(new MockContext());
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void postJsonTest(){
        String json = "{\n" +
                "    \"userId\": 1,\n" +
                "    \"id\": 1,\n" +
                "    \"title\": \"sunt aut facere repellat provident occaecati excepturi optio reprehenderit\",\n" +
                "    \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto\"\n" +
                "}";
        JSONObject body = null;
        try {
            body = new JSONObject(json);
        } catch (JSONException e) {
            Assert.fail();
        }
        try {
            sRH.jsonPost("https://jsonplaceholder.typicode.com/posts", body, x -> Log.d(TAG, "postJsonTest Success"), x -> Log.d(TAG, "postJsonTest Success"));
        } catch (JSONException e) {
            Assert.fail();
        }
    }
}
