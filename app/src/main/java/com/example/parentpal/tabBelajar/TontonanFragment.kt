package com.example.parentpal.tabBelajar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import android.widget.Spinner
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parentpal. adapter.VideoListAdapter
import com.example.parentpal.adapter.VideoListVerAdapter
import com.example.parentpal.R
import com.example.parentpal.adapter.CategoryListAdapter
import com.example.parentpal.model.Article
import com.example.parentpal.model.Category
import com.example.parentpal.model.Video

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tontonan, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        svTontonan = requireView().findViewById<SearchView>(R.id.sv_tontonan)

        setupRvTontonan()

        rvVideo = requireView().findViewById(R.id.rvVideo)
        rvVideo.setHasFixedSize(true)
        listVideo.addAll(listVideoStudy)
        showRvVideo()

        rv_category = requireView().findViewById(R.id.rvCategory)
        rv_category.setHasFixedSize(true)
        listKategori.addAll(listCat)
        showRvCat()

        rvVideoVer = requireView().findViewById(R.id.rvVideoVer)
        rvVideoVer.setHasFixedSize(true)
        listVideoVer.addAll(listVideoVertical)
        showRvVideoVer()

        //spinnerUmur
        spUsia = requireView().findViewById(R.id.sp_usia)
        val itemUsia = resources.getStringArray(R.array.data_age)

        val usiaAdapter = object : ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, itemUsia) {
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
                    val textUsia = ContextCompat.getColor(context, R.color.text_200)
                    view.setTextColor(textUsia)
                } else {
                }
                return view
            }
        }
        usiaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spUsia.adapter = usiaAdapter
        spUsia.setSelection(1)
    }

    private fun setupRvTontonan(){
        svTontonan.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText.orEmpty()
                adapterTontonan.filterVideo(query)
                adapterTontonanVer.filterVideoVer(query)
                return true
            }

        })
    }

    //rvVideo
    private val listVideoVertical: ArrayList<Video>
        get() {
            val videoImg = resources.obtainTypedArray(R.array.video_img)
            val videoTitle = resources.getStringArray(R.array.video_title)
            val videoViews = resources.getStringArray(R.array.video_views)
            val videoDate = resources.getStringArray(R.array.video_date)
            val videoCat = resources.getStringArray(R.array.video_cat)
            val dataVideo = ArrayList<Video>()
            for (i in videoTitle.indices){
                val video = Video(
                    videoImg.getResourceId(i, -1),
                    videoTitle[i],
                    videoViews[i],
                    videoDate[i],
                    videoCat[i],
                )
                dataVideo.add(video)
            }
            return dataVideo
        }

    private fun showRvVideoVer(){
        adapterTontonanVer = VideoListVerAdapter(listVideoVer)
        rvVideoVer.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvVideoVer.adapter = adapterTontonanVer
    }

    //rvVideoVer
    private val listVideoStudy: ArrayList<Video>
        get() {
            val videoImg = resources.obtainTypedArray(R.array.video_img)
            val videoTitle = resources.getStringArray(R.array.video_title)
            val videoViews = resources.getStringArray(R.array.video_views)
            val videoDate = resources.getStringArray(R.array.video_date)
            val videoCat = resources.getStringArray(R.array.video_cat)
            val dataVideo = ArrayList<Video>()
            for (i in videoTitle.indices){
                val video = Video(
                    videoImg.getResourceId(i, -1),
                    videoTitle[i],
                    videoViews[i],
                    videoDate[i],
                    videoCat[i],
                )
                dataVideo.add(video)
            }
            return dataVideo
        }

    private fun showRvVideo(){
        adapterTontonan = VideoListAdapter(listVideo)
        rvVideo.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvVideo.adapter = adapterTontonan
    }

    //rvKategori
    private val listCat: ArrayList<Category>
        get() {
            val nameCategory = resources.getStringArray(R.array.data_name)
            val dataCategory = ArrayList<Category>()
            for (i in 1..6){
                val category = Category(nameCategory[i])
                dataCategory.add(category)
            }
            return dataCategory
        }

    private fun showRvCat(){
        rv_category.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rv_category.adapter = CategoryListAdapter(listKategori)
    }


}