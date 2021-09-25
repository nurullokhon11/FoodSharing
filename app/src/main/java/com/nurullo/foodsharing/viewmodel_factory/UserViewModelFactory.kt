package com.nurullo.foodsharing.viewmodel_factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nurullo.foodsharing.repository.UserRepository
import com.nurullo.foodsharing.viewmodel.UserViewModel

class UserViewModelFactory(repository: UserRepository) : ViewModelProvider.Factory {
    var userRepository: UserRepository

    init {
        userRepository = repository
    }
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
