package com.nurullo.foodsharing.viewmodel_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nurullo.foodsharing.repository.FoodAdRepository
import com.nurullo.foodsharing.viewmodel.HomeViewModel
import com.nurullo.foodsharing.viewmodel.NotificationsViewModel

class NotificationsViewModelFactory(repository: FoodAdRepository): ViewModelProvider.Factory {
    lateinit var foodAdRepository: FoodAdRepository

    init {
        foodAdRepository = repository
    }
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationsViewModel::class.java)) {
            return NotificationsViewModel(foodAdRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
