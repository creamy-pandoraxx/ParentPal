package com.example.parentpal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parentpal.R
import com.example.parentpal.model.Webinar
import java.util.Locale

class WebAdapter(private val webinar: List<Webinar>) : RecyclerView.Adapter<WebAdapter.WebViewHolder>() {
    private var filterdWebList : ArrayList<Webinar> = ArrayList(webinar)

    class WebViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val ivWeb : ImageView = itemView.findViewById(R.id.ivWeb)
        val tvTitleWeb : TextView = itemView.findViewById(R.id.tvTitleWeb)
        val tvNameDoc : TextView = itemView.findViewById(R.id.tvNameDoc)
        val tvDateWeb : TextView = itemView.findViewById(R.id.tvDateWeb)
        val tvTimeWeb : TextView = itemView.findViewById(R.id.tvTimeWeb)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_web, parent, false)
        return WebViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filterdWebList.size
    }

    override fun onBindViewHolder(holder: WebViewHolder, position: Int) {
        val web = filterdWebList[position]

        holder.ivWeb.setImageResource(web.imgWeb)
        holder.tvTitleWeb.text = (web.titleWeb)
        holder.tvNameDoc.text = (web.nameDoc)
        holder.tvDateWeb.text = (web.dateWeb)
        holder.tvTimeWeb.text = (web.timeWeb)
    }

    fun filterWeb(query: String){
        val lowerCaseQuery = query.toLowerCase(Locale.getDefault())
        filterdWebList.clear()
        if (lowerCaseQuery.isEmpty()){
            filterdWebList.addAll(webinar)
        } else {
            for (web in webinar){
                if (web.titleWeb.toLowerCase(Locale.getDefault()).contains(lowerCaseQuery)
                ) {
                    filterdWebList.add(web)
                }
            }
        }
        notifyDataSetChanged()
    }


}