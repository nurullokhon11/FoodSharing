package com.nurullo.foodsharing.repository;

import com.nurullo.foodsharing.api.RetrofitClient;
import com.nurullo.foodsharing.model.response.FoodAdsResponse;

import retrofit2.Call;
import retrofit2.Callback;

public class FoodAdRepository {
    private RetrofitClient networkService;

    public FoodAdRepository(RetrofitClient networkService) {
        this.networkService = networkService;
    }

    public void getFoodAds(Long type, Callback<FoodAdsResponse> callback) {
        networkService.getFoodAds(type, callback);
    }
}
