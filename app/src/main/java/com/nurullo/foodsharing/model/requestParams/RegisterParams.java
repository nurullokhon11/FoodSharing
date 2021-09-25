package com.nurullo.foodsharing.model.requestParams;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterParams {
    @Expose
    @SerializedName("username")
    private final String username;

    @Expose
    @SerializedName("email")
    private final String email;

    @Expose
    @SerializedName("phone")
    private final String phone;

    @Expose
    @SerializedName("nickname")
    private final String nickname;

    @Expose
    @SerializedName("password")
    private final String password;

    public RegisterParams(String username, String email, String phone, String nickname, String password) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.nickname = nickname;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
