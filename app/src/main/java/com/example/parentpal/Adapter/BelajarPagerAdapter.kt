package com.example.parentpal.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.parentpal.tabBelajar.BacaanFragment
import com.example.parentpal.tabBelajar.TontonanFragment

class BelajarPagerAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm)  {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> BacaanFragment()
            1 -> TontonanFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Bacaan"
            1 -> "Tontonan"
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}