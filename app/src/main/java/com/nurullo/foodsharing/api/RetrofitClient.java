package com.nurullo.foodsharing.api;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.nurullo.foodsharing.model.requestParams.AuthParams;
import com.nurullo.foodsharing.model.requestParams.CreateFoodAdPojo;
import com.nurullo.foodsharing.model.requestParams.RegisterParams;
import com.nurullo.foodsharing.model.response.FoodAdsResponse;
import com.nurullo.foodsharing.model.response.FoodTypePojo;
import com.nurullo.foodsharing.model.response.UserPojo;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final Retrofit.Builder retrofitBuilder;
    private final OkHttpClient.Builder okHttpBuilder;
    private ApiService apiService;

    private static RetrofitClient INSTANCE;

    // "https://10.0.2.2:8080/";
    public static final String BASE_URL = "http://192.168.22.181:8080/";

    private RetrofitClient(@NonNull final Context context) {
        this.okHttpBuilder = new OkHttpClient.Builder()
                .connectTimeout(60L, TimeUnit.SECONDS)
                .readTimeout(60L, TimeUnit.SECONDS)
                .writeTimeout(60L, TimeUnit.SECONDS)
                .callTimeout(60L, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        this.retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                                .setLenient()
                                .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context1)
                                        -> new Date(json.getAsJsonPrimitive().getAsLong()))
                                .create()));

        apiService = retrofitBuilder.client(okHttpBuilder.build()).build().create(ApiService.class);
    }

    public static RetrofitClient getInstance(@NonNull final Context context) {
        if (INSTANCE == null) {
            synchronized (RetrofitClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RetrofitClient(context);
                }
            }
        }
        return INSTANCE;
    }

    /* Authentication */
    public void register(final RegisterParams registerParams, final Callback<ResponseBody> callback) {
        apiService.register(registerParams).enqueue(callback);
    }

    public void login(final AuthParams authParams, final Callback<UserPojo> callback) {
        apiService.login(authParams).enqueue(callback);
    }

    public void getFoodAds(final Long type, final Callback<FoodAdsResponse> callback) {
        apiService.getFoodAds(type).enqueue(callback);
    }

    public void getFoodTypes(final Callback<List<FoodTypePojo>> callback) {
        apiService.getFoodTypes().enqueue(callback);
    }

    public void createFoodAd(final CreateFoodAdPojo createParams, final Callback<ResponseBody> callback) {
        apiService.createFoodAd(createParams).enqueue(callback);
    }

    private static final String TAG = RetrofitClient.class.toString();
}
