package com.example.parentpal.navfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.core.view.children
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.parentpal.R
import com.example.parentpal.databinding.FragmentBerandaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class BerandaFragment : Fragment() {

    private var _binding:FragmentBerandaBinding?= null
    private lateinit var imageSlider : ImageSlider
    private val binding get() = _binding!!
    private lateinit var databaseRef: DatabaseReference
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        val root: View = binding.root
        imageSlider = binding.imageSlider

        databaseRef = Firebase.database("https://parentpal-ff1ef-default-rtdb.asia-southeast1.firebasedatabase.app").reference.child("users")
        auth = FirebaseAuth.getInstance()

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel("https://sekawan.s3.ap-southeast-2.amazonaws.com/parenting/slide1.jpeg",""))
        imageList.add(SlideModel("https://sekawan.s3.ap-southeast-2.amazonaws.com/parenting/slide2.jpeg",""))
        imageList.add(SlideModel("https://sekawan.s3.ap-southeast-2.amazonaws.com/parenting/slide3.jpeg",""))
        imageSlider.setSlideAnimation(AnimationTypes.DEPTH_SLIDE)
        imageSlider.setImageList(imageList, ScaleTypes.CENTER_INSIDE)



        // Mendapatkan ID pengguna saat ini
        val currentUser: FirebaseUser? = auth.currentUser
        val userId: String = currentUser?.uid ?: ""

        // Mendapatkan data "nama" dari Firebase Database
        val userRef: DatabaseReference = databaseRef.child(userId)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Mendapatkan data "nama" dari dataSnapshot
                val nama: String? = dataSnapshot.child("name").getValue(String::class.java)
                // Menggunakan data "nama" yang diperoleh
                binding.tvTitle.text = "Halo, $nama"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Penanganan kesalahan jika terjadi
            }
        })


        return root
    }

}