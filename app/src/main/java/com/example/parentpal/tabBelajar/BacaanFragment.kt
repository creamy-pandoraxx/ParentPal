package com.example.parentpal.tabBelajar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.parentpal.R


class BacaanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bacaan, container, false)
        val inputSandi = view.findViewById<EditText>(R.id.usia)
        inputSandi.requestFocus()

        return view

    }


}