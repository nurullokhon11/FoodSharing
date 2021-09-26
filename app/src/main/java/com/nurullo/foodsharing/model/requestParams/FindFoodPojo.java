package com.nurullo.foodsharing.model.requestParams;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FindFoodPojo {
    @Expose
    @SerializedName("ownerId")
    private String ownerId;

    @Expose
    @SerializedName("address")
    private String address;

    @Expose
    @SerializedName("description")
    private String description;

    @Expose
    @SerializedName("name")
    private String name;


    public FindFoodPojo(String ownerId, String address, String description, String name) {
        this.ownerId = ownerId;
        this.address = address;
        this.description = description;
        this.name = name;
    }
}
