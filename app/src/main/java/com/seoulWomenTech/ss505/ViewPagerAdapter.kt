package com.seoulWomenTech.ss505

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(fragment: MainFragment) : FragmentStateAdapter(fragment){
    var fragments = arrayOf(ChallengeFragment(), PostFragment(), MyChallengeFragment())

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }
}