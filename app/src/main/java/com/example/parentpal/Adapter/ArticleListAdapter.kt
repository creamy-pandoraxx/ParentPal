package com.example.parentpal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parentpal.R
import com.example.parentpal.model.Article
import org.w3c.dom.Text

class ArticleListAdapter(private val article: ArrayList<Article>) : RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>() {
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
        return article.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val (imgArticle, titleArticle, dateArticle, categoryArticle) = article[position]
        holder.imArticle.setImageResource(imgArticle)
        holder.tvTitleArticle.text = titleArticle
        holder.tvDateArticle.text = dateArticle
        holder.tvCategoryArticle.text = categoryArticle
    }
}
