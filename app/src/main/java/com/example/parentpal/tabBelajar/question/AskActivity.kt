package com.example.parentpal.tabBelajar.question

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.parentpal.R
import com.example.parentpal.navfragment.TanyaAhliFragment

class AskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask)

        val spinner : Spinner = findViewById(R.id.spn_category)
        val items= resources.getStringArray(R.array.data_name)

        val spnAge : Spinner = findViewById(R.id.spn_age)
        val itemAge= resources.getStringArray(R.array.data_age)

        val spinnerAdapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view : TextView = super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0) {
                    val textColor = ContextCompat.getColor(this@AskActivity, R.color.text_200)
                    view.setTextColor(textColor)
                } else{
                }
                return view
            }
        }

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val value = parent!!.getItemAtPosition(position).toString()
                if (value == items[0]) {
                    val text = ContextCompat.getColor(this@AskActivity, R.color.text_200)
                    (view as TextView).setTextColor(text)
                }
                else {
                    val text = ContextCompat.getColor(this@AskActivity, R.color.text)
                    (view as TextView).setTextColor(text)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        //SpinnerAge
        val ageAdapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemAge) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view : TextView = super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0) {
                    val textAge = ContextCompat.getColor(this@AskActivity, R.color.text_200)
                    view.setTextColor(textAge)
                } else{
                }
                return view
            }
        }

        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spnAge.adapter = ageAdapter

        spnAge.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedTextView = view as? TextView
                selectedTextView?.let {
                    val value = parent?.getItemAtPosition(position).toString()
                    if (value == itemAge[0]) {
                        val age = ContextCompat.getColor(this@AskActivity, R.color.text_200)
                        it.setTextColor(age)
                    } else {
                        val text = ContextCompat.getColor(this@AskActivity, R.color.text)
                        it.setTextColor(text)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Menangani kasus ketika tidak ada yang dipilih
            }
        }

    }
}