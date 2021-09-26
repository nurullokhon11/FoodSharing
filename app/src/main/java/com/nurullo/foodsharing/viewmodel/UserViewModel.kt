package com.nurullo.foodsharing.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nurullo.foodsharing.model.requestParams.AuthParams
import com.nurullo.foodsharing.model.response.FoodAdsResponse
import com.nurullo.foodsharing.model.response.UserPojo
import com.nurullo.foodsharing.repository.FoodAdRepository
import com.nurullo.foodsharing.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel(repository: UserRepository) : ViewModel() {
    private val repository: UserRepository
    private val userMutableLiveData: MutableLiveData<UserPojo>
   val successLiveData: MutableLiveData<Long> = MutableLiveData(-1L)
        get() = field

    init {
        this.repository = repository
        userMutableLiveData = MutableLiveData<UserPojo>()
    }

    fun login(authParams: AuthParams) {
        successLiveData.postValue(-1L)
        repository.loginUser(authParams, object : Callback<UserPojo> {
            override fun onResponse(call: Call<UserPojo>, response: Response<UserPojo>) {
                if (response.code() == 200) {
                   // repository.saveUserToDB(response.body()!!)
                    successLiveData.postValue(1L)
                } else {
                    successLiveData.postValue(0L)
                }
            }

            override fun onFailure(call: Call<UserPojo>, t: Throwable) {
                successLiveData.postValue(0L)
            }

        })
    }

    fun saveUserData(userPojo: UserPojo) {
        //System.out.println("HERE INSERT")
        //repository.saveUserToDB(userPojo)
    }

    fun getUserData() {
       // userMutableLiveData.postValue(repository.getUserInfoFromDB().value)
    }

    val user: LiveData<UserPojo>
        get() = userMutableLiveData
}
