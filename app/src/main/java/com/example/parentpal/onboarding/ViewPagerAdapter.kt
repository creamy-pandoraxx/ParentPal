package com.example.parentpal.onboarding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parentpal.R

class ViewPagerAdapter(private val viewPagerFragment: List<OnboardingItem>)
    : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imageIcon = view.findViewById<ImageView>(R.id.imageSlideIcon)
        private val textDescription = view.findViewById<TextView>(R.id.description)
        private val textTittle = view.findViewById<TextView>(R.id.tittle)

        fun bind(viewPagerFragment: OnboardingItem){
            imageIcon.setImageResource(viewPagerFragment.image)
            textDescription.text = viewPagerFragment.description
            textTittle.text = viewPagerFragment.tittle

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slide_item_container,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(viewPagerFragment[position])
    }

    override fun getItemCount(): Int {
        return viewPagerFragment.size
    }
}