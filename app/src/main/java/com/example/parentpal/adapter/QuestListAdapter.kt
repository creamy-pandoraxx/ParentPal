package com.example.parentpal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parentpal.R
import com.example.parentpal.model.Question
import java.util.Locale

class QuestListAdapter(private  val question: ArrayList<Question>) : RecyclerView.Adapter<QuestListAdapter.QuestViewHolder>(){
    private var filteredQuestionList : ArrayList<Question> = ArrayList(question)

    class QuestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val ivQuest : ImageView = itemView.findViewById(R.id.iv_img)
        val tvTitle : TextView = itemView.findViewById(R.id.tv_title)
        val tvTime : TextView = itemView.findViewById(R.id.tv_time)
        val tvDate : TextView = itemView.findViewById(R.id.tv_date)
        val tvQuest : TextView = itemView.findViewById(R.id.tv_quest)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_question, parent, false)
        return QuestViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredQuestionList.size
    }

    override fun onBindViewHolder(holder: QuestViewHolder, position: Int) {
        val quest = filteredQuestionList[position]
        //val (img, title, time, date, quest) = question[position]
        holder.ivQuest.setImageResource(quest.img)
        holder.tvTitle.text = quest.title
        holder.tvTime.text = quest.time
        holder.tvDate.text = quest.date
        holder.tvQuest.text = quest.quest
    }

    fun filter(query: String){
        val loweCaseQuery = query.toLowerCase(Locale.getDefault())
        filteredQuestionList.clear()
        if (loweCaseQuery.isEmpty()){
            filteredQuestionList.addAll(question)
        } else {
            for (quest in question){
                if (quest.title.toLowerCase(Locale.getDefault()).contains(loweCaseQuery) ||
                    quest.quest.toLowerCase(Locale.getDefault()).contains(loweCaseQuery)
                        ) {
                    filteredQuestionList.add(quest)
                }
            }
        }
        notifyDataSetChanged()
    }
}