package com.example.parentpal.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parentpal.R
import com.example.parentpal.activity.ArticleActivity
import com.example.parentpal.model.Article
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

        holder.ivArticle.setImageResource(artikel.imgArticle)
        holder.titleArticle.text = (artikel.titleArticle)
        holder.dateArticle.text = (artikel.dateArticle)
        holder.categoryArticle.text = (artikel.categoryArticle)

        holder.ivArticle.setOnClickListener {
            val intent = Intent(holder.itemView.context, ArticleActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }
    }

        fun filterArticleVertical(query: String){
            val lowerCaseQuery = query.toLowerCase(Locale.getDefault())
            filteredArticleVertical.clear()
            if (lowerCaseQuery.isEmpty()){
                filteredArticleVertical.addAll(article)
            } else {
                for (artikel in article){
                    if (artikel.titleArticle.toLowerCase(Locale.getDefault()).contains(lowerCaseQuery) ||
                        artikel.categoryArticle.toLowerCase(Locale.getDefault()).contains(lowerCaseQuery)
                    ) {
                        filteredArticleVertical.add(artikel)
                    }
                }
            }
            notifyDataSetChanged()
        }

    }