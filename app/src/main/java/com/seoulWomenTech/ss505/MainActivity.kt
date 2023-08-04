package com.seoulWomenTech.ss505


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.transition.MaterialSharedAxis
import com.seoulWomenTech.ss505.databinding.ActivityMainBinding
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    // 사용자가 누른 항목 번호
    var rowPosition = 0

    var newFragment:Fragment? = null
    var oldFragment:Fragment? = null



    companion object {
        // Activity가 관리할 프래그먼트들의 이름
        val MAIN_FRAGMENT = "MainFragment" //  메인 화면
        val PARTICIPATE_FRAGMENT = "ParticipateFragment" //  참여 화면
        val POSTDETAIL_FRAGMENT = "PostDetailFragment"
        val WRITE_FRAGMENT = "WriteFragment"
        val POST_FRAGMENT = "PostFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // challenge insert 문
//        val challenge1 = ChallengeClass(0,1,1,"챌린지1","챌린지내용입니다","2023-07-25","2023-08-04","12:00",5,1,100,"이미지주소")
//        ChallengeDAO.insertData(this,challenge1)
//        val challenge2 = ChallengeClass(0,1,1,"챌린지2","챌린지내용입니다2","2023-07-26","2023-08-05","14:00",5,1,100,"이미지주소2")
//        ChallengeDAO.insertData(this,challenge2)
//        val challenge3 = ChallengeClass(0,1,1,"챌린지3","챌린지내용입니다3","2023-07-27","2023-08-06","15:00",5,1,100,"이미지주소3")
//        ChallengeDAO.insertData(this,challenge3)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        addSampleData()
        replaceFragment(MAIN_FRAGMENT, false, null)
    }
    private fun addSampleData() {
        PostDAO.deleteData(this, 1);
        PostDAO.deleteData(this, 2);
        PostDAO.deleteData(this, 3);
        PostDAO.deleteData(this, 4);
        PostDAO.deleteData(this, 5);

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
            "이미지주소1"
        )
        PostImageDao.insertData(this, postImage1)

        val postImage2 = PostImageClass(
            2,
            2,
            "유저2",
            "이미지2",
            "이미지주소2"
        )
        PostImageDao.insertData(this, postImage2)

        val postImage3 = PostImageClass(
            3,
            3,
            "유저3",
            "이미지3",
            "이미지주소3"
        )
        PostImageDao.insertData(this, postImage3)
        val user1 = UserClass(
            1,
            "사용자1",
            "user1@example.com",
            "1234",
            1,
            0,
            100,
            "010-1111-1111",
            "sns1",
            "주소1",
            "2023-07-25",
            "사용자1 이미지 URL"
        )
        UserDao.insertData(this, user1)

        val user2 = UserClass(
            2,
            "사용자2",
            "user2@example.com",
            "1234",
            1,
            0,
            200,
            "010-2222-2222",
            "sns2",
            "주소2",
            "2023-07-26",
            "사용자2 이미지 URL"
        )
        UserDao.insertData(this, user2)

        val user3 = UserClass(
            3,
            "사용자3",
            "user3@example.com",
            "1234",
            1,
            0,
            300,
            "010-3333-3333",
            "sns3",
            "주소3",
            "2023-07-27",
            "사용자3 이미지 URL"
        )
        UserDao.insertData(this, user3)
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

    // 지정한 Fragment를 보여주는 메서드
    fun replaceFragment(name:String, addToBackStack:Boolean, bundle: Bundle?){
        // Fragment 교체 상태로 설정한다.
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        // newFragment 에 Fragment가 들어있으면 oldFragment에 넣어준다.
        if(newFragment != null){
            oldFragment = newFragment
        }

        // 새로운 Fragment를 담을 변수
        newFragment = when(name){
            MAIN_FRAGMENT -> MainFragment()
            PARTICIPATE_FRAGMENT -> ParticipateFragment()
            POSTDETAIL_FRAGMENT -> PostDetailFragment()
            WRITE_FRAGMENT -> WriteFragment()
            POST_FRAGMENT -> PostFragment()
            else -> Fragment()
        }
        newFragment?.arguments = bundle

        if(newFragment != null) {

            // oldFragment -> newFragment로 이동
            // oldFramgent : exit
            // newFragment : enter


            // oldFragment <- newFragment 로 되돌아가기
            // oldFragment : reenter
            // newFragment : return

            // 애니메이션 설정
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

            // Fragment를 교채한다.
            fragmentTransaction.replace(R.id.mainContainer, newFragment!!)

            if (addToBackStack == true) {
                // Fragment를 Backstack에 넣어 이전으로 돌아가는 기능이 동작할 수 있도록 한다.
                fragmentTransaction.addToBackStack(name)
            }

            // 교체 명령이 동작하도록 한다.
            fragmentTransaction.commit()
        }
    }

    // Fragment를 BackStack에서 제거한다.
    fun removeFragment(name:String){
        supportFragmentManager.popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}
// 정보를 담을 객체
data class AddrClass(var idx: Int, var g_nm: String, var d_nm: String)
data class ChallengeClass(var idx: Int, var admin_id: Int,var addr_id: Int,var name: String, var content: String,var postDate: String,var progDate: String, var progTime: String, var maxUser: Int, var active: Int, var reword: Int,var img: String)

data class PostClass(var post_id: Int, var user_id: Int, var post_title: String, var post_content: String,var post_date: String)
data class PostImageClass(var pi_id: Int, var post_id: Int, var pi_un: String, var pi_cn: String, var pi_url: String)
data class UserClass(var user_id: Int, var name: String, var email: String, var pwd: String, var role: Int, var gender: Int, var point: Int, var phone: String, var sns: String, var addr: String, var date: String, var user_image: String)

data class CommentClass(var c_id: Int, var post_id: Int, var writer_id: Int, var content: String, var date: String)