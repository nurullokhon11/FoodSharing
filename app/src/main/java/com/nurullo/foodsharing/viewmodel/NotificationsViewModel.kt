package com.nurullo.foodsharing.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nurullo.foodsharing.model.response.FoodAdPojo
import com.nurullo.foodsharing.model.response.FoodAdsResponse
import com.nurullo.foodsharing.repository.FoodAdRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationsViewModel(r: FoodAdRepository) : ViewModel() {

    private lateinit var foodAdsMutableLiveData: MutableLiveData<List<FoodAdPojo>>
    private lateinit var repository: FoodAdRepository

    init {
        this.repository = r
        foodAdsMutableLiveData = MutableLiveData<List<FoodAdPojo>>()
    }

    val foodAds: LiveData<List<FoodAdPojo>>
        get() = foodAdsMutableLiveData

    fun getAllFoodAdsByUserId(id: String?) {
        repository.getFoodAdsByUserId(id, object : Callback<List<FoodAdPojo>?> {
            override fun onResponse(
                call: Call<List<FoodAdPojo>?>,
                response: Response<List<FoodAdPojo>?>
            ) {
                foodAdsMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<List<FoodAdPojo>?>, t: Throwable) {
                System.out.println(t.toString() + " message error")
            }
        })
    }
}
