package com.example.parentpal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.parentpal.form.RoleActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val textInputLayout: TextInputLayout = findViewById(R.id.textSandiLayout)
        val inputEmail: EditText = findViewById(R.id.inputEmail)
        val inputSandi: EditText = findViewById(R.id.inputSandi)
        val textDaftar: TextView = findViewById(R.id.textDaftar)
        val buttonMasuk: Button = findViewById(R.id.buttonMasuk)

        val intent = intent
        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")

        inputEmail.setText(email)
        inputSandi.setText(password)

        auth = Firebase.auth

        if (isInputEmpty()) {
            disableButtonMasuk()
        } else {
            enableButtonMasuk()
        }

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }


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

        //text daftar
        textDaftar.setOnClickListener {
            // Panggil intent untuk berpindah ke activity lain
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isInputEmpty()) {
                    disableButtonMasuk()
                } else {
                    enableButtonMasuk()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        inputEmail.addTextChangedListener(textWatcher)
        inputSandi.addTextChangedListener(textWatcher)



        val errorMessage: TextView = findViewById(R.id.errorMessage)

        buttonMasuk.setOnClickListener {
            val email = inputEmail.text.toString().trim()
            val password = inputSandi.text.toString().trim()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Login berhasil, pengguna sudah terdaftar
                        val intent = Intent(this, RoleActivity::class.java)
                        startActivity(intent)
                        finish() // Optional: Menutup SignInActivity agar tidak bisa kembali ke halaman ini setelah login berhasil

                    } else {
                        // Login gagal, tampilkan pesan error atau tindakan lainnya
                        errorMessage.text = "Email atau password salah"

                    }
                }
        }


    }


    private fun disableButtonMasuk(){
        val buttonMasuk: Button = findViewById(R.id.buttonMasuk)
        buttonMasuk.isEnabled = false
        buttonMasuk.alpha= 0.4f
    }


    private fun enableButtonMasuk() {
        val buttonMasuk: Button = findViewById(R.id.buttonMasuk)
        buttonMasuk.isEnabled = true
        buttonMasuk.alpha = 1.0f
    }


    private fun isInputEmpty(): Boolean {
        val inputEmail: EditText = findViewById(R.id.inputEmail)
        val inputSandi: EditText = findViewById(R.id.inputSandi)
        val emailCek = inputEmail.text.toString().trim()
        val passwordCek = inputSandi.text.toString().trim()

        return emailCek.isEmpty() || passwordCek.isEmpty()
    }
}
