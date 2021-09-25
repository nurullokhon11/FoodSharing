package com.nurullo.foodsharing.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.nurullo.foodsharing.api.RetrofitClient
import com.nurullo.foodsharing.db.LocalPDatabase
import com.nurullo.foodsharing.db.UserDao
import com.nurullo.foodsharing.model.requestParams.AuthParams
import com.nurullo.foodsharing.model.response.FoodTypePojo
import com.nurullo.foodsharing.model.response.UserPojo
import retrofit2.Callback

class UserRepository(c: Context) {

    private lateinit var context: Context
    private lateinit var userDao: UserDao

    init {
        this.context = c
        this.userDao = LocalPDatabase.getInstance(context)!!.userDao()
    }
    fun saveUserToDB(data: UserPojo) {
        Thread { userDao.insertUserInfo(data) }.start()
    }

    fun getUserInfoFromDB(): LiveData<UserPojo> {
        return userDao.getUserInfo()
    }

    fun loginUser(authParams: AuthParams, callback: Callback<UserPojo>) {
        RetrofitClient.getInstance(context).login(authParams, callback)
    }
}
