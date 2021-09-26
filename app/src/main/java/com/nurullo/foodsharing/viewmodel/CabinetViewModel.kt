package com.nurullo.foodsharing.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.snackbar.Snackbar
import com.nurullo.foodsharing.api.RetrofitClient
import com.nurullo.foodsharing.model.response.FoodAdsResponse
import com.nurullo.foodsharing.model.response.UserPojo
import com.nurullo.foodsharing.utils.SessionManager
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class CabinetViewModel(c: Context) : ViewModel() {
    private val userMutableLiveData: MutableLiveData<UserPojo>
    private val context: Context
    private lateinit var sm: SessionManager

    init {
        this.context = c
        userMutableLiveData = MutableLiveData<UserPojo>()
        sm = SessionManager(context)
    }

    fun getUserInfo(id: String) {
        RetrofitClient.getInstance(context)
            .getUserInfo(id, object : retrofit2.Callback<UserPojo> {
                override fun onResponse(
                    call: Call<UserPojo>,
                    response: Response<UserPojo>
                ) {
                    if (response.code() == 200) {
                        userMutableLiveData.postValue(response.body()!!)

                        //sm.saveUserId(response.body()?.id)
                        sm.saveUsername(response.body()?.username)
                        sm.saveEmail(response.body()?.email)
                        sm.saveNickname(response.body()?.nickname)
                        sm.savePhone(response.body()?.phone)
                    }
                }

                override fun onFailure(call: Call<UserPojo>, t: Throwable) {

                }
            })
    }

    val data: LiveData<UserPojo>
        get() = userMutableLiveData
}