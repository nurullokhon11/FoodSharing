package com.nurullo.foodsharing.repository

import com.nurullo.foodsharing.api.RetrofitClient
import com.nurullo.foodsharing.model.response.FoodAdsResponse
import com.nurullo.foodsharing.model.response.FoodTypePojo
import retrofit2.Callback

class FoodTypeRepository(service: RetrofitClient) {
    private var networkService: RetrofitClient

    init {
        this.networkService = service
    }

    fun getFoodTypes(callback: Callback<List<FoodTypePojo>>) {
        networkService.getFoodTypes(callback)
    }
}
