package com.example.parentpal.tabBelajar

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parentpal.R
import com.example.parentpal.adapter.ArticleListAdapter
import com.example.parentpal.adapter.ArticleVerticalAdapter
import com.example.parentpal.adapter.CategoryListAdapter
import com.example.parentpal.model.Article
import com.example.parentpal.model.Category
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class BacaanFragment : Fragment() {
    private lateinit var spAge: Spinner
    private lateinit var rvArtikel: RecyclerView
    private var listArticle = ArrayList<Article>()
    private lateinit var rvKategori: RecyclerView
    private var listCategory = ArrayList<Category>()
    private lateinit var rvArtikelVertical: RecyclerView
    private var listArticleVertical = ArrayList<Article>()
    private lateinit var svBacaan: SearchView
    private lateinit var adapterArticle: ArticleListAdapter
    private lateinit var adapterArticleVer: ArticleVerticalAdapter
    private lateinit var categoryAdapter: CategoryListAdapter
    var tittleQuery = ""
    var categoryQuery = ""
    var ageQuery = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bacaan, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val items = listOf("Semua Usia","0 – 6 tahun", "7 – 12 tahun","13 – 17 tahun")

        svBacaan = requireView().findViewById<SearchView>(R.id.sv_bacaan)

        setupSvBacaan()

        rvArtikel = requireView().findViewById(R.id.rvArticle)
        rvArtikel.setHasFixedSize(true)
        showRvArticle()

        rvArtikelVertical = requireView().findViewById(R.id.rvArtikelVer)
        rvArtikelVertical.setHasFixedSize(true)
        showRvArticleVer()

        rvKategori = requireView().findViewById(R.id.rvKategori)
        rvKategori.setHasFixedSize(true)
        listCategory.addAll(listKategori)
        showRvCategory()

        //spinnerUmur
        spAge = requireView().findViewById(R.id.sp_age)
        val itemUmur = items

        val umurAdapter = object :
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, itemUmur) {
//            override fun isEnabled(position: Int): Boolean {
//                return position != 0
//            }
            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView =
                    super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0) {
//                    val textUmur = ContextCompat.getColor(context, R.color.text_200)
//                    view.setTextColor(textUmur)
                } else {
                    val text = ContextCompat.getColor(requireContext(), R.color.text)
                    (view as TextView).setTextColor(text)
                }
                return view
            }
        }
        umurAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spAge.adapter = umurAdapter
        spAge.setSelection(1)

        spAge.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val value = parent!!.getItemAtPosition(position).toString()
                if (position > 0){
                    ageQuery = position.toString()
                    adapterArticle.filterArticle(query=tittleQuery, categoryQuery=categoryQuery, ageQuery=ageQuery)
                    adapterArticleVer.filterArticleVertical(query=tittleQuery, categoryQueryVer=categoryQuery, ageQueryVer=ageQuery)
                }else{
                    //nampilin semua item
                    adapterArticle.filterArticle()
                    adapterArticleVer.filterArticleVertical()
                }
                if (value == items[0]) {
//                    val text = ContextCompat.getColor(requireContext(), R.color.text_200)
//                    (view as TextView).setTextColor(text)
                } else {
                    val text = ContextCompat.getColor(requireContext(), R.color.text)
                    (view as TextView).setTextColor(text)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun setupSvBacaan(){
        svBacaan.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText.orEmpty()
                tittleQuery = query
                categoryQuery = query

                adapterArticle.filterArticle(query = tittleQuery, categoryQuery = categoryQuery, ageQuery=ageQuery)
                adapterArticleVer.filterArticleVertical(query = tittleQuery, categoryQueryVer = categoryQuery, ageQueryVer=ageQuery)
                return true
            }
        })
    }

    //rvArtikel
    private fun showRvArticle() {
        adapterArticle = ArticleListAdapter(listArticle)
        rvArtikel.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvArtikel.adapter = adapterArticle
        fetchArticles()
        adapterArticle.filterArticle("")
    }

    private fun fetchArticles() {
        val db = Firebase.firestore
        db.collection("artikel")
            .get()
            .addOnSuccessListener { result ->
                for (document: QueryDocumentSnapshot in result) {
                    val article = document.toObject(Article::class.java)
                    listArticle.add(article)
                }
                adapterArticle.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting articles: ${exception.message}")
            }
    }


    private fun showRvArticleVer() {
        adapterArticleVer = ArticleVerticalAdapter(listArticleVertical)
        rvArtikelVertical.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvArtikelVertical.adapter = adapterArticleVer
        fetchArticlesVer()
        adapterArticleVer.filterArticleVertical("")
    }

    private fun fetchArticlesVer(category: String? = null) {
        val db = Firebase.firestore
        listArticleVertical.clear() // Clear list sebelum mengambil artikel baru

        var collectionRef = db.collection("artikel")
        if (category != null) {
            collectionRef
                .whereEqualTo("Kategori", category)
                .get()
                .addOnSuccessListener { result ->
                    for (document: QueryDocumentSnapshot in result) {
                        val article = document.toObject(Article::class.java)
                        listArticleVertical.add(article)
                    }
                    adapterArticleVer.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.e(ContentValues.TAG, "Error getting articles: ${exception.message}")
                }
        }else {
            collectionRef.get()
                .addOnSuccessListener { result ->
                    for (document: QueryDocumentSnapshot in result) {
                        val article = document.toObject(Article::class.java)
                        listArticleVertical.add(article)
                    }
                    adapterArticleVer.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.e(ContentValues.TAG, "Error getting articles: ${exception.message}")
                }
        }
    }

    //rvKategori
    private val listKategori: ArrayList<Category>
        get() {
            val nameCategory = resources.getStringArray(R.array.data_name)
            val dataCategory = ArrayList<Category>()
            for (i in 2..13) {
                val category = Category(nameCategory[i])
                dataCategory.add(category)
            }
            return dataCategory
        }

    private fun showRvCategory() {
        rvKategori.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        categoryAdapter = CategoryListAdapter(listCategory)
        rvKategori.adapter = categoryAdapter
        categoryAdapter.setOnCategorySelectedListener { categoryName ->
            // Dapatkan artikel berdasarkan kategori yang dipilih
            adapterArticleVer.filterArticleVertical(categoryQueryVer = categoryName.toString())
            //fetchArticlesVer(categoryName)
            adapterArticleVer.filterArticleVertical(categoryQueryVer = categoryName.orEmpty())
        }
    }

}