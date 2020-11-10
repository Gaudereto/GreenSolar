package com.green.greensolar.Controllers;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesController {
    // Constant variables:
    static final String GREEN_PREFS = "GreenPrefs";
    static final String LOGGED_FLAG = "userlogged";
    static final String USER_NAME = "username";

    private final Context mContext;

    public SharedPreferencesController(Context context) {
        mContext = context;
    }

    public void setLogged(boolean logged) {
        mContext.getSharedPreferences(GREEN_PREFS, 0)
                .edit().putBoolean(LOGGED_FLAG, logged).apply();
    }

    public boolean isLogged() {
        return mContext.getSharedPreferences(GREEN_PREFS, 0)
                .getBoolean(LOGGED_FLAG, false);
    }

    public void saveUserName(String userName) {
            mContext.getSharedPreferences(GREEN_PREFS, 0)
                    .edit().putString(USER_NAME, userName).apply();
    }

    public String getUserName() {
        return mContext.getSharedPreferences(GREEN_PREFS, 0)
                .getString(USER_NAME, "");
    }
}
