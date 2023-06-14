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


class ProfilFragment : Fragment() {
    private lateinit var logOutButton: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profil, container, false)

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