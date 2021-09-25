package com.nurullo.foodsharing.viewmodel_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nurullo.foodsharing.repository.FoodAdRepository
import com.nurullo.foodsharing.repository.FoodTypeRepository
import com.nurullo.foodsharing.viewmodel.FoodTypeViewModel
import com.nurullo.foodsharing.viewmodel.HomeViewModel

class FoodTypeViewModelFactory(repository: FoodTypeRepository) : ViewModelProvider.Factory {
    lateinit var foodTypeRepository: FoodTypeRepository

    init {
        foodTypeRepository = repository
    }
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FoodTypeViewModel::class.java)) {
            return FoodTypeViewModel(foodTypeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
