package com.nurullo.foodsharing.model.requestParams

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.nurullo.foodsharing.model.response.FoodAdPojo

class RegisterParams     constructor (username: kotlin.String, email: kotlin.String, phone: kotlin.String, nickname: kotlin.String, password: kotlin.String){
    @Expose @SerializedName("username")
    val  username: kotlin.String

    @Expose @SerializedName("email")
    val  email: kotlin.String

    @Expose @SerializedName("phone")
    val  phone: kotlin.String

    @Expose @SerializedName("nickname")
    val  nickname: kotlin.String

    @Expose @SerializedName("password")
    val  password: kotlin.String

    @Expose @SerializedName("foodAds")
    private  val  foodAds: kotlin.collections.MutableList<FoodAdPojo> = java.util.ArrayList<FoodAdPojo>()

    init {
        this.username = username
        this.email = email
        this.phone = phone
        this.nickname = nickname
        this.password = password
        foodAds.add(
            FoodAdPojo("", "", "", "", "", 0L,
            "", "", -2L, "", "",
            0.0, 0.0, false, 0L)
        )
    }
}
