package com.example.parentpal.tabBelajar

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parentpal.adapter.VideoListAdapter
import com.example.parentpal.adapter.VideoListVerAdapter
import com.example.parentpal.R
import com.example.parentpal.adapter.CategoryListAdapter
import com.example.parentpal.model.Article
import com.example.parentpal.model.Category
import com.example.parentpal.model.Video
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class TontonanFragment : Fragment() {
    private lateinit var spUsia: Spinner
    private lateinit var rvVideo: RecyclerView
    private var listVideo = ArrayList<Video>()
    private lateinit var rv_category: RecyclerView
    private var listKategori = ArrayList<Category>()
    private lateinit var rvVideoVer: RecyclerView
    private var listVideoVer = ArrayList<Video>()
    private lateinit var svTontonan: SearchView
    private lateinit var adapterTontonan: VideoListAdapter
    private lateinit var adapterTontonanVer: VideoListVerAdapter
    private lateinit var kategoriAdapter: CategoryListAdapter
    var titleQuery = ""
    var categoryQuery = ""
    var ageQuery = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment,
        return inflater.inflate(R.layout.fragment_tontonan, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val items = listOf("Semua Usia","0 – 6 tahun", "7 – 12 tahun","13 – 17 tahun")


        svTontonan = requireView().findViewById<SearchView>(R.id.sv_tontonan)

        setupRvTontonan()

        rvVideo = requireView().findViewById(R.id.rvVideo)
        rvVideo.setHasFixedSize(true)
        showRvVideo()

        rv_category = requireView().findViewById(R.id.rvCategory)
        rv_category.setHasFixedSize(true)
        listKategori.addAll(listCat)
        showRvCat()

        rvVideoVer = requireView().findViewById(R.id.rvVideoVer)
        rvVideoVer.setHasFixedSize(true)
        showRvVideoVer()

        //spinnerUmur
        spUsia = requireView().findViewById(R.id.sp_usia)
        val itemUsia = items

        val usiaAdapter = object :
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, itemUsia) {
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
//                    val textUsia = ContextCompat.getColor(context, R.color.text_200)
//                    view.setTextColor(textUsia)
                } else {
                    val text = ContextCompat.getColor(requireContext(), R.color.text)
                    (view as TextView).setTextColor(text)
                }
                return view
            }
        }
        usiaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spUsia.adapter = usiaAdapter
        spUsia.setSelection(0)

        spUsia.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val value = parent!!.getItemAtPosition(position).toString()
                if (position > 0){
                    ageQuery = position.toString()
                    adapterTontonan.filterVideo(titleQuery=titleQuery, categoryQuery=categoryQuery, ageQuery=ageQuery)
                    adapterTontonanVer.filterVideoVer(titleQueryVer=titleQuery, categoryQueryVer=categoryQuery, ageQueryVer=ageQuery)
                }else{
                    //nampilin semua item
                    adapterTontonan.filterVideo()
                    adapterTontonanVer.filterVideoVer()
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

    private fun setupRvTontonan(){
        svTontonan.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText.orEmpty()
                titleQuery = query
                categoryQuery = query

                adapterTontonan.filterVideo(titleQuery = titleQuery, categoryQuery = categoryQuery, ageQuery=ageQuery)
                adapterTontonanVer.filterVideoVer(titleQueryVer = titleQuery, categoryQueryVer = categoryQuery, ageQueryVer=ageQuery)
                return true
            }

        })
    }

    private fun showRvVideoVer(){
        adapterTontonanVer = VideoListVerAdapter(listVideoVer)
        rvVideoVer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvVideoVer.adapter = adapterTontonanVer
        fetchVideosVer()
        adapterTontonanVer.filterVideoVer("")
    }

    private fun fetchVideosVer(category: String? = null) {
        val vid = Firebase.firestore
        listVideoVer.clear()

        var collectionRef = vid.collection("tontonan")
        if (category != null) {
            collectionRef
                .whereEqualTo("category", category)
                .get()
                .addOnSuccessListener { result ->
                    for (document: QueryDocumentSnapshot in result) {
                        val videos = document.toObject(Video::class.java)
                        listVideoVer.add(videos)
                    }
                    adapterTontonanVer.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.e(ContentValues.TAG, "Error getting videos: ${exception.message}")
                }
        }else {
            collectionRef.get()
                .addOnSuccessListener { result ->
                    for (document: QueryDocumentSnapshot in result) {
                        val videos = document.toObject(Video::class.java)
                        listVideoVer.add(videos)
                    }
                    adapterTontonanVer.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.e(ContentValues.TAG, "Error getting videos: ${exception.message}")
                }
        }
    }

    private fun showRvVideo(){
        adapterTontonan = VideoListAdapter(listVideo)
        rvVideo.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvVideo.adapter = adapterTontonan
        fetchVideos()
        adapterTontonan.filterVideo("")
    }

    private fun fetchVideos() {
        val vid = Firebase.firestore
        vid.collection("tontonan")
            .get()
            .addOnSuccessListener { result ->
                for (document: QueryDocumentSnapshot in result) {
                    val watch = document.toObject(Video::class.java)
                    listVideo.add(watch)
                }
                adapterTontonan.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Error getting video: ${exception.message}")
            }
    }

    private val listCat: ArrayList<Category>
        get() {
            val nameCategory = resources.getStringArray(R.array.data_name)
            val dataCategory = ArrayList<Category>()
            for (i in 2..13){
                val category = Category(nameCategory[i])
                dataCategory.add(category)
            }
            return dataCategory
        }

    private fun showRvCat(){
        rv_category.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        kategoriAdapter = CategoryListAdapter(listKategori)
        rv_category.adapter = kategoriAdapter
        kategoriAdapter.setOnCategorySelectedListener { kategoriName ->
            adapterTontonanVer.filterVideoVer(categoryQueryVer  =kategoriName.toString())
//            fetchVideosVer(kategoriName)
            adapterTontonanVer.filterVideoVer(categoryQueryVer = kategoriName.orEmpty())
        }
    }


}