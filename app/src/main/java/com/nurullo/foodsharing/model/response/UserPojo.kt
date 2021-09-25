package com.nurullo.foodsharing.model.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_info")
class UserPojo(username: String, email: String, id: String, nickname: String, phone: String) {
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

    @PrimaryKey
    @SerializedName("id")
    @Expose
    var id: String = id
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

//    @SerializedName("foodAds")
//    @Expose
//    var foodAds: List<FoodAdPojo> = f
//        get() = field
//        set(value) { field = value }
}
