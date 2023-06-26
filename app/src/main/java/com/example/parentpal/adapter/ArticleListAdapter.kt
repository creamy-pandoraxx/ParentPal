package com.example.parentpal.adapter

import android.content.ContentValues
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parentpal.ArtikelWebviewActivity
import com.example.parentpal.R
import com.example.parentpal.model.Article
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.util.Locale

class ArticleListAdapter(private val article: List<Article>) : RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>() {
    private var filteredArticleList: ArrayList<Article> = ArrayList(article)

    init {
        filteredArticleList.addAll(article.take(3))
    }
    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imArticle: ImageView = itemView.findViewById(R.id.imgArticle)
        val tvTitleArticle: TextView = itemView.findViewById(R.id.tvTitleArticle)
        val tvDateArticle: TextView = itemView.findViewById(R.id.tvdateArticle)
        val tvCategoryArticle: TextView = itemView.findViewById(R.id.tvcategoryArticle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredArticleList.size
        //return article.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {

        val artikel = filteredArticleList[position]

        Picasso.get().load(artikel.thumbnail).into(holder.imArticle)
        //holder.imArticle.setImageResource(artikel.thumbnail)
        holder.tvTitleArticle.text = (artikel.judul)
        holder.tvDateArticle.text = (artikel.tanggal)
        holder.tvCategoryArticle.text = (artikel.Kategori)

        holder.imArticle.setOnClickListener {
            val intent = Intent(holder.itemView.context, ArtikelWebviewActivity::class.java)
            intent.putExtra("article_url", artikel.judul)
            holder.itemView.context.startActivity(intent)
        }

    }

    fun filterArticle(query: String = "", categoryQuery: String = "", ageQuery: String = "" ) {
        val lowerCaseQuery = query.lowercase(Locale.getDefault())
        val lowerCaseCategoryQuery = categoryQuery.toLowerCase(Locale.getDefault())
        val lowerCaseAgeQuery = ageQuery.toLowerCase(Locale.getDefault())

        filteredArticleList.clear()

        if (lowerCaseQuery.isEmpty()&& lowerCaseCategoryQuery.isEmpty() && lowerCaseAgeQuery.isEmpty()) {
            val db = Firebase.firestore
            db.collection("artikel")
                .get()
                .addOnSuccessListener { result ->
                    filteredArticleList.clear()
                    for (document: QueryDocumentSnapshot in result) {
                        val article = document.toObject(Article::class.java)
                        filteredArticleList.add(article)
                    }
                    filteredArticleList = ArrayList(filteredArticleList.take(3))
                    notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.e(ContentValues.TAG, "Error getting articles: ${exception.message}")
                }
        }else if(lowerCaseCategoryQuery.isNotEmpty() && lowerCaseQuery.isNotEmpty() && lowerCaseAgeQuery.isNotEmpty()){
            for (artikel in article){
                if (artikel.Kategori?.toLowerCase(
                        Locale.getDefault()
                )
                        ?.contains(lowerCaseCategoryQuery) == true &&
                    artikel.age_id?.toLowerCase(Locale.getDefault())
                        ?.contains(lowerCaseAgeQuery)==true
                ){
                    filteredArticleList.add(artikel)
                }else if (artikel.judul?.toLowerCase(
                        Locale.getDefault()
                )
                        ?.contains(lowerCaseQuery) == true &&
                    artikel.age_id?.toLowerCase(Locale.getDefault())
                        ?.contains(lowerCaseAgeQuery) == true
                ){
                    filteredArticleList.add(artikel)
                }
            }
        } else {
            for (artikel in article) {
                if (lowerCaseCategoryQuery.isNotEmpty() && artikel.Kategori?.toLowerCase(Locale.getDefault())
                        ?.contains(lowerCaseCategoryQuery) == true
                ) {
                    filteredArticleList.add(artikel)
                }

                if (lowerCaseAgeQuery.isNotEmpty() && artikel.age_id?.toLowerCase(Locale.getDefault())
                        ?.contains(lowerCaseAgeQuery) == true
                ) {
                    filteredArticleList.add(artikel)
                }

                if (lowerCaseQuery.isNotEmpty() && artikel.judul?.toLowerCase(Locale.getDefault())
                        ?.contains(lowerCaseQuery) == true
                ) {
                    filteredArticleList.add(artikel)
                }
            }
        }
        notifyDataSetChanged()
    }

}
