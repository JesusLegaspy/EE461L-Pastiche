package com.pastiche.pastiche.server;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.Gson;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

/**
 * Created by jacobingalls on 11/19/16.
 */

public class PersistentCookieStore implements CookieStore, Runnable {
    CookieStore store;
    Gson gson;
    AppCompatActivity app;
    String SHARED_PREF_NAME = "PCookies";

    public PersistentCookieStore(AppCompatActivity app) {
        this.app = app;

        gson = new Gson();

        // get the default in memory cookie store
        store = new CookieManager().getCookieStore();

        // read in cookies from persistent storage
        SharedPreferences pref = app.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if(pref != null) {
            String p = pref.getString("json", "[]");
            HttpCookie cookies[] = gson.fromJson(p, HttpCookie[].class);
            for (HttpCookie c : cookies) {
                Log.d(SHARED_PREF_NAME, "Loading cookies: "+c.toString());
                store.add(URI.create(ServerRequestHandler.baseURL), c);
            }
        }

        // add a shutdown hook to write out the in memory cookies
        Runtime.getRuntime().addShutdownHook(new Thread(this));
    }

    public void run() {
        // write cookies in store to persistent storage
        SharedPreferences pref = app.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String json = gson.toJson(store.getCookies());

        for (URI uri : store.getURIs())
            Log.d(SHARED_PREF_NAME, uri.toString());

        pref.edit().putString("json", json).apply();
    }

    public void	add(URI uri, HttpCookie cookie) {
        Log.d(SHARED_PREF_NAME, "Adding cookie: "+cookie.toString());
        store.add(uri, cookie);
        run();
    }

    public List<HttpCookie> get(URI uri) {
        return store.get(uri);
    }

    public List<HttpCookie> getCookies() {
        return store.getCookies();
    }

    public List<URI> getURIs() {
        return store.getURIs();
    }

    public boolean remove(URI uri, HttpCookie cookie) {
        boolean ret = store.remove(uri, cookie);
        run();
        return ret;
    }

    public boolean removeAll()  {
        boolean ret =  store.removeAll();
        run();
        return ret;
    }
}
