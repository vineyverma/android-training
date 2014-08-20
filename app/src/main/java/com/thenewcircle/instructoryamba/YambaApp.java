package com.thenewcircle.instructoryamba;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.marakana.android.yamba.clientlib.YambaClient;

/**
 * Created by geoff on 8/19/14.
 */
public class YambaApp extends Application implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "newcircle.yamba." + YambaApp.class.getSimpleName();
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    private YambaClient yambaClient;
    private static YambaApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        instance = this;
    }

    public YambaClient getYambaClient() {
        if(yambaClient == null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

            String username = prefs.getString(USERNAME, null);
            String password = prefs.getString(PASSWORD, null);
            if(username != null && username.length() != 0 &&
                password != null && password.length() != 0) {
                yambaClient = new YambaClient(username, password);
            }
            prefs.registerOnSharedPreferenceChangeListener(this);
        }
        return yambaClient;
    }

    public static YambaApp getInstance() {
        return instance;
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.d(TAG, "onSharedPreferenceChanged " + key);
        if(key.equals(USERNAME) || key.equals(PASSWORD)) {
            yambaClient = null;
        }
    }
}
