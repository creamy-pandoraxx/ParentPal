package com.example.parentpal.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.parentpal.R
import androidx.appcompat.app.AppCompatDelegate

class StageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stage)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val iBack = findViewById<ImageView>(R.id.iv_back)
        val bNext = findViewById<Button>(R.id.btn_stage)

        iBack.setOnClickListener {
            val backRole = Intent(this@StageActivity, RoleActivity::class.java)
            startActivity(backRole)
        }

        bNext.setOnClickListener {
            val goBio = Intent(this@StageActivity, BioActivity::class.java)
            startActivity(goBio)
        }
    }
}