package com.nurullo.foodsharing.viewmodel_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nurullo.foodsharing.repository.FoodAdRepository
import com.nurullo.foodsharing.viewmodel.HomeViewModel

class HomeViewModelFactory(repository: FoodAdRepository) : ViewModelProvider.Factory {
    lateinit var foodAdRepository: FoodAdRepository

    init {
        foodAdRepository = repository
    }
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(foodAdRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
