package com.nurullo.foodsharing.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nurullo.foodsharing.model.response.FoodTypePojo
import com.nurullo.foodsharing.model.response.UserPojo

@Dao
interface UserDao {
    @Query("SELECT * FROM user_info")
    fun getUserInfo(): LiveData<UserPojo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUserInfo(user: UserPojo)
}
