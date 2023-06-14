package com.example.parentpal.navfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
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
import com.example.parentpal.adapter.CategoryListAdapter
import com.example.parentpal.databinding.FragmentBerandaBinding
import com.example.parentpal.model.Category

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BerandaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BerandaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var rv_kategori: RecyclerView
    private var list = ArrayList<Category>()
    private var _binding:FragmentBerandaBinding?= null
    lateinit var imageSlider : ImageSlider

    private val binding get() = _binding!!

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
    }

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
        rv_kategori.adapter = CategoryListAdapter(list)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        imageSlider = binding.imageSlider
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel("https://sekawan.s3.ap-southeast-2.amazonaws.com/parenting/slide1.jpeg",""))
        imageList.add(SlideModel("https://sekawan.s3.ap-southeast-2.amazonaws.com/parenting/slide2.jpeg",""))
        imageList.add(SlideModel("https://sekawan.s3.ap-southeast-2.amazonaws.com/parenting/slide3.jpeg",""))
        imageSlider.setSlideAnimation(AnimationTypes.DEPTH_SLIDE)
        imageSlider.setImageList(imageList, ScaleTypes.CENTER_INSIDE)

        return root
    }
}