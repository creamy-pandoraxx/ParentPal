package com.example.parentpal.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.parentpal.R
import com.example.parentpal.activity.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RoleActivity : AppCompatActivity() {
    private lateinit var radioGroup: RadioGroup
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val bRole = findViewById<Button>(R.id.btn_role)
        radioGroup = findViewById(R.id.rg_role)
        auth = FirebaseAuth.getInstance()
        db = Firebase.firestore

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
                db.collection("mobile_users").document(userEmail).get()
                    .addOnSuccessListener { document ->
                        if (document != null && document.exists()) {
                            // Jika pengguna sudah mengisi aktivitas ini sebelumnya, lanjutkan ke aktivitas selanjutnya
                            navigateToNextActivity()
                        } else {
                            // Simpan pilihan role ke Firestore jika belum mengisi sebelumnya
                            db.collection("mobile_users").document(userEmail)
                                .set(role, SetOptions.merge())
                                .addOnSuccessListener {
                                    navigateToNextActivity()
                                }
                                .addOnFailureListener { exception ->
                                    // Menangani kesalahan saat menyimpan data
                                    Toast.makeText(this@RoleActivity, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                                    Log.e("RoleActivity", "Error saving data: ${exception.message}")
                                }
                        }
                    }
                    .addOnFailureListener { exception ->
                        // Menangani kesalahan saat memeriksa data
                        Toast.makeText(this@RoleActivity, "Gagal memeriksa data", Toast.LENGTH_SHORT).show()
                        Log.e("RoleActivity", "Error checking data: ${exception.message}")
                    }
            }
        }

        checkPreviousRoleActivity()
    }

    private fun checkPreviousRoleActivity() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userEmail = currentUser.email.toString()
            db.collection("mobile_users").document(userEmail).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Jika pengguna sudah mengisi aktivitas ini sebelumnya, langsung beralih ke aktivitas selanjutnya
                        navigateToNextActivity()
                    }
                }
                .addOnFailureListener { exception ->
                    // Menangani kesalahan saat memeriksa data
                    Toast.makeText(this@RoleActivity, "Gagal memeriksa data", Toast.LENGTH_SHORT).show()
                    Log.e("RoleActivity", "Error checking data: ${exception.message}")
                }
        }
    }

    private fun navigateToNextActivity() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userEmail = currentUser.email.toString()
            db.collection("mobile_users").document(userEmail).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val stage = document.getString("stage")
                        if (stage != null && stage.isNotBlank()) {
                            // Pengguna telah mengisi RoleActivity dan StageActivity sebelumnya
                            // Navigasi ke Activity selanjutnya (misalnya HomeActivity)
                            val intent = Intent(this@RoleActivity, HomeActivity::class.java)
                            startActivity(intent)
                            finish() // Selesai dengan menutup RoleActivity
                        } else {
                            // Pengguna hanya mengisi RoleActivity sebelumnya
                            // Navigasi ke Activity Stage
                            val intent = Intent(this@RoleActivity, StageActivity::class.java)
                            startActivity(intent)
                            finish() // Selesai dengan menutup RoleActivity
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    // Menangani kesalahan saat memeriksa data
                    Toast.makeText(this@RoleActivity, "Gagal memeriksa data", Toast.LENGTH_SHORT).show()
                    Log.e("RoleActivity", "Error checking data: ${exception.message}")
                }
        }
    }
}

