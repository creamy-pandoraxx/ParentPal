package com.example.parentpal.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parentpal.ArtikelWebviewActivity
import com.example.parentpal.R
import com.example.parentpal.model.Article
import com.squareup.picasso.Picasso

class ArticleHomeAdapter(private val article: List<Article>): RecyclerView.Adapter<ArticleHomeAdapter.ArticleViewHolder>() {

    init {
        article.take(3)
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
        //return filteredArticleList.size
        return article.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val artikel = article[position]
        //val artikel = filteredArticleList[position]

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
}