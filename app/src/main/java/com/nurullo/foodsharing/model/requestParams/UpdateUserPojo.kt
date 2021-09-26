package com.nurullo.foodsharing.model.requestParams

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UpdateUserPojo(username: String, email: String, nickname: String, phone: String) {
    @SerializedName("username")
    @Expose
    var username: String = username
        get() = field
        set(value) { field = value }

    @SerializedName("email")
    @Expose
    var email: String = email
        get() = field
        set(value) { field = value }

    @SerializedName("nickname")
    @Expose
    var nickname: String = nickname
        get() = field
        set(value) { field = value }

    @SerializedName("phone")
    @Expose
    var phone: String = phone
        get() = field
        set(value) { field = value }
}
