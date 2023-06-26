package com.example.parentpal

import android.os.Bundle
import android.util.DisplayMetrics
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class ArtikelWebviewActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var btn_back: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artikel_webview)

        btn_back = findViewById(R.id.btn_back)

        webView = findViewById(R.id.webView)
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
        }

        // Set WebViewClient agar konten ditampilkan di dalam WebView
        webView.webViewClient = WebViewClient()

        // Dapatkan data konten HTML dari Firestore
        val artikelId = intent.getStringExtra("article_url").toString() // Ganti dengan ID artikel yang ingin ditampilkan
        val db = FirebaseFirestore.getInstance()
        val artikelRef = db.collection("artikel").document(artikelId)

        artikelRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot != null && documentSnapshot.exists()) {
                    val konten = documentSnapshot.getString("kontent")

                    // Tampilkan konten HTML di WebView jika tidak null
                    konten?.let { displayHtmlContent(it) }
                } else {
                    // Dokumen tidak ditemukan
                }
            }
            .addOnFailureListener { exception ->
                // Error saat mengambil data dari Firestore
            }

        btn_back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            // Kembali ke fragment sebelumnya
            super.onBackPressed()
        }
    }
    private fun displayHtmlContent(htmlContent: String) {
        // Load konten HTML ke WebView
        val css = "<style>img { max-width: 100%; height: auto; }</style>"
        val formattedHtmlContent = "<html><head>$css</head><body>$htmlContent</body></html>"

        // Load konten HTML ke WebView
        webView.loadDataWithBaseURL(null, formattedHtmlContent, "text/html", "UTF-8", null)
    }
}
