package com.seoulWomenTech.ss505


import OnboardingFragment
import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.animation.AnticipateInterpolator
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.transition.MaterialSharedAxis
import com.seoulWomenTech.ss505.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    val permissionList = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_MEDIA_LOCATION,
        Manifest.permission.INTERNET
    )

    var userPosition = 2
    // 사용자가 누른 항목 번호
    var rowPosition = 0

    var newFragment:Fragment? = null
    var oldFragment:Fragment? = null



    companion object {
        val ONBOARDING_FRAGMENT = "OnboardingFragment" // 온보딩 화면
        val LOGIN_FRAGMENT = "LoginFragment" // 로그인 화면
        val JOIN_FRAGMENT = "JoinFragment" // 회원가입 화면
        val ADD_USER_FRAGMENT = "AddUserFragment" // 유저 정보 입력 화면
        val MAIN_FRAGMENT = "MainFragment" //  메인 화면
        val PARTICIPATE_FRAGMENT = "ParticipateFragment" //  참여 화면
        val CPI_FRAGMENT = "CPIFragment" //  인증샷 제출 화면

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
         splashScreenCustomizing(splashScreen)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        requestPermissions(permissionList,0)
//        replaceFragment(ONBOARDING_FRAGMENT, false, null)
        replaceFragment(LOGIN_FRAGMENT, false, null)


    }

    fun splashScreenCustomizing(splashScreen: SplashScreen){
        splashScreen.setOnExitAnimationListener{
            val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 2f, 1f, 0f)
            val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 2f, 1f, 0f)
            val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 1f, 1f, 0.5f, 0f)
            val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(it.iconView, scaleX, scaleY, alpha)
            objectAnimator.interpolator = AnticipateInterpolator()
            objectAnimator.duration = 1000
            objectAnimator.addListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)

                    it.remove()
                }
            })

            objectAnimator.start()
        }
    }

    fun replaceFragment(name:String, addToBackStack:Boolean, bundle: Bundle?){
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if(newFragment != null){
            oldFragment = newFragment
        }

        newFragment = when(name){
            ONBOARDING_FRAGMENT -> OnboardingFragment()
            JOIN_FRAGMENT -> JoinFragment()
            ADD_USER_FRAGMENT -> AddUserFragment()
            LOGIN_FRAGMENT -> LoginFragment()
            MAIN_FRAGMENT -> MainFragment()
            PARTICIPATE_FRAGMENT -> ParticipateFragment()
            CPI_FRAGMENT -> CPIFragment()
            else -> Fragment()
        }

        newFragment?.arguments = bundle

        if(newFragment != null) {

            if(oldFragment != null){
                oldFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                oldFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
                oldFragment?.enterTransition = null
                oldFragment?.returnTransition = null
            }

            newFragment?.exitTransition = null
            newFragment?.reenterTransition = null
            newFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
            newFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

            fragmentTransaction.replace(R.id.mainContainer, newFragment!!)

            if (addToBackStack == true) {
                fragmentTransaction.addToBackStack(name)
            }

            fragmentTransaction.commit()
        }
    }

    fun removeFragment(name:String){
        supportFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    // 입력 요소에 포커스를 주는 메서드
    fun showSoftInput(view:View){
        view.requestFocus()

        val inputMethodManger = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        thread {
            SystemClock.sleep(200)
            inputMethodManger.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
data class AddrClass(var idx: Int, var d_nm: String, var g_nm: String)
data class ChallengeClass(var idx: Int, var admin_id: Int,var addr_id: Int,var name: String, var content: String,var postDate: String,var progDate: String, var progTime: String, var maxUser: Int, var active: Int, var reword: Int,var img: String,var location : String)
data class ParticipantsClass(var clg_id:Int, var user_id:Int)
data class UserInfo(
    val idx: Int,
    val name: String,
    val email: String,
    val password: String,
    val role: Int,
    val gender: Int?,
    val point: Int?,
    val phone: String?,
    val sns: String?,
    val address: String?,
    val date: String?,
    val image: String?,
    val admin_office : String?,
    val admin_rank : String?
)

data class CPIClass(
    val idx: Int,
    val clg_id: Int,
    val user_id: Int,
    val url: String?,
)

data class SafetyData(
    val idx: Int,
    val adminId: Int,
    val title: String?,
    val content: String?,
    val date: String?
)

data class SafetyImage(
    val idx: Int,
    val sdId: Int?,
    val url: String?
)