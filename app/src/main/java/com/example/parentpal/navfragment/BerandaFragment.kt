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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.example.parentpal.model.Category
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore

class BerandaFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private lateinit var rv_kategori: RecyclerView
    private var list = ArrayList<Category>()
    private var _binding:FragmentBerandaBinding?= null
    private lateinit var imageSlider : ImageSlider
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

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