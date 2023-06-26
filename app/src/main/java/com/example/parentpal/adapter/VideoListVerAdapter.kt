package com.example.parentpal.adapter

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parentpal.R
import com.example.parentpal.adapter.ArticleListAdapter
import com.example.parentpal.model.Video
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.util.Locale
import java.util.regex.Pattern

class VideoListVerAdapter(private val video: List<Video>) :
    RecyclerView.Adapter<VideoListVerAdapter.VideoViewHolder>() {
    private var filteredVideoVer: ArrayList<Video> = ArrayList(video)

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imVideo: ImageView = itemView.findViewById(R.id.ivVideo)
        val tvTitleVideo: TextView = itemView.findViewById(R.id.tvTitleVid)
        val tvCatVideo: TextView = itemView.findViewById(R.id.tvcatVideo)
        val tvAgeVideo: TextView = itemView.findViewById(R.id.tvAgeVid)
        val ivPlayVideo: ImageView = itemView.findViewById(R.id.ivPlay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_video_ver, parent, false)
        return VideoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredVideoVer.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = filteredVideoVer[position]

        Picasso.get().load(video.image).into(holder.imVideo)
        holder.tvTitleVideo.text = (video.title)
        holder.tvCatVideo.text = (video.category)
        holder.tvAgeVideo.text = (video.age)
        val videoId = video.link?.let { getYouTubeVideoId(it) }

        //thumbnail
        if (videoId != null) {
            if (videoId.isNotEmpty()) {
                val thumbnailUrl = "https://img.youtube.com/vi/$videoId/0.jpg"

                Picasso.get().load(thumbnailUrl).into(holder.imVideo)
            } else {
            }
        }

        //direct ke yt
        holder.imVideo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video.link))
            intent.setPackage("com.google.android.youtube")
            holder.itemView.context.startActivity(intent)
        }

        holder.ivPlayVideo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(video.link))
            intent.setPackage("com.google.android.youtube")
            holder.itemView.context.startActivity(intent)
        }
    }

    private fun getYouTubeVideoId(url: String): String {
        url?.let {
            val pattern =
                "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*"
            val compiledPattern = Pattern.compile(pattern)
            val matcher = compiledPattern.matcher(url)
            if (matcher.find()) {
                return matcher.group()
            }
        }
        return ""
    }


    fun filterVideoVer(titleQueryVer: String = "", categoryQueryVer: String = "", ageQueryVer: String = "") {
        val lowerCaseTitleQuery = titleQueryVer.toLowerCase(Locale.getDefault())
        val lowerCaseCategoryQuery = categoryQueryVer.toLowerCase(Locale.getDefault())
        val lowerCaseAgeQuery = ageQueryVer.toLowerCase(Locale.getDefault())

        filteredVideoVer.clear()

        if (lowerCaseTitleQuery.isEmpty() && lowerCaseCategoryQuery.isEmpty() && lowerCaseAgeQuery.isEmpty()) {
            val video = Firebase.firestore
            video.collection("tontonan")
                .get()
                .addOnSuccessListener { result ->
                    for (document: QueryDocumentSnapshot in result) {
                        val watch = document.toObject(Video::class.java)
                        filteredVideoVer.add(watch)
                    }
                    filteredVideoVer = ArrayList(filteredVideoVer)
                    notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.e(ContentValues.TAG, "Error getting video: ${exception.message}")
                }
        } else if (lowerCaseCategoryQuery.isNotEmpty() && lowerCaseTitleQuery.isNotEmpty() && lowerCaseAgeQuery.isNotEmpty()) {
            for (vid in video) {
                if (vid.category?.toLowerCase(
                        Locale.getDefault()
                    )
                        ?.contains(lowerCaseCategoryQuery) == true &&
                    vid.age_id?.toLowerCase(Locale.getDefault())
                        ?.contains(lowerCaseAgeQuery) == true
                ) {
                    filteredVideoVer.add(vid)
                } else if (vid.title?.toLowerCase(
                        Locale.getDefault()
                    )
                        ?.contains(lowerCaseTitleQuery) == true &&
                    vid.age_id?.toLowerCase(Locale.getDefault())
                        ?.contains(lowerCaseAgeQuery) == true
                ) {
                    filteredVideoVer.add(vid)
                }
            }
        } else {
            for (vid in video) {
                if (lowerCaseCategoryQuery.isNotEmpty() && vid.category?.toLowerCase(Locale.getDefault())
                        ?.contains(lowerCaseCategoryQuery) == true
                ) {
                    filteredVideoVer.add(vid)
                }

                if (lowerCaseAgeQuery.isNotEmpty() && vid.age_id?.toLowerCase(Locale.getDefault())
                        ?.contains(lowerCaseAgeQuery) == true
                ) {
                    filteredVideoVer.add(vid)
                }

                if (lowerCaseTitleQuery.isNotEmpty() && vid.title?.toLowerCase(Locale.getDefault())
                        ?.contains(lowerCaseTitleQuery) == true
                ) {
                    filteredVideoVer.add(vid)
                }
            }


        }
        notifyDataSetChanged()
    }
}
