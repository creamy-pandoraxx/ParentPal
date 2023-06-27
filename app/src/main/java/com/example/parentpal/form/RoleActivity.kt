package com.example.parentpal.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatDelegate
import com.example.parentpal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RoleActivity : AppCompatActivity() {


    private lateinit var radioGroup: RadioGroup
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val bRole = findViewById<Button>(R.id.btn_role)


        val db = Firebase.firestore
        auth = FirebaseAuth.getInstance()


        radioGroup = findViewById(R.id.rg_role)

        bRole.setOnClickListener {
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
            val selectedRole = selectedRadioButton.text.toString()
            val role = hashMapOf<String, Any>(
                "role" to selectedRole
            )

            // Simpan pilihan role ke Firestore
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val userEmail = currentUser.email.toString()
                db.collection("mobile_users").document(userEmail).set(role, SetOptions.merge())
            }
            val goStage = Intent(this@RoleActivity, StageActivity::class.java)
            startActivity(goStage)
        }
    }
}