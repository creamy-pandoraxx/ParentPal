package com.example.parentpal.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import com.example.parentpal.R

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        var handler = Handler()
        handler.postDelayed({
            var goHome = Intent(this@LandingActivity, HomeActivity::class.java)
            startActivity(goHome)
            finish()
        }, 1000)
    }
}