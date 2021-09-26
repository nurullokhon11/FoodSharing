package com.nurullo.foodsharing.model.requestParams

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CreateFoodAdPojo(
    @field:SerializedName("ownerId") @field:Expose val ownerId: String,
    @field:SerializedName(
        "ownerName"
    ) @field:Expose val ownerName: String,
    @field:SerializedName("name") @field:Expose val name: String,
    @field:SerializedName(
        "address"
    ) @field:Expose val address: String,
    @field:SerializedName("type") @field:Expose val type: Long,
    @field:SerializedName(
        "typeName"
    ) @field:Expose val typeName: String,
    @field:SerializedName("weight") @field:Expose val weight: Long,
    @field:SerializedName("description") @field:Expose val description: String,
    @field:SerializedName(
        "image"
    ) @field:Expose val image: String,
    @field:SerializedName("exp_date") @field:Expose val exp_date: String,
    @field:SerializedName("latitude") @field:Expose val latitude: Double,
    @field:SerializedName(
        "longitude"
    ) @field:Expose val longitude: Double,
    @field:SerializedName("reserved") @field:Expose val reserved: Boolean,
    @field:SerializedName(
        "visibility"
    ) @field:Expose val visibility: Int
)
