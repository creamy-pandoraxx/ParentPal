package com.example.parentpal.navfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.parentpal.adapter.BelajarPagerAdapter
import com.example.parentpal.R
import com.google.android.material.tabs.TabLayout

class BelajarFragment : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_belajar, container, false)

        tabLayout = view.findViewById(R.id.tab_layout)
        viewPager = view.findViewById(R.id.view_pager)

        // Mengatur adapter untuk view pager
        val pagerAdapter = BelajarPagerAdapter(childFragmentManager)
        viewPager.adapter = pagerAdapter

        // Menghubungkan tab layout dengan view pager
        tabLayout.setupWithViewPager(viewPager)

        // Menghubungkan tab layout dengan view pager
        tabLayout.setupWithViewPager(viewPager)

        return view

    }

}