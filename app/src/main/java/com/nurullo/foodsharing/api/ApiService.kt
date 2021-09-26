package com.nurullo.foodsharing.api

import com.nurullo.foodsharing.model.requestParams.*
import com.nurullo.foodsharing.model.response.FoodAdPojo
import com.nurullo.foodsharing.model.response.FoodAdsResponse
import com.nurullo.foodsharing.model.response.FoodTypePojo
import com.nurullo.foodsharing.model.response.UserPojo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    /* Authentication */
    @POST("/api/createUser")
    fun register(@Body registerParams: RegisterParams?): Call<ResponseBody?>?

    @POST("/api/login")
    fun login(@Body authParams: AuthParams?): Call<UserPojo?>?

    @GET("/api/getFoodAds/")
    fun getFoodAds(@Query("type") type: Long): Call<FoodAdsResponse?>?

    @GET("/api/getFoodTypes")
    fun getFoodTypes(): Call<List<FoodTypePojo?>?>?

    @POST("/api/createFoodAd")
    fun createFoodAd(@Body createParams: CreateFoodAdPojo?): Call<FoodAdPojo?>?

    @POST("/api/reserveFoodAd")
    fun updateFoodAd(@Query("id") id: String?, @Body body: UpdateFoodAdPojo?): Call<ResponseBody?>?

    @GET("/api/getUserInfo")
    fun getUserInfo(@Query("userId") userId: String?): Call<UserPojo?>?

    @POST("/api/updateUserInfo")
    fun updateUserInfo(@Query("id") id: String?, @Body body: UpdateUserPojo?): Call<ResponseBody?>?

    @POST("/api/addUserAd")
    fun addUserAd(
        @Query("userId") id: String?,
        @Body createParams: CreateFoodAdPojo?
    ): Call<ResponseBody?>?

    @GET("/api/getUserFoodAds")
    fun getFoodAdsByUserId(@Query("userId") userId: String?): Call<List<FoodAdPojo?>?>?

    @POST("/api/deleteUserAd")
    fun deleteUserAd(
        @Query("userId") userId: String?,
        @Query("adId") adId: String?
    ): Call<ResponseBody?>?

    @POST("/api/updateFoodAdByQuery")
    fun updateFoodAdByQuery(
        @Body body: FindFoodPojo
    ): Call<ResponseBody?>?

    @POST("/api/changeVisibilityFoodAdByQuery")
    fun changeVisibilityFoodAdByQuery(
        @Body body: FindFoodPojo
    ): Call<ResponseBody?>?

    @POST("/api/deleteUserAdByQuery")
    fun deleteUserAdByQuery(@Query("userId") userId: String,
        @Body body: FindFoodPojo
    ): Call<ResponseBody?>?
}
