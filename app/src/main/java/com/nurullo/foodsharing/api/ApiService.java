package com.nurullo.foodsharing.api;

import com.nurullo.foodsharing.model.requestParams.AuthParams;
import com.nurullo.foodsharing.model.requestParams.CreateFoodAdPojo;
import com.nurullo.foodsharing.model.requestParams.RegisterParams;
import com.nurullo.foodsharing.model.response.FoodAdsResponse;
import com.nurullo.foodsharing.model.response.FoodTypePojo;
import com.nurullo.foodsharing.model.response.UserPojo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    /* Authentication */
    @POST("/api/createUser")
    Call<ResponseBody> register(@Body final RegisterParams registerParams);

    @POST("/api/login")
    Call<UserPojo> login(@Body final AuthParams authParams);

    @GET("/api/getFoodAds/")
    Call<FoodAdsResponse> getFoodAds(@Query("type") long type);

    @GET("/api/getFoodTypes")
    Call<List<FoodTypePojo>> getFoodTypes();

    @POST("/api/createFoodAd")
    Call<ResponseBody> createFoodAd(@Body final CreateFoodAdPojo createParams);
}
