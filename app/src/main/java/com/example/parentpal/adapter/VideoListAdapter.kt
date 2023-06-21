package com.example.parentpal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parentpal.R
import com.example.parentpal.adapter.ArticleListAdapter
import com.example.parentpal.model.Video
import java.util.Locale

class VideoListAdapter(private val video: List<Video>) : RecyclerView.Adapter<VideoListAdapter.VideoViewHolder>() {
    private var filteredVideoList: ArrayList<Video> = ArrayList(video)

    class VideoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val imVideo: ImageView = itemView.findViewById(R.id.ivVideo)
        val tvTitleVideo: TextView = itemView.findViewById(R.id.tvTitleVideo)
        val tvDateVideo: TextView = itemView.findViewById(R.id.tvDateVideo)
        val tvViewsVideo: TextView = itemView.findViewById(R.id.tvViewsVideo)
        val tvCatVideo: TextView = itemView.findViewById(R.id.tvcategoryVideo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredVideoList.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = filteredVideoList[position]

        holder.imVideo.setImageResource(video.imgVideo)
        holder.tvTitleVideo.text = (video.titleVideo)
        holder.tvViewsVideo.text = (video.viewsVideo)
        holder.tvDateVideo.text = (video.dateVideo)
        holder.tvCatVideo.text = (video.categoryVid)
    }

    fun filterVideo(query: String){
        val lowerCaseQuery = query.toLowerCase(Locale.getDefault())
        filteredVideoList.clear()
        if (lowerCaseQuery.isEmpty()){
            filteredVideoList.addAll(video)
        } else {
            for (vid in video){
                if (vid.titleVideo.toLowerCase(Locale.getDefault()).contains(lowerCaseQuery) ||
                    vid.categoryVid.toLowerCase(Locale.getDefault()).contains(lowerCaseQuery)
                ) {
                    filteredVideoList.add(vid)
                }
            }
        }
        notifyDataSetChanged()
    }
}