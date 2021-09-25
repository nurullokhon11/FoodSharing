package com.nurullo.foodsharing.ui.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.nurullo.foodsharing.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = Color.WHITE

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener { item ->
            run {
                when (item.itemId) {
                    R.id.navigation_home -> {
                        navController.navigate(R.id.homeFragment)
                    }
                    R.id.navigation_notification -> {
                        navController.navigate(R.id.notificationsFragment)
                    }
                    R.id.navigation_groceries -> {

                    }
                    R.id.navigation_add -> {
                        navController.navigate(R.id.mapActivity)
                    }
                    R.id.navigation_map -> {

                    }
                }
            }
            true
        }
    }
}
