package com.pastiche.pastiche;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;

/**
 * Created by jesus on 11/12/2016.
 */

public class BitmapCache {
    @SuppressLint("StaticFieldLeak") // Okay as long as this.getApplicationContext is passed as a parameter in getInstance.
    private static BitmapCache mInstance;
    @SuppressLint("StaticFieldLeak") // https://stackoverflow.com/questions/40094020/warning-do-not-place-android-context-classes-in-static-fields-this-is-a-memor
    private static Context mCtx;

    private BitmapCache (Context context){
        mCtx = context;
    }

    public static BitmapCache getInstance(Context context) {
        if (mInstance == null) {
            synchronized (BitmapCache.class) {
                if (mInstance == null) {
                    mInstance = new BitmapCache(context);
                }
            }
        }
        return mInstance;
    }

    public void testMethod(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decod
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        String imageType = options.outMimeType;
    }
}
