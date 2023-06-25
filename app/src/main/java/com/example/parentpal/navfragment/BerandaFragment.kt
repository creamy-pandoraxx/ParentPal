package com.example.parentpal.navfragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.parentpal.R
import com.example.parentpal.adapter.ArticleHomeAdapter
import com.example.parentpal.adapter.ArticleListAdapter
import com.example.parentpal.adapter.CategoryListAdapter
import com.example.parentpal.adapter.QuestListAdapter
import com.example.parentpal.databinding.FragmentBerandaBinding
import com.example.parentpal.model.Article
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.example.parentpal.model.Category
import com.example.parentpal.model.Question
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore

class BerandaFragment : Fragment() {

    private lateinit var rv_kategori: RecyclerView
    private var list = ArrayList<Category>()
    private lateinit var rv_artikel: RecyclerView
    private var listArtikel = ArrayList<Article>()
    private lateinit var rv_tanya: RecyclerView
    private var listTanya = ArrayList<Question>()
    private lateinit var questAdapter: QuestListAdapter

    private lateinit var nextQuest : TextView
    private var _binding:FragmentBerandaBinding?= null
    private lateinit var imageSlider : ImageSlider
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var adapterArtikel: ArticleHomeAdapter
    private lateinit var categoryAdapter: CategoryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_kategori = requireView().findViewById(R.id.rv_kategori)
        rv_kategori.setHasFixedSize(true)
        list.addAll(listCategory)
        showRecyclerView()

        adapterArtikel = ArticleHomeAdapter(listArtikel)
        rv_artikel = requireView().findViewById(R.id.rv_artikel)
        rv_artikel.setHasFixedSize(true)
        //listArtikel.addAll(listArticle)
        //listArtikel = arrayListOf()
        showRvArticle()

        rv_tanya = requireView().findViewById(R.id.rv_tanya)
        rv_tanya.setHasFixedSize(true)

        listTanya.addAll(listQuest)
        showRvQuest()

        nextQuest = requireView().findViewById(R.id.tv_next)

