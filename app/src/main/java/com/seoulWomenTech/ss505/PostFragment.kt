package com.seoulWomenTech.ss505

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seoulWomenTech.ss505.databinding.FragmentPostBinding


class PostFragment : Fragment() {
lateinit var postBinding: FragmentPostBinding
lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        postBinding = FragmentPostBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        return postBinding.root
    }


}