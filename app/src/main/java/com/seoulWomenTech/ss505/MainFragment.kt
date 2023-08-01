package com.seoulWomenTech.ss505


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.seoulWomenTech.ss505.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    lateinit var fragmentMainBinding: FragmentMainBinding
    lateinit var mainActivity: MainActivity

    private val tabTextList = listOf("챌린지", "게시판", "나의 챌린지")

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMainBinding = FragmentMainBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        tabLayout = fragmentMainBinding.tabLayoutMain
        viewPager = fragmentMainBinding.pager

        // adapter 준비 및 연결
        adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter


        fragmentMainBinding.run {
            toolbarMain.run{
                title = "Safety Seoul"

                
                setNavigationOnClickListener {
                    // 네비게이션 뷰를 보여준다.

                }

            }
        }

        // TabLayout, ViewPager 연결
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.setText(tabTextList.get(0))
                1 -> tab.setText(tabTextList.get(1))
                else -> tab.setText(tabTextList.get(2))
            }
        }.attach()


        return fragmentMainBinding.root



    }


}