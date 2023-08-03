package com.seoulWomenTech.ss505

import android.content.DialogInterface
import android.icu.text.SimpleDateFormat
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.seoulWomenTech.ss505.databinding.FragmentAddUserBinding
import java.util.Date
import java.util.Locale


class AddUserFragment : Fragment() {
    lateinit var fragmentAddUserBinding: FragmentAddUserBinding
    lateinit var mainActivity: MainActivity
    var guList = mutableListOf<String>()
    var userPoint = 0
    var userSNS = "default"
    var userImg = "user_sample"
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
            (addUserAddrGu.editText as? AutoCompleteTextView)?.setAdapter(guAdapter)


            // AutoCompleteTextView의 값이 변경될 때마다 호출되는 리스너를 추가합니다.
            addUserAddrGu.editText?.addTextChangedListener(object : TextWatcher {
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
                    (addUserAddrDong.editText as? AutoCompleteTextView)?.setAdapter(dongAdapter)
                }
            })

            addUserAddrDong.editText?.addTextChangedListener(object : TextWatcher {
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





            var userGender = 0
            radioGroupAddUserGender.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radioBtnUserAddGenderF -> {
                        if (radioBtnUserAddGenderF.isChecked) {
                           userGender = 0
                        }
                    }
                    R.id.radioBtnUserAddGenderM -> {
                        if (radioBtnUserAddGenderM.isChecked) {
                            userGender = 1
                        }
                    }
                }
            }

            btnAddUserSubmit.setOnClickListener {
                val userName = textInputEditTextAddUserName.text.toString()
                val userAddr = userAddrList.map{a->a.idx}.joinToString(",")
                val userPhone = textInputEditTextAddUserPhone.text.toString()
                val userDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

                if(userName.isEmpty()){
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setTitle("이름 입력 오류")
                    builder.setMessage("이름을 입력해주세요")
                    builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                        mainActivity.showSoftInput(textInputEditTextAddUserName)
                    }
                    builder.show()
                    return@setOnClickListener
                }

                if(userPhone.isEmpty()){
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setTitle("전화번호 입력 오류")
                    builder.setMessage("전화번호를 입력해주세요")
                    builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                        mainActivity.showSoftInput(textInputEditTextAddUserPhone)
                    }
                    builder.show()
                    return@setOnClickListener
                }

                if(userAddrList.map{a->a.idx}.isEmpty()){
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setTitle("주소 입력 오류")
                    builder.setMessage("주소를 입력해주세요")
                    builder.setPositiveButton("확인",null)
                    builder.show()
                    return@setOnClickListener
                }

                // 저장할 데이터들을 담는다.
                val userEmail = arguments?.getString("joinUserId")!!
                val userPw = arguments?.getString("joinUserPw")!!
                val userInfo = UserInfo(0,userName,userEmail,userPw,0,userGender,userPoint,userPhone,userSNS,userAddr,userDate,userImg,null,null)

                UserInfoDAO.insertData(mainActivity,userInfo)

                Snackbar.make(fragmentAddUserBinding.root, "가입이 완료되었습니다", Snackbar.LENGTH_SHORT).show()

                mainActivity.removeFragment(MainActivity.ADD_USER_FRAGMENT)
                mainActivity.removeFragment(MainActivity.JOIN_FRAGMENT)
            }

            


        }
        return fragmentAddUserBinding.root
    }

}