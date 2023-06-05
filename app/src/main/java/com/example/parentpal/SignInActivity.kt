package com.example.parentpal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.parentpal.form.RoleActivity
import com.google.android.material.textfield.TextInputLayout

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val textInputLayout: TextInputLayout = findViewById(R.id.textSandiLayout)
        val inputEmail: EditText = findViewById(R.id.inputEmail)
        val inputSandi: EditText = findViewById(R.id.inputSandi)
        val textDaftar: TextView = findViewById(R.id.textDaftar)
        val buttonMasuk: Button = findViewById(R.id.buttonMasuk)

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

        //button masuk
        buttonMasuk.isEnabled = false
        buttonMasuk.alpha= 0.4f
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (isInputEmpty()) {
                    buttonMasuk.isEnabled = false
                    buttonMasuk.alpha= 0.4f
                } else {
                    buttonMasuk.isEnabled = true
                    buttonMasuk.alpha= 1.0f
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        inputEmail.addTextChangedListener(textWatcher)
        inputSandi.addTextChangedListener(textWatcher)

        buttonMasuk.setOnClickListener {
            // Panggil intent untuk berpindah ke activity lain
            val intent = Intent(this, RoleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun isInputEmpty(): Boolean {
        val inputEmail: EditText = findViewById(R.id.inputEmail)
        val inputSandi: EditText = findViewById(R.id.inputSandi)
        val email = inputEmail.text.toString().trim()
        val password = inputSandi.text.toString().trim()
        return email.isEmpty() || password.isEmpty()
    }
}
