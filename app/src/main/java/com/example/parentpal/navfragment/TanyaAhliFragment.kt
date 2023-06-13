   package com.example.parentpal.navfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parentpal.R
import com.example.parentpal.adapter.CategoryListAdapter
import com.example.parentpal.adapter.QuestListAdapter
import com.example.parentpal.model.Category
import com.example.parentpal.model.Question

   // TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TanyaAhliFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TanyaAhliFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var rv_category: RecyclerView
    private var list = ArrayList<Category>()
    private lateinit var rv_quest: RecyclerView
    private var listQuest = ArrayList<Question>()

    private lateinit var searchView : SearchView
    private var dataSet = ArrayList<Question>()
    private lateinit var adapter: QuestListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_tanya_ahli, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_category = requireView().findViewById(R.id.rv_category)
        rv_quest = requireView().findViewById(R.id.rv_quest)
        searchView = requireView().findViewById(R.id.sv_tanya)

        rv_category.setHasFixedSize(true)
        rv_quest.setHasFixedSize(true)

        list.addAll(listCategory)
        listQuest.addAll(listQuestion)
        showRecyclerView()
        showRvQuest()
        setupSearchView()
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText.orEmpty())
                return true
            }
        })
    }


    private val listCategory: ArrayList<Category>
        get() {
            val dataName = resources.getStringArray(R.array.data_name)
            val dataList = ArrayList<Category>()
            for (i in 0..5){
                val category = Category(dataName[i])
                dataList.add(category)
            }
            return dataList
        }

    private val listQuestion: ArrayList<Question>
        get() {
            val dataTitle = resources.getStringArray(R.array.data_title)
            val dataDate = resources.getStringArray(R.array.data_date)
            val dataTime = resources.getStringArray(R.array.data_time)
            val dataQuest = resources.getStringArray(R.array.data_quest)
            val dataImg = resources.obtainTypedArray(R.array.data_img)
            val dataListQuest = ArrayList<Question>()
            for (i in dataTitle.indices){
                val quest = Question(
                    dataImg.getResourceId(i, -1),
                    dataTitle[i],
                    dataTime[i],
                    dataDate[i],
                    dataQuest[i]
                )
                dataListQuest.add(quest)
            }
            return dataListQuest
        }

    private fun showRecyclerView(){
        rv_category.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        rv_category.adapter = CategoryListAdapter(list)

    }

    private fun showRvQuest(){
        rv_quest.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = QuestListAdapter(listQuest)
        rv_quest.adapter = adapter
    }
}