package com.seoulWomenTech.ss505

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seoulWomenTech.ss505.databinding.ActivityMainBinding
import com.seoulWomenTech.ss505.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    lateinit var mainBinding: FragmentMainBinding
    lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mainBinding = FragmentMainBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        return mainBinding.root
    }


}