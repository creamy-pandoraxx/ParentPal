package com.example.parentpal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class BioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bio)

        val back = findViewById<ImageView>(R.id.iv_backBio)

        back.setOnClickListener {
            val backStage = Intent(this@BioActivity, StageActivity::class.java)
            startActivity(backStage)
        }
    }
}