package com.example.parentpal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnRole = findViewById<Button>(R.id.BRole)

        btnRole.setOnClickListener {
            val goRole = Intent(this@MainActivity, RoleActivity::class.java)
            startActivity(goRole)
        }
    }
}