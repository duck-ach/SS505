package com.seoulWomenTech.ss505

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.google.android.material.chip.Chip
import com.seoulWomenTech.ss505.databinding.FragmentAddUserBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AddUserFragment : Fragment() {
    lateinit var fragmentAddUserBinding: FragmentAddUserBinding
    lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentAddUserBinding = FragmentAddUserBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        fragmentAddUserBinding.run {


        }
        return fragmentAddUserBinding.root
    }

}