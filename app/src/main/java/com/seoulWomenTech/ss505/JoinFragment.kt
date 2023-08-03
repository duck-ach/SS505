package com.seoulWomenTech.ss505

import android.content.DialogInterface
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
import com.seoulWomenTech.ss505.databinding.FragmentJoinBinding

class JoinFragment : Fragment() {

    lateinit var fragmentJoinBinding: FragmentJoinBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentJoinBinding = FragmentJoinBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        fragmentJoinBinding.run {
            btnJoin.setOnClickListener {
                next()
            }

        }


        return fragmentJoinBinding.root
    }

    // 다음 버튼을 눌렀거나 비밀번호 확인 입력 요소에서 엔터키를 눌렀을 경우
    fun next(){

        fragmentJoinBinding.run{

            // 입력한 내용을 가져온다.
            val joinUserId = textInputEditTextJoinUserId.text.toString()
            val joinUserPw = textInputEditTextJoinUserPw.text.toString()
            val joinUserPw2 = textInputEditTextJoinUserPw2.text.toString()

            if(joinUserId.isEmpty()){
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("로그인 오류")
                builder.setMessage("아이디를 입력해주세요")
                builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(textInputEditTextJoinUserId)
                }
                builder.show()
                return
            }

            if(joinUserPw.isEmpty()){
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("비빌번호 오류")
                builder.setMessage("비밀번호를 입력해주세요")
                builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(textInputEditTextJoinUserPw)
                }
                builder.show()
                return
            }

            if(joinUserPw2.isEmpty()){
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("비빌번호 오류")
                builder.setMessage("비밀번호를 입력해주세요")
                builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(textInputEditTextJoinUserPw2)
                }
                builder.show()
                return
            }

            if(joinUserPw != joinUserPw2){
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("비밀번호 오류")
                builder.setMessage("비밀번호가 일치하지 않습니다.")
                builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                    textInputEditTextJoinUserPw.setText("")
                    textInputEditTextJoinUserPw2.setText("")
                    mainActivity.showSoftInput(textInputEditTextJoinUserPw)
                }
                builder.show()
                return
            }

            val newBundle = Bundle()
            newBundle.putString("joinUserId", joinUserId)
            newBundle.putString("joinUserPw", joinUserPw)

            mainActivity.replaceFragment(MainActivity.ADD_USER_FRAGMENT, true, newBundle)
        }
    }


}