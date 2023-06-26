package com.example.parentpal.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.parentpal.R
import com.example.parentpal.model.Category
import kotlinx.coroutines.withContext

class CategoryListAdapter(private val category: ArrayList<Category>) : RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>(){

    private var selectedCategoryIndex: Int = -1
    private var onCategorySelectedListener: ((String?) -> Unit)? = null
    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return category.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val (name) = category[position]
        holder.tvName.text = name
        holder.itemView.setOnClickListener {
            val previousSelectedIndex = selectedCategoryIndex

            // Jika kategori yang sama diklik kembali, kosongkan pilihan kategori dan atur tampilan menjadi default
            if (selectedCategoryIndex == position) {
                selectedCategoryIndex = -1
                resetCategoryView(holder)
            } else {
                selectedCategoryIndex = holder.adapterPosition
                setSelectedCategoryView(holder)
            }

            // Reset warna latar belakang dan teks kategori sebelumnya
            if (previousSelectedIndex != -1 && previousSelectedIndex != selectedCategoryIndex) {
                notifyItemChanged(previousSelectedIndex)
            }

            // Ubah warna latar belakang dan teks kategori yang diklik atau kosongkan pilihan kategori
            notifyItemChanged(selectedCategoryIndex)

            // Panggil listener dengan nilai string kategori yang dipilih atau null jika tidak ada yang dipilih
            val selectedCategory = if (selectedCategoryIndex == -1) null else category[selectedCategoryIndex].name
            onCategorySelectedListener?.invoke(selectedCategory)
        }

        // Atur warna latar belakang dan teks kategori sesuai dengan kondisi pemilihan
        if (selectedCategoryIndex == position) {
            setSelectedCategoryView(holder)
        } else {
            setDefaultCategoryView(holder)
        }
    }

    private fun setSelectedCategoryView(holder: CategoryViewHolder) {
        holder.tvName.setBackgroundResource(R.drawable.shape_line_selected)
        holder.tvName.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.primary_300))
    }

    private fun setDefaultCategoryView(holder: CategoryViewHolder) {
        holder.tvName.setBackgroundResource(R.drawable.shape_line_primary)
        holder.tvName.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.primary))
    }

    private fun resetCategoryView(holder: CategoryViewHolder) {
        holder.tvName.setBackgroundResource(R.drawable.shape_line_primary)
        holder.tvName.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.primary))
    }

    // Set listener untuk kategori yang dipilih
    fun setOnCategorySelectedListener(listener: (String?) -> Unit) {

        onCategorySelectedListener = listener
    }
}