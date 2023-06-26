package com.example.parentpal.navfragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.parentpal.R
import com.example.parentpal.activity.EditActivity
import com.example.parentpal.activity.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso


class ProfilFragment : Fragment() {
    private lateinit var logOutButton: TextView
    private lateinit var namaPengguna: TextView
    private lateinit var emailPengguna: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var buttoEditProfil: TextView
    private lateinit var fotoPengguna: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profil, container, false)

        val db = Firebase.firestore
        auth = FirebaseAuth.getInstance()

        namaPengguna = view.findViewById(R.id.namaPengguna)
        emailPengguna = view.findViewById(R.id.emailPengguna)
        fotoPengguna = view.findViewById(R.id.fotoPengguna)
        buttoEditProfil = view.findViewById(R.id.buttoEditProfil)


        buttoEditProfil.setOnClickListener {
            val intent = Intent(activity, EditActivity::class.java)
            startActivity(intent)
        }

        // Mendapatkan ID pengguna saat ini
        val currentUser: FirebaseUser? = auth.currentUser
        val email: String = currentUser?.email ?: ""

        val photoUrl = currentUser?.photoUrl
        if (photoUrl != null) {
            Glide.with(this)
                .load(photoUrl)
                .into(fotoPengguna)
        }else{
            fotoPengguna.setImageResource(R.drawable.blank)
        }

        namaPengguna.text = currentUser?.displayName
        emailPengguna.text = email
        // Mendapatkan data "nama" dari Firebase Database
//        val userRef = db.collection("mobile_users").document(email)
//        userRef.get(Source.CACHE).addOnSuccessListener { documentSnapshot ->
//            if (documentSnapshot.exists()) {
//                val nama: String? = documentSnapshot.getString("name")
//                val email: String? = documentSnapshot.getString("email")
//                namaPengguna.text = "$nama"
//                emailPengguna.text = "$email"
//            }
//        }.addOnFailureListener { exception ->
//            // Penanganan kesalahan jika terjadi
//        }


        // Cari referensi tombol "Keluar Akun"
        logOutButton = view.findViewById(R.id.logOut)

        // Set onClickListener untuk tombol "Keluar Akun"
        logOutButton.setOnClickListener {
            // Panggil metode signOut() saat tombol diklik
            signOut()
        }

        return view



    }

    private fun signOut() {
        // Inflate custom dialog layout
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.alert_logout, null)

        // Find views in custom dialog layout
        val titleTextView = view.findViewById<TextView>(R.id.dialog_title)
        val messageTextView = view.findViewById<TextView>(R.id.dialog_message)
        val cancelButton = view.findViewById<Button>(R.id.dialog_cancel_button)
        val confirmButton = view.findViewById<Button>(R.id.dialog_confirm_button)

        // Customize the views as needed
        titleTextView.text = "Konfirmasi Keluar"
        messageTextView.text = "Anda yakin ingin keluar akun?"

        // Create AlertDialog using the custom layout
        val builder = AlertDialog.Builder(requireContext(), R.style.CustomAlertDialogStyle)
        builder.setView(view)
        val dialog = builder.create()

        // Set click listeners for the buttons
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        confirmButton.setOnClickListener {
            // Perform sign out
            FirebaseAuth.getInstance().signOut()

            // Intent ke activity sign in
            val intent = Intent(requireContext(), SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()

            dialog.dismiss()
        }

        dialog.show()
    }
}