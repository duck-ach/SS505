package com.seoulWomenTech.ss505

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.seoulWomenTech.ss505.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {
    lateinit var fragmentLoginBinding: FragmentLoginBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        fragmentLoginBinding.run{
            // toolbar
            toolbarLogin.run{
                title = "로그인"
            }

            // 회원가입 버튼
            btnLoginJoin.run{
                setOnClickListener {
                    // JoinFragment를 보이게 한다.
                    mainActivity.replaceFragment(MainActivity.JOIN_FRAGMENT, true, null)
                }
            }

            // 로그인 버튼
            btnLoginSubmit.run{
                setOnClickListener {
                    loginSubmit()

                }
            }

            // 비빌번호 입력요소
            textInputEditTextLoginUserPw.run{
                setOnEditorActionListener { textView, i, keyEvent ->
                    loginSubmit()
                    true
                }
            }
        }

        return fragmentLoginBinding.root
    }

    fun loginSubmit(){
        fragmentLoginBinding.run{
            val loginUserId = textInputEditTextLoginUserId.text.toString()
            val loginUserPw = textInputEditTextLoginUserPw.text.toString()

            if(loginUserId.isEmpty()){
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("로그인 오류")
                builder.setMessage("아이디를 입력해주세요")
                builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(textInputEditTextLoginUserId)
                }
                builder.show()
                return
            }

            if(loginUserPw.isEmpty()){
                val builder = MaterialAlertDialogBuilder(mainActivity)
                builder.setTitle("비밀번호 오류")
                builder.setMessage("비밀번호를 입력해주세요")
                builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                    mainActivity.showSoftInput(textInputEditTextLoginUserPw)
                }
                builder.show()
                return
            }


            // userId가 사용자가 입력한 아이디와 같은 데이터를 가져온다.
            val userData = UserInfoDAO.selectDataByEmail(mainActivity,loginUserId)

                // 가져온 데이터가 없다면
                if(userData.isEmpty()){
                    val builder = MaterialAlertDialogBuilder(mainActivity)
                    builder.setTitle("로그인 오류")
                    builder.setMessage("존재하지 않는 아이디 입니다")
                    builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                        textInputEditTextLoginUserId.setText("")
                        textInputEditTextLoginUserPw.setText("")
                        mainActivity.showSoftInput(textInputEditTextLoginUserId)
                    }
                    builder.show()
                }
                // 가져온 데이터가 있다면
                else {

                        val userPw = userData[0].password

                        // 입력한 비밀번호와 현재 계정의 비밀번호가 다르다면
                        if(loginUserPw != userPw){
                            val builder = MaterialAlertDialogBuilder(mainActivity)
                            builder.setTitle("로그인 오류")
                            builder.setMessage("잘못된 비밀번호 입니다")
                            builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                                textInputEditTextLoginUserPw.setText("")
                                mainActivity.showSoftInput(textInputEditTextLoginUserPw)
                            }
                            builder.show()
                        }
                        // 입력한 비밀번호와 현재 계정의 비밀번호가 같다면
                        else {
                            mainActivity.userPosition = userData[0].idx
                            val newBundle  = Bundle()
                            newBundle.putString("userName",userData[0].name)
                            newBundle.putString("userImage",userData[0].image)
                            newBundle.putString("userEmail",userData[0].email)
                            newBundle.putString("userPoint",userData[0].point.toString())

                            Snackbar.make(fragmentLoginBinding.root, "로그인 되었습니다", Snackbar.LENGTH_SHORT).show()

                            mainActivity.replaceFragment(MainActivity.MAIN_FRAGMENT, false, newBundle)
                        }


            }
        }
    }


}