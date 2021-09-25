package com.nurullo.foodsharing.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class SessionManager {
    Context context;
    private SharedPreferences prefs;

    public SessionManager(Context context) {
        this.context = context;
    }

    public String getUsername() {
        prefs = context.getSharedPreferences(Constants.MY_SETTINGS, Context.MODE_PRIVATE);
        return prefs.getString(Constants.USERNAME, "");
    }

    public void saveUsername(String username) {
        prefs = context.getSharedPreferences(Constants.MY_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.USERNAME, username);
        editor.apply();
    }

    public String getEmail() {
        prefs = context.getSharedPreferences(Constants.MY_SETTINGS, Context.MODE_PRIVATE);
        return prefs.getString(Constants.EMAIL, "");
    }

    public void saveEmail(String email) {
        prefs = context.getSharedPreferences(Constants.MY_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.EMAIL, email);
        editor.apply();
    }

    public String getNickname() {
        prefs = context.getSharedPreferences(Constants.MY_SETTINGS, Context.MODE_PRIVATE);
        return prefs.getString(Constants.NICKNAME, "");
    }

    public void saveNickname(String nickname) {
        prefs = context.getSharedPreferences(Constants.MY_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.NICKNAME, nickname);
        editor.apply();
    }

    public String getUserId() {
        prefs = context.getSharedPreferences(Constants.MY_SETTINGS, Context.MODE_PRIVATE);
        return prefs.getString(Constants.USER_ID, "");
    }

    public void saveUserId(String id) {
        prefs = context.getSharedPreferences(Constants.MY_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Constants.USER_ID, id);
        editor.apply();
    }

    public void hideSoftKeyBoard(Context context, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
