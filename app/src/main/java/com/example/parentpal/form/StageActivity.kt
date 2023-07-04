package com.example.parentpal.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.parentpal.R
import androidx.appcompat.app.AppCompatDelegate
import com.example.parentpal.activity.HomeActivity
import com.example.parentpal.activity.LandingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class StageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stage)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val iBack = findViewById<ImageView>(R.id.iv_back)
        val bNext = findViewById<Button>(R.id.btn_stage)
        val radioGroup = findViewById<RadioGroup>(R.id.rg_stage)

        val db = Firebase.firestore
        val auth = FirebaseAuth.getInstance()

        iBack.setOnClickListener {
            val backRole = Intent(this@StageActivity, RoleActivity::class.java)
            startActivity(backRole)
        }

        bNext.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId
            val selectedRadioButton = findViewById<RadioButton>(selectedId)
            val selectedStage= selectedRadioButton.text.toString()
            val stage = hashMapOf<String, Any>(
                "stage" to selectedStage
            )
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val userEmail = currentUser.email.toString()
                db.collection("mobile_users").document(userEmail).set(stage, SetOptions.merge())
            }

            if (selectedId == R.id.rb_calon) {
                // Navigasi ke Activity Main
                val intent = Intent(this, LandingActivity::class.java)
                startActivity(intent)

            } else if (selectedId == R.id.rb_ortu) {
                // Navigasi ke Activity Biodata anak
                val intent = Intent(this, BioActivity::class.java)
                startActivity(intent)
            }
            finish()
        }
    }
}