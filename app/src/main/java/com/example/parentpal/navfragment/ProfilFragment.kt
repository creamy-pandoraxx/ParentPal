package com.example.parentpal.navfragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.parentpal.R
import com.example.parentpal.activity.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ProfilFragment : Fragment() {
    private lateinit var logOutButton: TextView
    private lateinit var namaPengguna: TextView
    private lateinit var emailPengguna: TextView
    private lateinit var databaseRef: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profil, container, false)

        databaseRef = Firebase.database("https://parentpal-ff1ef-default-rtdb.asia-southeast1.firebasedatabase.app").reference.child("users")
        auth = FirebaseAuth.getInstance()

        namaPengguna = view.findViewById(R.id.namaPengguna)
        emailPengguna = view.findViewById(R.id.emailPengguna)


        // Mendapatkan ID pengguna saat ini
        val currentUser: FirebaseUser? = auth.currentUser
        val userId: String = currentUser?.uid ?: ""

        // Mendapatkan data "nama" dari Firebase Database
        val userRef: DatabaseReference = databaseRef.child(userId)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Mendapatkan data "nama" dari dataSnapshot
                val nama: String? = dataSnapshot.child("name").getValue(String::class.java)
                val email: String? = dataSnapshot.child("email").getValue(String::class.java)
                // Menggunakan data "nama" yang diperoleh
                namaPengguna.text = "$nama"
                emailPengguna.text = "$email"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Penanganan kesalahan jika terjadi
            }
        })






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