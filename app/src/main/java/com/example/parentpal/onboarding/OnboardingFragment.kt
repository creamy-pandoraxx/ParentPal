package com.example.parentpal.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.example.parentpal.PreferenceManager
import com.example.parentpal.R
import com.example.parentpal.RegisterActivity
import com.example.parentpal.SignInActivity

class OnboardingFragment : Fragment() {
    private lateinit var preferenceManager: PreferenceManager
    private val viewPagerAdapter = ViewPagerAdapter(
        listOf(
            OnboardingItem(
                R.drawable.onbor1,
                "Halo!",
                "Selamat datang di ParentPal, teman terbaik bagi para orangtua."
            ),
            OnboardingItem(
                R.drawable.onbor2,
                "Ayo, Belajar Bersama Kami!",
                "Bangun hubungan harmonis dengan anak dengan mempelajari karakter mereka"
            ),
            OnboardingItem(
                R.drawable.onbor3,
                "Tanya dan cari solusi dari para ahli!",
                "Cari solusi dari segala jenis kendala yang kamu alami dengan berbagai fitur yang disediakan"
            )
        )
    )

    private lateinit var buttonNext: Button
    private lateinit var textSkip: TextView
    private lateinit var introSliderViewPager: ViewPager2
    private lateinit var indicatorContainer: LinearLayout
    private lateinit var buttonBack: TextView
    private lateinit var buttonSkip: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_onboarding, container, false)

        buttonNext = rootView.findViewById(R.id.lanjut)
        textSkip = rootView.findViewById(R.id.lewati)
        introSliderViewPager = rootView.findViewById(R.id.introSliderViewPager)
        indicatorContainer = rootView.findViewById(R.id.indicatorContainer)
        buttonBack = rootView.findViewById(R.id.kembali)
        buttonSkip = rootView.findViewById(R.id.lewati)

        preferenceManager = PreferenceManager(requireContext()) // Inisialisasi preferenceManager

        introSliderViewPager.adapter = viewPagerAdapter

        introSliderViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)

                if (position == 0) {
                    buttonBack.visibility = View.INVISIBLE
                } else {
                    buttonBack.visibility = View.VISIBLE
                }

                if (position == viewPagerAdapter.itemCount - 1) {
                    buttonNext.text = "Mulai Sekarang!"
                    buttonSkip.visibility = View.INVISIBLE
                } else {
                    buttonNext.text = "Lanjut"
                    buttonSkip.visibility = View.VISIBLE
                }
            }
        })

        buttonNext.setOnClickListener {
            if (introSliderViewPager.currentItem + 1 < viewPagerAdapter.itemCount) {
                introSliderViewPager.currentItem += 1
            } else {
                preferenceManager.setFirstTimeLaunch(false)
                val intent = Intent(requireContext(), RegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }

        buttonBack.setOnClickListener {
            if (introSliderViewPager.currentItem > 0) {
                introSliderViewPager.currentItem -= 1
            }
        }

        textSkip.setOnClickListener {
            preferenceManager.setFirstTimeLaunch(false)
            val intent = Intent(requireContext(), RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!preferenceManager.isFirstTimeLaunch()) {
            val intent = Intent(requireContext(), SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        } else {
            setupIndicator()
            setCurrentIndicator(0)
            buttonBack.visibility = View.INVISIBLE
            buttonSkip.visibility = View.VISIBLE
        }
    }

    private fun setupIndicator() {
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8, 0, 8, 0)

        for (i in 0 until viewPagerAdapter.itemCount) {
            val indicator = ImageView(requireContext())
            indicator.apply {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_inactive
                    )
                )
                this.layoutParams = layoutParams
            }
            indicatorContainer.addView(indicator)
        }
    }

    private fun setCurrentIndicator(index: Int) {
        for (i in 0 until indicatorContainer.childCount) {
            val imageView = indicatorContainer[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext().applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext().applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }
}

