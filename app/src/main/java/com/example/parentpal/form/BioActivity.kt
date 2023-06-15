package com.example.parentpal.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import com.example.parentpal.activity.LandingActivity
import com.example.parentpal.R

class BioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bio)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val back = findViewById<ImageView>(R.id.iv_backBio)
        val nextLanding = findViewById<Button>(R.id.btn_bio)

        back.setOnClickListener {
            val backStage = Intent(this@BioActivity, StageActivity::class.java)
            startActivity(backStage)
        }

        nextLanding.setOnClickListener {
            val goLanding = Intent(this@BioActivity, LandingActivity::class.java)
            startActivity(goLanding)
        }
    }
}