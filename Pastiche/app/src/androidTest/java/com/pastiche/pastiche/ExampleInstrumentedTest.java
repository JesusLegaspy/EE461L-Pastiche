package com.pastiche.pastiche;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.pastiche.pastiche.server.ServerHandler;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertTrue;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {


    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        ServerHandler handler = ServerHandler.getInstance(appContext);

        System.out.println("hello");
        handler.login("user", "pass", data-> assertTrue(true), error-> assertTrue(false) );

//        assertEquals("com.pastiche.pastiche", appContext.getPackageName());
    }
}
