package com.nurullo.foodsharing.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FoodAdPojo(
    @field:Expose @field:SerializedName("_id") val id: String,
    @field:Expose @field:SerializedName(
        "name"
    ) val name: String,
    @field:Expose @field:SerializedName("ownerName") val ownerName: String,
    @field:Expose @field:SerializedName(
        "address"
    ) val address: String,
    @field:Expose @field:SerializedName("description") val description: String,
    @field:Expose @field:SerializedName("weight") val weight: Long,
    @field:Expose @field:SerializedName(
        "exp_date"
    ) val expDate: String,
    @field:Expose @field:SerializedName("image") val image: String,
    @field:Expose @field:SerializedName(
        "type"
    ) val type: Long,
    @field:Expose @field:SerializedName("typeName") val typeName: String,
    @field:Expose @field:SerializedName("ownerId") val ownerId: String,
    @field:SerializedName(
        "latitude"
    ) @field:Expose val latitude: Double,
    @field:SerializedName("longitude") @field:Expose val longitude: Double,
    @field:Expose @field:SerializedName(
        "reserved"
    ) val reserved: Boolean,
    @field:Expose @field:SerializedName("__v") val v: Long
)