        nextQuest.setOnClickListener {
            val fragmentTanya = TanyaAhliFragment()

            parentFragmentManager.beginTransaction()
                .replace(R.id.layout_frame, fragmentTanya)
                .addToBackStack(null)
                .commit()

            val bottomNavigationView : BottomNavigationView = requireActivity().findViewById(R.id.bottom_navigation)
            bottomNavigationView.selectedItemId = R.id.item_3
        }


    }

    //category
    private val listCategory: ArrayList<Category>
        get() {
            val dataName = resources.getStringArray(R.array.data_name)
            val dataList = ArrayList<Category>()
            for (i in 7..13){
                val category = Category(dataName[i])
                dataList.add(category)
            }
            return dataList
        }

    private fun showRecyclerView(){
        rv_kategori.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        categoryAdapter = CategoryListAdapter(list)
        rv_kategori.adapter = categoryAdapter
        categoryAdapter.setOnCategorySelectedListener { categoryName ->
            // Dapatkan artikel berdasarkan kategori yang dipilih
            fetchArticles(categoryName)
        }
    }

    //article
    /*private val listArticle: ArrayList<Article>
        get() {
            val articleImg = resources.obtainTypedArray(R.array.article_img)
            val articleTitle = resources.getStringArray(R.array.article_title)
            val articleDate = resources.getStringArray(R.array.article_date)
            val articleCategory = resources.getStringArray(R.array.article_category)
            val dataArticle = ArrayList<Article>()
            for (i in articleTitle.indices){
                val article = Article(
                    articleImg.getResourceId(i, -1),
                    articleTitle[i],
                    articleDate[i],
                    articleCategory[i]
                )
                dataArticle.add(article)
            }
            return dataArticle
        }*/

    private fun showRvArticle() {
        adapterArtikel = ArticleHomeAdapter(listArtikel)
        rv_artikel.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_artikel.adapter = adapterArtikel
        fetchArticles()
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ...

        rv_kategori = requireView().findViewById(R.id.rv_kategori)
        rv_kategori.setHasFixedSize(true)
        list.addAll(listCategory)
        showRecyclerView()

        categoryAdapter = CategoryListAdapter(list)
        rv_kategori.adapter = categoryAdapter
    }*/

    /*private fun fetchArticles() {
        val db = Firebase.firestore
        db.collection("artikel")
            .get()
            .addOnSuccessListener { result ->
                for (document: QueryDocumentSnapshot in result) {
                    val article = document.toObject(Article::class.java)
                    listArtikel.add(article)
                }
                adapterArtikel.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e(ContentValues.TAG, "Error getting articles: ${exception.message}")
            }
    }*/
    private fun fetchArticles(category: String? = null) {
        val db = Firebase.firestore
        listArtikel.clear() // Clear list sebelum mengambil artikel baru

        var collectionRef = db.collection("artikel")
        if (category != null) {
           collectionRef
                .whereEqualTo("Kategori", category)
                .get()
                .addOnSuccessListener { result ->
                    for (document: QueryDocumentSnapshot in result) {
                        val article = document.toObject(Article::class.java)
                        listArtikel.add(article)
                    }
                    adapterArtikel.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.e(ContentValues.TAG, "Error getting articles: ${exception.message}")
                }
        }else {
            collectionRef.get()
                .addOnSuccessListener { result ->
                    for (document: QueryDocumentSnapshot in result) {
                        val article = document.toObject(Article::class.java)
                        listArtikel.add(article)
                    }
                    adapterArtikel.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.e(ContentValues.TAG, "Error getting articles: ${exception.message}")
                }
        }

    }


    //question
    private val listQuest: ArrayList<Question>
        get() {
            val dataTitle = resources.getStringArray(R.array.data_title)
            val dataDate = resources.getStringArray(R.array.data_date)
            val dataTime = resources.getStringArray(R.array.data_time)
            val dataQuest = resources.getStringArray(R.array.data_quest)
            val dataImg = resources.obtainTypedArray(R.array.data_img)
            val dataListQuest = ArrayList<Question>()
            for (i in dataTitle.indices){
                val quest = Question(
                    dataImg.getResourceId(i, -1),
                    dataTitle[i],
                    dataTime[i],
                    dataDate[i],
                    dataQuest[i]
                )
                dataListQuest.add(quest)
            }
            return dataListQuest
        }

    private fun showRvQuest(){
        rv_tanya.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        questAdapter = QuestListAdapter(listQuest.take(1).toList())
        rv_tanya.adapter = questAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        imageSlider = binding.imageSlider

        val db = Firebase.firestore
        auth = FirebaseAuth.getInstance()

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel("https://sekawan.s3.ap-southeast-2.amazonaws.com/parenting/slide1.jpeg",""))
        imageList.add(SlideModel("https://sekawan.s3.ap-southeast-2.amazonaws.com/parenting/slide2.jpeg",""))
        imageList.add(SlideModel("https://sekawan.s3.ap-southeast-2.amazonaws.com/parenting/slide3.jpeg",""))
        imageSlider.setSlideAnimation(AnimationTypes.DEPTH_SLIDE)
        imageSlider.setImageList(imageList, ScaleTypes.CENTER_INSIDE)



        // Mendapatkan Email pengguna saat ini
        val currentUser: FirebaseUser? = auth.currentUser
        val email: String = currentUser?.email ?: ""

            // Mendapatkan data "nama" dari Firestore
            val userRef = db.collection("mobile_users").document(email)
            userRef.get(Source.CACHE)
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        // Mendapatkan data "nama" dari dokumen
                        val nama: String? = documentSnapshot.getString("name")

                        // Mendapatkan string sebelum spasi
                        val firstName: String = nama?.split(" ")?.get(0) ?: ""

                        // Menggunakan string "firstName" yang diperoleh
                        binding.tvTitle.text = "Halo, $firstName"
                    } else {
                        // Dokumen tidak ditemukan
                    }
                }
                .addOnFailureListener { e ->
                    // Gagal mengambil data
                }

        return root
    }

}