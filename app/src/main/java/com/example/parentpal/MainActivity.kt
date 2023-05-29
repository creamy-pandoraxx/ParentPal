package com.example.parentpal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bRole = findViewById<Button>(R.id.btn_role)

        bRole.setOnClickListener {
            val goRole = Intent(this@MainActivity, RoleActivity::class.java)
            startActivity(goRole)
        }
    }
}