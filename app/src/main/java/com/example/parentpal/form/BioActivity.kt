package com.example.parentpal.form

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatEditText
import com.example.parentpal.activity.LandingActivity
import com.example.parentpal.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale



class BioActivity : AppCompatActivity() {
    private lateinit var birthEditText: AppCompatEditText
    private lateinit var birthTextInputLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bio)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val back = findViewById<ImageView>(R.id.iv_backBio)
        val nextLanding = findViewById<Button>(R.id.btn_bio)
        birthEditText = findViewById(R.id.et_birth) as AppCompatEditText
        birthTextInputLayout = findViewById(R.id.il_birth)

        birthEditText.setOnClickListener {
            showDatePickerDialog()
        }

        back.setOnClickListener {
            val backStage = Intent(this@BioActivity, StageActivity::class.java)
            startActivity(backStage)
        }

        nextLanding.setOnClickListener {
            val goLanding = Intent(this@BioActivity, LandingActivity::class.java)
            startActivity(goLanding)
        }
        }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, R.style.DatePickerDialogTheme, { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            val selectedDate = formatDate(selectedYear, selectedMonth, selectedDay)
            birthEditText.setText(selectedDate)
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}

