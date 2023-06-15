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
import com.google.firebase.ktx.Firebase

class RoleActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var userRef: DatabaseReference
    private lateinit var radioGroup: RadioGroup
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val bRole = findViewById<Button>(R.id.btn_role)

        database = Firebase.database("https://parentpal-ff1ef-default-rtdb.asia-southeast1.firebasedatabase.app")
        userRef = database.reference

        auth = FirebaseAuth.getInstance()


        radioGroup = findViewById(R.id.rg_role)

        bRole.setOnClickListener {
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
            val selectedRole = selectedRadioButton.text.toString()

            // Simpan pilihan role ke Firebase Realtime Database
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val userId = currentUser.uid
                userRef.child("users").child(userId).child("role").setValue(selectedRole)
            }



            val goStage = Intent(this@RoleActivity, StageActivity::class.java)
            startActivity(goStage)
        }
    }
}