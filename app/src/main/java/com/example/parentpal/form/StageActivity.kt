package com.example.parentpal.form

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
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
            val selectedStage = selectedRadioButton.text.toString()
            val stage = hashMapOf<String, Any>(
                "stage" to selectedStage
            )

            val currentUser = auth.currentUser
            if (currentUser != null) {
                val userEmail = currentUser.email.toString()
                db.collection("mobile_users").document(userEmail).get()
                    .addOnSuccessListener { document ->
                        if (document != null && document.exists()) {
                            // Jika pengguna sudah mengisi aktivitas ini sebelumnya, lanjutkan ke aktivitas selanjutnya
                            navigateToNextActivity(selectedId)
                        } else {
                            // Simpan pilihan stage ke Firestore jika belum mengisi sebelumnya
                            db.collection("mobile_users").document(userEmail)
                                .set(stage, SetOptions.merge())
                                .addOnSuccessListener {
                                    navigateToNextActivity(selectedId)
                                }
                                .addOnFailureListener { exception ->
                                    // Menangani kesalahan saat menyimpan data
                                    Toast.makeText(this@StageActivity, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                                    Log.e("StageActivity", "Error saving data: ${exception.message}")
                                }
                        }
                    }
                    .addOnFailureListener { exception ->
                        // Menangani kesalahan saat memeriksa data
                        Toast.makeText(this@StageActivity, "Gagal memeriksa data", Toast.LENGTH_SHORT).show()
                        Log.e("StageActivity", "Error checking data: ${exception.message}")
                    }
            }
        }

        checkPreviousStageActivity()
    }

    private fun checkPreviousStageActivity() {
        val db = Firebase.firestore
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userEmail = currentUser.email.toString()
            db.collection("mobile_users").document(userEmail).get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        // Jika pengguna sudah mengisi aktivitas ini sebelumnya, langsung beralih ke aktivitas selanjutnya
                        val selectedId = document.getString("stage")?.let { getSelectedRadioButtonId(it) }
                        val intent = Intent(this@StageActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                .addOnFailureListener { exception ->
                    // Menangani kesalahan saat memeriksa data
                    Toast.makeText(this@StageActivity, "Gagal memeriksa data", Toast.LENGTH_SHORT).show()
                    Log.e("StageActivity", "Error checking data: ${exception.message}")
                }
        }
    }

    private fun navigateToNextActivity(selectedId: Int?) {
        if (selectedId == R.id.rb_calon) {
            // Navigasi ke Activity Main
            val intent = Intent(this, LandingActivity::class.java)
            startActivity(intent)
            finish() // Selesai dengan menutup StageActivity

        } else if (selectedId == R.id.rb_ortu) {
            // Navigasi ke Activity Biodata anak
            val intent = Intent(this, BioActivity::class.java)
            startActivity(intent)
            finish() // Selesai dengan menutup StageActivity
        }
    }

    private fun getSelectedRadioButtonId(stage: String): Int? {
        return when (stage) {
            "Calon Orang Tua" -> R.id.rb_calon
            "Orang Tua" -> R.id.rb_ortu
            else -> null
        }
    }
}

