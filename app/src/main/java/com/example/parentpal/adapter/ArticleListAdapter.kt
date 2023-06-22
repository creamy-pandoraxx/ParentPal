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
import com.example.parentpal.activity.ArticleActivity
import com.example.parentpal.model.Article
import java.util.Locale

class ArticleListAdapter(private val article: List<Article>) : RecyclerView.Adapter<ArticleListAdapter.ArticleViewHolder>() {
    private var filteredArticleList: ArrayList<Article> = ArrayList(article)

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
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val artikel = filteredArticleList[position]

        holder.imArticle.setImageResource(artikel.imgArticle)
        holder.tvTitleArticle.text = (artikel.titleArticle)
        holder.tvDateArticle.text = (artikel.dateArticle)
        holder.tvCategoryArticle.text = (artikel.categoryArticle)

        holder.imArticle.setOnClickListener {
            val intent= Intent(holder.itemView.context, ArtikelWebviewActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }
    }

    fun filterArticle(query: String){
        val lowerCaseQuery = query.toLowerCase(Locale.getDefault())
        filteredArticleList.clear()
        if (lowerCaseQuery.isEmpty()){
            filteredArticleList.addAll(article)
        } else {
            for (artikel in article){
                if (artikel.titleArticle.toLowerCase(Locale.getDefault()).contains(lowerCaseQuery) ||
                    artikel.categoryArticle.toLowerCase(Locale.getDefault()).contains(lowerCaseQuery)
                ) {
                    filteredArticleList.add(artikel)
                }
            }
        }
        notifyDataSetChanged()
    }

}
