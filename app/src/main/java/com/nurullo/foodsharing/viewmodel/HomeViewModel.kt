package com.nurullo.foodsharing.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nurullo.foodsharing.model.response.FoodAdsResponse
import com.nurullo.foodsharing.repository.FoodAdRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(repository: FoodAdRepository) : ViewModel() {
    private val repository: FoodAdRepository
    private val foodAdsMutableLiveData: MutableLiveData<FoodAdsResponse>

    init {
        this.repository = repository
        foodAdsMutableLiveData = MutableLiveData<FoodAdsResponse>()
    }

    fun getAllFoodAds() {
        repository.getFoodAds(-1L, object : Callback<FoodAdsResponse?> {
            override fun onResponse(
                call: Call<FoodAdsResponse?>,
                response: Response<FoodAdsResponse?>
            ) {
                foodAdsMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<FoodAdsResponse?>, t: Throwable) {}
        })
    }

    val foodAds: LiveData<FoodAdsResponse>
        get() = foodAdsMutableLiveData

    fun getAllFoodAdsByType(type: Long?) {
        repository.getFoodAds(type, object : Callback<FoodAdsResponse> {
            override fun onResponse(
                call: Call<FoodAdsResponse>,
                response: Response<FoodAdsResponse>
            ) {
                foodAdsMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<FoodAdsResponse>, t: Throwable) {}
        })
    }
}
