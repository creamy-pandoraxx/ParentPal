package com.example.parentpal.activity

//import BerandaFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.parentpal.navfragment.BelajarFragment
import com.example.parentpal.R
import com.example.parentpal.navfragment.BerandaFragment
import com.example.parentpal.navfragment.ProfilFragment
import com.example.parentpal.navfragment.TanyaAhliFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.itemIconTintList = null
        val fragment = BerandaFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.layout_frame, fragment)
            .commit()

        bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.item_1 -> {
                    val fragment = BerandaFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_frame, fragment)
                        .commit()
                    bottomNavigationView.menu.findItem(R.id.item_1).isChecked = true
                    true
                }
                R.id.item_2 -> {
                    // Respond to navigation item 2 click
                    val fragment = BelajarFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_frame, fragment)
                        .commit()
                    bottomNavigationView.menu.findItem(R.id.item_2).isChecked = true
                    true
                }
                R.id.item_3 -> {
                    val fragment = TanyaAhliFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_frame, fragment)
                        .commit()
                    bottomNavigationView.menu.findItem(R.id.item_3).isChecked = true
                    true
                }
                R.id.item_4 -> {
                    val fragment = ProfilFragment()
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.layout_frame, fragment)
                        .commit()
                    bottomNavigationView.menu.findItem(R.id.item_4).isChecked = true
                    true
                }
                else -> false
            }
        }
    }
}
