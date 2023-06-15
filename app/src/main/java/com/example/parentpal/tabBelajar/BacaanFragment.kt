package com.example.parentpal.tabBelajar

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import com.example.parentpal.R
import com.example.parentpal.activity.RegisterActivity
import com.example.parentpal.artikel


class BacaanFragment : Fragment() {
    private lateinit var artikelBaca: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bacaan, container, false)

        artikelBaca = view.findViewById(R.id.artikel_1)
        val inputSandi = view.findViewById<EditText>(R.id.usia)
        inputSandi.requestFocus()


        artikelBaca.setOnClickListener {
            val intent = Intent(requireContext(), artikel::class.java)
            startActivity(intent)
        }

        return view

    }


}