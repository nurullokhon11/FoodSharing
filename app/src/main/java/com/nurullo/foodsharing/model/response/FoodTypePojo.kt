package com.nurullo.foodsharing.model.response

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class FoodTypePojo(mongo: String, n: String, id: Long) {
    @SerializedName("_id")
    @Expose
    var mongoId: String

    @SerializedName("name")
    @Expose
    lateinit var name: String

    @SerializedName("id")
    @Expose
    var id: Long

    init {
        this.mongoId = mongo
        this.name = n
        this.id = id
    }
}