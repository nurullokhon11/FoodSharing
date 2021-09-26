package com.nurullo.foodsharing.ui.fragment

interface FoodListener {
    fun onFoodClicked(id: String, pos: Int)
    fun onFoodReceived(id: String, pos: Int)
}