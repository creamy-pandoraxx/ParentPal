package com.example.parentpal.form

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatEditText
import com.example.parentpal.activity.LandingActivity
import com.example.parentpal.R
import com.example.parentpal.activity.HomeActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale



class BioActivity : AppCompatActivity() {
    private lateinit var birthEditText: AppCompatEditText
    private lateinit var birthTextInputLayout: TextInputLayout
    private lateinit var namaAnak: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var heightEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var bmiTextView: TextView
    private lateinit var recommendationTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bio)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val back = findViewById<ImageView>(R.id.iv_backBio)
        val nextLanding = findViewById<Button>(R.id.calculate)
        birthEditText = findViewById(R.id.et_birth) as AppCompatEditText
        birthTextInputLayout = findViewById(R.id.il_birthChild)
        namaAnak = findViewById(R.id.et_nama)
        radioGroup = findViewById(R.id.rg_gender)
        heightEditText = findViewById(R.id.et_tinggiChild)
        weightEditText = findViewById(R.id.et_beratChild)
        bmiTextView = findViewById(R.id.bmiTextView)
        recommendationTextView = findViewById(R.id.recommendationTextView)



        birthEditText.setOnClickListener {
            showDatePickerDialog()
        }

        back.setOnClickListener {
            val backStage = Intent(this@BioActivity, HomeActivity::class.java)
            startActivity(backStage)
        }

        nextLanding.setOnClickListener {
            val db = Firebase.firestore
            val auth = Firebase.auth
            val currentUser: FirebaseUser? = auth.currentUser
            val email: String = currentUser?.email ?: ""
            val selectedDate = birthEditText.text.toString().trim()
            val namaAnak = namaAnak.text.toString().trim()
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            val selectedRadioButton = findViewById<RadioButton>(selectedRadioButtonId)
            val selectedGender= selectedRadioButton.text.toString()
            val dataAnak = hashMapOf(
                "nama anak" to namaAnak,
                "tanggal lahir" to selectedDate,
                "jenis kelamin" to selectedGender
            )
            db.collection("mobile_users").document(email).collection("data_anak").document(namaAnak).set(dataAnak, SetOptions.merge())
            calculateBMI()
//            val goLanding = Intent(this@BioActivity, LandingActivity::class.java)
//            startActivity(goLanding)
        }
        }

    //function cek
    private fun calculateBMI() {
        val heightText = heightEditText.text.toString()
        val weightText = weightEditText.text.toString()

        if (heightText.isNotEmpty() && weightText.isNotEmpty()) {
            val height = heightText.toDouble()
            val weight = weightText.toDouble()

            val heightInMeter = height / 100
            val bmi = weight / (heightInMeter * heightInMeter)

            val bmiCategory = getBmiCategory(bmi)
            val recommendation = getRecommendation(bmiCategory)

            bmiTextView.text = "BMI anak: %.2f".format(bmi)
            recommendationTextView.text = "Kategori BMI: $bmiCategory\nRekomendasi: $recommendation"
        } else {
            bmiTextView.text = ""
            recommendationTextView.text = ""
        }
    }

    private fun getBmiCategory(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Kurus"
            bmi < 25.0 -> "Normal"
            bmi < 30.0 -> "Gemuk"
            else -> "Obesitas"
        }
    }

    private fun getRecommendation(bmiCategory: String): String {
        return when (bmiCategory) {
            "Kurus" -> "Anjurkan pola makan seimbang dan olahraga teratur."
            "Normal" -> "Pertahankan pola makan sehat dan aktivitas fisik yang cukup."
            "Gemuk" -> "Perhatikan pola makan dan rajin berolahraga untuk menjaga berat badan."
            else -> "Konsultasikan dengan dokter atau ahli gizi untuk penanganan lebih lanjut."
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

