package com.example.parentpal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class RoleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role)

        val bRole = findViewById<Button>(R.id.btn_role)

        bRole.setOnClickListener {
            val goStage = Intent(this@RoleActivity, StageActivity::class.java)
            startActivity(goStage)
        }
    }
}