package com.nurullo.foodsharing.model.requestParams

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UpdateFoodAdPojo {
    @Expose
    @SerializedName("id")
    var id: String
        private set

    @Expose
    @SerializedName("visibility")
    var visibility: Int? = null
        private set

    @Expose
    @SerializedName("reserved")
    var reserved: Boolean? = null
        private set

    constructor(id: String, visibility: Int?) {
        this.id = id
        this.visibility = visibility
    }

    constructor(id: String, reserved: Boolean?) {
        this.id = id
        this.reserved = reserved
    }
}
