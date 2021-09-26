package com.nurullo.foodsharing.model.requestParams

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthParams(
    @field:SerializedName("email") @field:Expose val email: String, @field:SerializedName(
        "password"
    ) @field:Expose val password: String
) {

    override fun toString(): String {
        return "AuthParams{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}'
    }

    companion object {
        private val TAG = AuthParams::class.java.toString()
    }
}
