package com.example.parentpal.tabBelajar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parentpal.adapter.ArticleVerticalAdapter
import com.example.parentpal.R
import com.example.parentpal.adapter.ArticleListAdapter
import com.example.parentpal.adapter.CategoryListAdapter
import com.example.parentpal.model.Article
import com.example.parentpal.model.Category


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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bacaan, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        svBacaan = requireView().findViewById<SearchView>(R.id.sv_bacaan)
        setupSvBacaan()

        rvArtikel = requireView().findViewById(R.id.rvArticle)
        rvArtikel.setHasFixedSize(true)
        listArticle.addAll(listArtikel)
        showRvArticle()

        rvArtikelVertical = requireView().findViewById(R.id.rvArtikelVer)
        rvArtikelVertical.setHasFixedSize(true)
        listArticleVertical.addAll(listArtikelVer)
        showRvArticleVer()

        rvKategori = requireView().findViewById(R.id.rvKategori)
        rvKategori.setHasFixedSize(true)
        listCategory.addAll(listKategori)
        showRvCategory()

        //spinnerUmur
        spAge = requireView().findViewById(R.id.sp_age)
        val itemUmur = resources.getStringArray(R.array.data_age)

        val umurAdapter = object :
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, itemUmur) {
            override fun isEnabled(position: Int): Boolean {
                return position != 0
            }

            override fun getDropDownView(
                position: Int,
                convertView: View?,
                parent: ViewGroup
            ): View {
                val view: TextView =
                    super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0) {
                    val textUmur = ContextCompat.getColor(context, R.color.text_200)
                    view.setTextColor(textUmur)
                } else {
                }
                return view
            }
        }
        umurAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spAge.adapter = umurAdapter
        spAge.setSelection(1)
    }

    private fun setupSvBacaan(){
        svBacaan.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText.orEmpty()
                adapterArticle.filterArticle(query)
                adapterArticleVer.filterArticleVertical(query)
                return true
            }
        })
    }

    //rvArtikel
    private val listArtikel: ArrayList<Article>
        get() {
            val articleImg = resources.obtainTypedArray(R.array.article_img)
            val articleTitle = resources.getStringArray(R.array.article_title)
            val articleDate = resources.getStringArray(R.array.article_date)
            val articleCategory = resources.getStringArray(R.array.article_category)
            val dataArticle = ArrayList<Article>()
            for (i in articleTitle.indices) {
                val article = Article(
                    articleImg.getResourceId(i, -1),
                    articleTitle[i],
                    articleDate[i],
                    articleCategory[i]
                )
                dataArticle.add(article)
            }
            return dataArticle
        }

    private fun showRvArticle() {
        adapterArticle = ArticleListAdapter(listArticle)
        rvArtikel.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvArtikel.adapter = adapterArticle
    }

    //rvArtikelVer
    private val listArtikelVer: ArrayList<Article>
        get() {
            val articleImg = resources.obtainTypedArray(R.array.article_img)
            val articleTitle = resources.getStringArray(R.array.article_title)
            val articleDate = resources.getStringArray(R.array.article_date)
            val articleCategory = resources.getStringArray(R.array.article_category)
            val dataArticle = ArrayList<Article>()
            for (i in articleTitle.indices) {
                val article = Article(
                    articleImg.getResourceId(i, -1),
                    articleTitle[i],
                    articleDate[i],
                    articleCategory[i]
                )
                dataArticle.add(article)
            }
            return dataArticle
        }

    private fun showRvArticleVer() {
        adapterArticleVer = ArticleVerticalAdapter(listArticleVertical)
        rvArtikelVertical.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvArtikelVertical.adapter = adapterArticleVer
    }

    //rvKategori
    private val listKategori: ArrayList<Category>
        get() {
            val nameCategory = resources.getStringArray(R.array.data_name)
            val dataCategory = ArrayList<Category>()
            for (i in 1..6) {
                val category = Category(nameCategory[i])
                dataCategory.add(category)
            }
            return dataCategory
        }

    private fun showRvCategory() {
        rvKategori.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvKategori.adapter = CategoryListAdapter(listCategory)
    }
    }

