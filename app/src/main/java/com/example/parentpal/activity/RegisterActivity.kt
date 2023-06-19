package com.example.parentpal.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.parentpal.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Logger
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val textInputLayout: TextInputLayout = findViewById(R.id.textSandiLayout)
        val textKonfirLayout: TextInputLayout = findViewById(R.id.textKonfirLayout)
        val inputEmail: EditText = findViewById(R.id.inputEmail)
        val inputNama: EditText = findViewById(R.id.inputNama)
        val inputSandi: EditText = findViewById(R.id.inputSandi)
        val konfirSandi: EditText = findViewById(R.id.inputKonfir)
        val buttonRegis: Button = findViewById(R.id.buttonRegis)
        val textMasuk: TextView = findViewById(R.id.textMasuk)

        //refrensi Firestore
        val db = Firebase.firestore

        // Sandi
        textInputLayout.setEndIconOnClickListener {
            val isPasswordVisible = inputSandi.transformationMethod is PasswordTransformationMethod
            if (isPasswordVisible) {
                // Jika password terlihat, ubah menjadi mode tersembunyi dan ganti ikon
                inputSandi.transformationMethod = HideReturnsTransformationMethod.getInstance()
                textInputLayout.endIconDrawable = ContextCompat.getDrawable(this, R.drawable.show)
            } else {
                // Jika password tersembunyi, ubah menjadi mode terlihat dan ganti ikon
                inputSandi.transformationMethod = PasswordTransformationMethod.getInstance()
                textInputLayout.endIconDrawable = ContextCompat.getDrawable(this, R.drawable.hide)
            }
        }

        // Konfir
        textKonfirLayout.setEndIconOnClickListener {
            val isPasswordVisible = konfirSandi.transformationMethod is PasswordTransformationMethod
            if (isPasswordVisible) {
                // Jika password terlihat, ubah menjadi mode tersembunyi dan ganti ikon
                konfirSandi.transformationMethod = HideReturnsTransformationMethod.getInstance()
                textKonfirLayout.endIconDrawable = ContextCompat.getDrawable(this, R.drawable.show)
            } else {
                // Jika password tersembunyi, ubah menjadi mode terlihat dan ganti ikon
                konfirSandi.transformationMethod = PasswordTransformationMethod.getInstance()
                textKonfirLayout.endIconDrawable = ContextCompat.getDrawable(this, R.drawable.hide)
            }
        }

        // Button Regis
        buttonRegis.isEnabled = false
        buttonRegis.alpha = 0.4f
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isInputEmpty()) {
                    buttonRegis.isEnabled = false
                    buttonRegis.alpha = 0.4f
                } else {
                    buttonRegis.isEnabled = true
                    buttonRegis.alpha = 1.0f
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        inputEmail.addTextChangedListener(textWatcher)
        inputSandi.addTextChangedListener(textWatcher)
        inputNama.addTextChangedListener(textWatcher)
        konfirSandi.addTextChangedListener(textWatcher)

        // Text Masuk
        textMasuk.setOnClickListener {
            // Panggil intent untuk berpindah ke activity lain
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        // Button Regis Click Listener
        buttonRegis.setOnClickListener {
            val email = inputEmail.text.toString().trim()
            val password = inputSandi.text.toString().trim()
            val nama = inputNama.text.toString().trim()

            // Registrasi pengguna dengan Firebase Authentication
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { registrationTask ->
                    if (registrationTask.isSuccessful) {
                        // Registrasi berhasil
                        val currentUser = FirebaseAuth.getInstance().currentUser
                        val userId = currentUser?.uid

                        // Simpan data pengguna dalam firestore
                        if (userId != null) {
                            val userData = HashMap<String, Any>()
                            userData["email"] = email
                            userData["name"] = nama

                            db.collection("mobile_users").document(email).set(userData)
                                .addOnCompleteListener { databaseTask ->
                                    if (databaseTask.isSuccessful) {
                                        // Data pengguna berhasil disimpan dalam Realtime Database
                                        // Lakukan tindakan selanjutnya, seperti navigasi ke halaman utama
                                        val intent = Intent(this, SignInActivity::class.java)
                                        Firebase.auth.signOut()
                                        intent.putExtra("email", email)
                                        intent.putExtra("password", password)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        // Gagal menyimpan data pengguna dalam Realtime Database
                                        Toast.makeText(this, "Gagal menyimpan data pengguna.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        }
                    } else {
                        // Registrasi gagal
                        Toast.makeText(this, "Gagal mendaftar. Silakan coba lagi.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun isInputEmpty(): Boolean {
        val inputEmail: EditText = findViewById(R.id.inputEmail)
        val inputSandi: EditText = findViewById(R.id.inputSandi)
        val inputNama: EditText = findViewById(R.id.inputNama)
        val konfirSandi: EditText = findViewById(R.id.inputKonfir)

        val email = inputEmail.text.toString().trim()
        val password = inputSandi.text.toString().trim()
        val nama = inputNama.text.toString().trim()
        val konfir = konfirSandi.text.toString().trim()
        return email.isEmpty() || password.isEmpty() || nama.isEmpty() || konfir.isEmpty() || password != konfir
    }
}
