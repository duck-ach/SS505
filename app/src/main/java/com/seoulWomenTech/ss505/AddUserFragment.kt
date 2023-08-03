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


class AddUserFragment : Fragment() {
    lateinit var fragmentAddUserBinding: FragmentAddUserBinding
    lateinit var mainActivity: MainActivity
    var guList = mutableListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentAddUserBinding = FragmentAddUserBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        fragmentAddUserBinding.run {
            toolbarAddUser.run {
                title = "회원가입"
                setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material)
                setNavigationOnClickListener {
                    mainActivity.removeFragment(MainActivity.ADD_USER_FRAGMENT)
                }
            }

            AddrDAO.selectAllData(mainActivity).forEach {
                guList.add(it.g_nm)
            }
            guList = guList.distinct() as MutableList<String>

            val userAddrList = mutableListOf<AddrClass>()
            val guAdapter = ArrayAdapter(requireContext(), R.layout.list_item, guList)
            (joinAddrGu.editText as? AutoCompleteTextView)?.setAdapter(guAdapter)


            // AutoCompleteTextView의 값이 변경될 때마다 호출되는 리스너를 추가합니다.
            joinAddrGu.editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    var guName = s.toString()
                    val dongList = AddrDAO.selectByGNM(mainActivity, guName)
                        .map { addr -> addr.d_nm } as MutableList<String>
                    val dongAdapter = ArrayAdapter(requireContext(), R.layout.list_item, dongList)
                    (joinAddrDong.editText as? AutoCompleteTextView)?.setAdapter(dongAdapter)
                }
            })

            joinAddrDong.editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    var dongName = s.toString()
                    val addr = AddrDAO.selectByDNM(mainActivity, dongName)

                    if (userAddrList.filter { ua -> ua.idx == addr[0].idx }.isEmpty()) {
                        userAddrList.add(addr[0])
                        chipGroupAddUser.addView(Chip(mainActivity, null, com.google.android.material.R.style.Widget_Material3_Chip_Input).apply {
                            text = "${addr[0].g_nm} ${addr[0].d_nm}"
                            isCloseIconVisible = true
                            setOnCloseIconClickListener {
                                chipGroupAddUser.removeView(this)
                                userAddrList.filter { ua -> ua.idx == addr[0].idx }
                                    .forEach { userAddrList.remove(it) }
                                Log.d("유저 주소", userAddrList.toString())
                            }
                        })
                    }

                }
            })

            val userAddr = userAddrList.map{a->a.idx}.joinToString(",")


        }
        return fragmentAddUserBinding.root
    }

}