package com.example.parentpal.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import com.example.parentpal.R

class BioChildActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var birthEditText: EditText
    private lateinit var gender: RadioButton
    private lateinit var heightEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var calculateButton: Button
    private lateinit var bmiTextView: TextView
    private lateinit var recommendationTextView: TextView
    private lateinit var nameTextView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bio_child)
        heightEditText = findViewById(R.id.et_tinggiChild)
        weightEditText = findViewById(R.id.et_beratChild)
        calculateButton = findViewById(R.id.calculateButton)
        bmiTextView = findViewById(R.id.bmiTextView)
        recommendationTextView = findViewById(R.id.recommendationTextView)
        nameTextView = findViewById(R.id.namaTextView)

        calculateButton.setOnClickListener {
            val name = nameEditText.text.toString()
            nameTextView.text = name
            calculateBMI()
        }
    }

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
}