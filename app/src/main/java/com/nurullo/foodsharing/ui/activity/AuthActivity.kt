package com.nurullo.foodsharing.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nurullo.foodsharing.R
import com.nurullo.foodsharing.utils.SessionManager

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        window.statusBarColor = Color.WHITE

        val sm = SessionManager(this)
        if (sm.userId != "" && sm.userId.length > 1) {
            startActivity(Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            })
            finish()
        }
    }
}
