package com.nurullo.foodsharing.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.nurullo.foodsharing.model.response.FoodTypePojo
import com.nurullo.foodsharing.model.response.UserPojo

@Database(entities = [UserPojo::class], version = 16, exportSchema = false)
abstract class LocalPDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: LocalPDatabase? = null
        fun getInstance(context: Context): LocalPDatabase? {
            if (instance == null) {
                synchronized(LocalPDatabase::class.java) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            LocalPDatabase::class.java, "foodsharing_db"
                        )
                            .fallbackToDestructiveMigration()
                            .addCallback(object : Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                }

                                override fun onOpen(db: SupportSQLiteDatabase) {
                                    super.onOpen(db)
                                }
                            }).build()
                    }
                }
            }
            return instance
        }
    }
}
