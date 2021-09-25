package com.nurullo.foodsharing.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FoodAdsResponse {
    @SerializedName("items")
    @Expose
    private List<FoodAdPojo> items;

    public FoodAdsResponse(List<FoodAdPojo> items) {
        this.items = items;
    }

    public List<FoodAdPojo> getItems() {
        return items;
    }
}
