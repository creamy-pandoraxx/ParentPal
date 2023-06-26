package com.example.parentpal.adapter

import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parentpal.R
import com.example.parentpal.activity.ArticleActivity
import com.example.parentpal.model.Article
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.util.Locale

class ArticleVerticalAdapter(private val article: List<Article>): RecyclerView.Adapter<ArticleVerticalAdapter.ArticleVerticalViewHolder>() {
    private var filteredArticleVertical: ArrayList<Article> = ArrayList(article)

    class ArticleVerticalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivArticle: ImageView = itemView.findViewById(R.id.ivArticle)
        val titleArticle: TextView = itemView.findViewById(R.id.tvTitleArtikel)
        val dateArticle: TextView = itemView.findViewById(R.id.tvdateArtikel)
        val categoryArticle: TextView = itemView.findViewById(R.id.tvcategoryArtikel)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleVerticalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_article_vertical, parent, false)
        return ArticleVerticalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredArticleVertical.size
    }

    override fun onBindViewHolder(holder: ArticleVerticalViewHolder, position: Int) {
        val artikel = filteredArticleVertical[position]

        Picasso.get().load(artikel.thumbnail).into(holder.ivArticle)
        //holder.ivArticle.setImageResource(artikel.thumbnail)
        holder.titleArticle.text = (artikel.judul)
        holder.dateArticle.text = (artikel.tanggal)
        holder.categoryArticle.text = (artikel.Kategori)

//        holder.ivArticle.setOnClickListener {
//            val intent = Intent(holder.itemView.context, ArticleActivity::class.java)
//            holder.itemView.context.startActivity(intent)
//        }
    }

        fun filterArticleVertical(query: String = "", categoryQueryVer: String = "", ageQueryVer: String = ""){
            val lowerCaseQuery = query.toLowerCase(Locale.getDefault())
            val lowerCaseCategoryQuery = categoryQueryVer.toLowerCase(Locale.getDefault())
            val lowerCaseAgeQuery = ageQueryVer.toLowerCase(Locale.getDefault())
//            val db = Firebase.firestore
//            var collectionRef = db.collection("artikel")
            filteredArticleVertical.clear()

            if (lowerCaseQuery.isEmpty() && lowerCaseCategoryQuery.isEmpty() && lowerCaseAgeQuery.isEmpty()){
                val db = Firebase.firestore
                db.collection("artikel")
                    .get()
                    .addOnSuccessListener { result ->
                        for (document: QueryDocumentSnapshot in result) {
                            val article = document.toObject(Article::class.java)
                            filteredArticleVertical.add(article)
                        }
                        filteredArticleVertical = ArrayList(filteredArticleVertical)
                        notifyDataSetChanged()
                    }
                    .addOnFailureListener { exception ->
                        Log.e(ContentValues.TAG, "Error getting articles: ${exception.message}")
                    }
            }else if(lowerCaseCategoryQuery.isNotEmpty() && lowerCaseQuery.isNotEmpty() && lowerCaseAgeQuery.isNotEmpty()){
                for (artikel in article) {
                    if (artikel.Kategori?.toLowerCase(
                            Locale.getDefault()
                        )
                            ?.contains(lowerCaseCategoryQuery) == true &&
                        artikel.age_id?.toLowerCase(Locale.getDefault())
                            ?.contains(lowerCaseAgeQuery) == true
                    ) {
                        filteredArticleVertical.add(artikel)
                    } else if (artikel.judul?.toLowerCase(
                            Locale.getDefault()
                        )
                            ?.contains(lowerCaseQuery) == true &&
                        artikel.age_id?.toLowerCase(Locale.getDefault())
                            ?.contains(lowerCaseAgeQuery) == true
                    ) {
                        filteredArticleVertical.add(artikel)
                    }
                }
            } else {
                for (artikel in article) {
                    if (lowerCaseCategoryQuery.isNotEmpty() && artikel.Kategori?.toLowerCase(Locale.getDefault())
                            ?.contains(lowerCaseCategoryQuery) == true
                    ) {
                        filteredArticleVertical.add(artikel)
                    }

                    if (lowerCaseAgeQuery.isNotEmpty() && artikel.age_id?.toLowerCase(Locale.getDefault())
                            ?.contains(lowerCaseAgeQuery) == true
                    ) {
                        filteredArticleVertical.add(artikel)
                    }

                    if (lowerCaseQuery.isNotEmpty() && artikel.judul?.toLowerCase(Locale.getDefault())
                            ?.contains(lowerCaseQuery) == true
                    ) {
                        filteredArticleVertical.add(artikel)
                    }
                }
            }
            notifyDataSetChanged()
        }

    }