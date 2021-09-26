package com.nurullo.foodsharing.model.response

import androidx.room.Entity
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

@Entity(tableName = "foodtype")
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