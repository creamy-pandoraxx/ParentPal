package com.example.parentpal.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.parentpal.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class EditActivity : AppCompatActivity() {
    private lateinit var editNama: EditText
    private lateinit var editEmail: EditText
    private lateinit var btnSimpan: TextView
    private lateinit var editFoto: ImageView

    private val REQUEST_IMAGE_GALLERY = 100
    private val REQUEST_IMAGE_CAMERA = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        editNama = findViewById(R.id.editNama)
        editEmail = findViewById(R.id.editEmail)
        btnSimpan = findViewById(R.id.btn_simpan)
        editFoto = findViewById(R.id.editFoto)


        // Mendapatkan data pengguna saat ini
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            // Mengisi EditText dengan data pengguna saat ini
            editNama.setText(currentUser.displayName)
            editEmail.setText(currentUser.email)

            // Menampilkan foto pengguna saat ini
            val photoUrl = currentUser.photoUrl
            if (photoUrl != null) {
                Picasso.get()
                    .load(photoUrl)
                    .into(editFoto)
            }
        }

        editFoto.setOnClickListener {
            // Menampilkan dialog pilihan untuk mengambil gambar dari galeri atau kamera
            val options = arrayOf<CharSequence>("Galeri", "Kamera")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Pilih Sumber Gambar")
            builder.setItems(options) { _, which ->
                when (which) {
                    0 -> openGallery()
                    1 -> openCamera()
                }
            }
            builder.show()
        }

        btnSimpan.setOnClickListener {
            // Mendapatkan nilai yang diisi pengguna
            val nama = editNama.text.toString().trim()
            val email = editEmail.text.toString().trim()

            // Memperbarui profil pengguna di Firebase Auth
            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(nama)
                .build()

            currentUser?.updateProfile(profileUpdates)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Proses pembaruan profil berhasil
                        Toast.makeText(this, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show()
                        finish() // Kembali ke activity sebelumnya
                    } else {
                        // Proses pembaruan profil gagal
                        Toast.makeText(this, "Gagal memperbarui profil", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_GALLERY -> {
                    val selectedImage = data?.data
                    if (selectedImage != null) {
                        // Mengunggah gambar dari galeri ke Firebase Auth
                        val currentUser = FirebaseAuth.getInstance().currentUser
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setPhotoUri(selectedImage)
                            .build()

                        currentUser?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Proses pembaruan foto profil berhasil
                                    Toast.makeText(this, "Foto profil berhasil diperbarui", Toast.LENGTH_SHORT).show()

                                    // Menampilkan foto baru pengguna
                                    Glide.with(this)
                                        .load(selectedImage)
                                        .into(editFoto)
                                } else {
                                    // Proses pembaruan foto profil gagal
                                    Toast.makeText(this, "Gagal memperbarui foto profil", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }
                REQUEST_IMAGE_CAMERA -> {
                    val image = data?.extras?.get("data") as Bitmap

                    // Mengunggah gambar dari kamera ke Firebase Auth
                    val currentUser = FirebaseAuth.getInstance().currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setPhotoUri(getImageUri(this, image))
                        .build()

                    currentUser?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Proses pembaruan foto profil berhasil
                                Toast.makeText(this, "Foto profil berhasil diperbarui", Toast.LENGTH_SHORT).show()

                                // Menampilkan foto baru pengguna
                                editFoto.setImageBitmap(image)
                            } else {
                                // Proses pembaruan foto profil gagal
                                Toast.makeText(this, "Gagal memperbarui foto profil", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
    }

    private fun getImageUri(context: Context, image: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, image, "Title", null)
        return Uri.parse(path)
    }
}

