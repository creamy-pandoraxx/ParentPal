package com.example.parentpal.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.parentpal.R
import com.example.parentpal.adapter.WebAdapter
import com.example.parentpal.model.Webinar

class BinaActivity : AppCompatActivity() {
    private lateinit var rv_bina: RecyclerView
    private var listWeb = ArrayList<Webinar>()
    private lateinit var adapterWeb: WebAdapter
    private lateinit var sv_bina: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bina)

        sv_bina = findViewById(R.id.sv_bina)
        rv_bina = findViewById(R.id.rv_bina)
        rv_bina.setHasFixedSize(true)

        listWeb.addAll(listWebinar)
        showRvWeb()
        setupSvWeb()

        val backBina = findViewById<ImageView>(R.id.iv_backBina)
        backBina.setOnClickListener {
            val goHome = Intent(this@BinaActivity, HomeActivity::class.java)
            startActivity(goHome)
        }
    }

    private fun setupSvWeb(){
        sv_bina.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapterWeb.filterWeb(newText.orEmpty())
                return true
            }
        })
    }

    private val listWebinar: ArrayList<Webinar>
        get() {
            val dataImgWeb = resources.obtainTypedArray(R.array.data_img_web)
            val dataTitleWeb = resources.getStringArray(R.array.data_title_web)
            val dataNameDoc = resources.getStringArray(R.array.data_name_doc)
            val dataDateWeb = resources.getStringArray(R.array.data_date_web)
            val dataTimeWeb = resources.getStringArray(R.array.data_time_web)
            val dataListWeb = ArrayList<Webinar>()
            for(i in dataTitleWeb.indices){
                val web = Webinar(
                    dataImgWeb.getResourceId(i, -1),
                    dataTitleWeb[i],
                    dataNameDoc[i],
                    dataDateWeb[i],
                    dataTimeWeb[i]
                )
                dataListWeb.add(web)
            }
            return dataListWeb
        }

    private fun showRvWeb(){
        rv_bina.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterWeb = WebAdapter(listWeb)
        rv_bina.adapter = adapterWeb
    }
}