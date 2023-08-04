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

    var userPosition = 0
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
        val POSTDETAIL_FRAGMENT = "PostDetailFragment"
        val WRITE_FRAGMENT = "WriteFragment"
        val POST_FRAGMENT = "PostFragment"
        val CPI_FRAGMENT = "CPIFragment" //  인증샷 제출 화면
        val MYPAGE_FRAGMENT = "MyPageFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
         splashScreenCustomizing(splashScreen)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        
        requestPermissions(permissionList,0)

        addSampleData()
//         replaceFragment(MAIN_FRAGMENT, false, null)
        
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
            POSTDETAIL_FRAGMENT -> PostDetailFragment()
            WRITE_FRAGMENT -> WriteFragment()
            POST_FRAGMENT -> PostFragment()
            CPI_FRAGMENT -> CPIFragment()
            MYPAGE_FRAGMENT -> MyPageFragment()

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
    
    // DB Insert
    private fun addSampleData() {

        val post1 = PostClass(
            1,
            1,
            "[우리동네 자랑]\n동네 담배 꽁초 근황",
            "담배 꽁초로 인해 침수가 계속 발생한다고 그래서 많이 걱정했었는데 다행히 저희 동네는 깨끗하네요! 어쩐지 침수된 구역이 없다 싶었어요. 이런 상황에서 저희 동네 주민들이 모여서 함께 깨끗한 환경을 지키려는 모습이 보기 좋습니다.",
            "2023-07-25 21:08:23"
        )
        val postId1 = PostDAO.insertData(this, post1)

        val post2 = PostClass(
            2,
            2,
            "[방범후기]\n첫 챌린지 참여 후기",
            "오늘은 홍길동 경관님과 함께 순찰 방범 챌린지를 진행했어요. 주민들 모두가 함께 모여 동네의 안전을 지키기 위한 노력을 기울였습니다. 함께 순찰하며 느낀 점은 이웃 간의 소통과 협력이 정말 중요하다는 것이었어요. 앞으로도 챌린지에 자주 참여해야겠어요~^^.",
            "2023-07-26 15:02:20"
        )
        val postId2 = PostDAO.insertData(this, post2)

        val post3 = PostClass(
            3,
            3,
            "[우리동네 자랑]\n동네 공원의 봄",
            "오늘 공원에서 사진 한 장 찍어봤어요. 봄이 참 아름답죠! 여기 저기 피어난 꽃들이 정말 환상적입니다. 우리 동네 공원은 봄이 찾아오면 더욱 아름답게 변해요.",
            "2023-07-27 15:02:20"
        )
        val postId3 = PostDAO.insertData(this, post3)

        val post4 = PostClass(
            4,
            2,
            "[방범후기]\n동네 순찰 방범 챌린지 후기",
            "어제 동네 순찰 방범 챌린지에 참여했어요. 밤에 동네를 둘러보며 경찰과 주민들이 함께하여 동네의 안전을 지켜보는 모습이 인상적이었습니다. 늦은 시간에도 동네 안전을 위해 노력하는 분들을 보니 뿌듯함과 동시에 감동을 받았어요. 앞으로도 이런 행사에 더 많이 참여하고 싶습니다.",
            "2023-07-27 15:02:20"
        )
        val postId4 = PostDAO.insertData(this, post4)

        // 샘플 포스트 이미지 데이터 추가
        val postImage1 = PostImageClass(
            1,
            1,
            "유저1",
            "이미지1",
            "placeholder_2"
        )
        PostImageDao.insertData(this, postImage1)

        val postImage2 = PostImageClass(
            2,
            2,
            "유저2",
            "이미지2",
            "sample_img"
        )
        PostImageDao.insertData(this, postImage2)

        val postImage3 = PostImageClass(
            3,
            3,
            "유저3",
            "이미지3",
            "c202307271113"
        )
        PostImageDao.insertData(this, postImage3)

        val comment1 = CommentClass(
            1,
            1,
            1,
            "1번 댓글 내용입니다",
            "2023-07-28"
        )
        CommentDAO.insertData(this, comment1)
        val comment2 = CommentClass(
            2,
            1,
            2,
            "2번 댓글 내용입니다",
            "2023-07-28"
        )
        CommentDAO.insertData(this, comment2)
        val comment3 = CommentClass(
            3,
            1,
            3,
            "3번 댓글 내용입니다",
            "2023-07-28"
        )
        CommentDAO.insertData(this, comment3)
    }
}

// 정보를 담을 객체
data class PostClass(var post_id: Int, var user_id: Int, var post_title: String, var post_content: String,var post_date: String)
data class PostImageClass(var pi_id: Int, var post_id: Int, var pi_un: String, var pi_cn: String, var pi_url: String)
data class CommentClass(var c_id: Int, var post_id: Int, var writer_id: Int, var content: String, var date: String)
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

