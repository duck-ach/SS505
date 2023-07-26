package com.seoulWomenTech.ss505


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.MaterialSharedAxis
import com.seoulWomenTech.ss505.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    private val tabTextList = listOf("챌린지", "게시판", "나의 챌린지")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        activityMainBinding.run {
            pager.adapter = ViewPagerAdapter(this@MainActivity)

            TabLayoutMediator(tabLayoutMain, pager) { tab, pos ->
                tab.text = tabTextList[pos]

            }.attach()
        }


    }
}
// 정보를 담을 객체
data class AddrClass(var idx: Int, var g_nm: String, var d_nm: String)