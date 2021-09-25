package com.nurullo.foodsharing.model.requestParams;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthParams {
    @Expose
    @SerializedName("email")
    private final String email;

    @Expose
    @SerializedName("password")
    private final String password;

    public AuthParams(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @NonNull
    @Override
    public String toString() {
        return "AuthParams{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    private static final String TAG = AuthParams.class.toString();
}
