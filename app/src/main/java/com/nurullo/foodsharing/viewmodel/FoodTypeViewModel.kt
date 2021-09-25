package com.nurullo.foodsharing.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nurullo.foodsharing.model.response.FoodAdsResponse
import com.nurullo.foodsharing.model.response.FoodTypePojo
import com.nurullo.foodsharing.repository.FoodAdRepository
import com.nurullo.foodsharing.repository.FoodTypeRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodTypeViewModel(rep: FoodTypeRepository) : ViewModel() {
    private val repository: FoodTypeRepository
    private val foodTypesMutableLiveData: MutableLiveData<List<FoodTypePojo>>

    init {
        this.repository = rep
        foodTypesMutableLiveData = MutableLiveData<List<FoodTypePojo>>()
    }

    fun getFoodTypes() {
        repository.getFoodTypes(object : Callback<List<FoodTypePojo>> {
            override fun onResponse(
                call: Call<List<FoodTypePojo>>,
                response: Response<List<FoodTypePojo>>
            ) {
                foodTypesMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<List<FoodTypePojo>>, t: Throwable) {}
        })
    }

    val foodAds: LiveData<List<FoodTypePojo>>
        get() = foodTypesMutableLiveData
}
