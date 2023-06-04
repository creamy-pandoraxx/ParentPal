package com.example.parentpal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing)

        var handler = Handler()
        handler.postDelayed({
            var goHome = Intent(this@LandingActivity, HomeActivity::class.java)
            startActivity(goHome)
            finish()
        }, 1000)
    }
}