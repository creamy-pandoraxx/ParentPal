package com.example.parentpal.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.parentpal.R
import com.example.parentpal.form.BioActivity
import com.example.parentpal.navfragment.BerandaFragment

class FaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fase)

        val backFase = findViewById<ImageView>(R.id.iv_backFase)
        val btnFase = findViewById<Button>(R.id.btn_fase)

        backFase.setOnClickListener {
            val goHome = Intent(this@FaseActivity, HomeActivity::class.java)
            startActivity(goHome)
        }
        btnFase.setOnClickListener {
            val goCek = Intent(this@FaseActivity, BioActivity::class.java)
            startActivity(goCek)
        }
    }
}