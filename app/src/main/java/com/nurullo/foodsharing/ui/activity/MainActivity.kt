package com.nurullo.foodsharing.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nurullo.foodsharing.R

class MainActivity : AppCompatActivity() {

    companion object navigation {
        lateinit var navView: BottomNavigationView
        fun updateNavigationBarState(itemId: Int) {
            val item: MenuItem = MainActivity.navView.getMenu().findItem(itemId)
            item.isChecked = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = Color.WHITE

        navView = findViewById(R.id.nav_view)
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
                    R.id.navigation_user -> {
                        navController.navigate(R.id.cabinetFragment)
                    }
                    R.id.navigation_add -> {
                        navController.navigate(R.id.mapActivity)
                    }
                    R.id.navigation_map -> {
                        navController.navigate(R.id.foodMapActivity)
                    }
                }
            }
            true
        }
    }
}
