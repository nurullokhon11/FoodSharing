package com.nurullo.foodsharing.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FoodAdsResponse(@field:Expose @field:SerializedName("items") val items: List<FoodAdPojo>)