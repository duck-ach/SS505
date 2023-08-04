package com.seoulWomenTech.ss505

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.seoulWomenTech.ss505.databinding.FragmentCPIBinding
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread


class CPIFragment : Fragment() {
    lateinit var fragmentCPIBinding: FragmentCPIBinding
    lateinit var mainActivity: MainActivity

    // 앨범 액티비티를 실행하기 위한 런처
    lateinit var cpiLauncher: ActivityResultLauncher<Intent>
    var cpiImg = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fragmentCPIBinding = FragmentCPIBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        val cpiChallenge = ChallengeDAO.selectData(mainActivity,mainActivity.rowPosition)
        val userInfo = UserInfoDAO.selectData(mainActivity,mainActivity.userPosition)

        fragmentCPIBinding.run {
            toolbarCPI.run{
                title = "Safety Seoul"


                setNavigationOnClickListener {
                    drawerLayoutCPI.open()

                }

            }

            navigationViewCPI.run {
                setNavigationItemSelectedListener {
                    when(it.itemId){
                        R.id.nav_challenge -> {
                            drawerLayoutCPI.close()
                            mainActivity.replaceFragment(MainActivity.MAIN_FRAGMENT,true,null)
                        }

                        R.id.nav_safedata -> {
                            mainActivity.replaceFragment(MainActivity.SAFETYDATA_FRAGMENT,true,null)
                            drawerLayoutCPI.close()
                        }

                        R.id.mypage -> {
                            mainActivity.replaceFragment(MainActivity.MYPAGE_FRAGMENT,true,null)
                            drawerLayoutCPI.close()
                        }
                        R.id.nav_logout -> {
                            mainActivity.supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                            mainActivity.replaceFragment(MainActivity.LOGIN_FRAGMENT,false,null)

                        }

                        else -> {

                        }

                    }
                    false
                }
            }



            CPITitle.text = cpiChallenge.name
            CPIDate.append("${cpiChallenge.progDate} ${cpiChallenge.progTime}")
            CPIMaxUser.append("${cpiChallenge.maxUser}명")
            CPILocation.append(cpiChallenge.location)
            CPIContent.append(cpiChallenge.content)


            val contract1 = ActivityResultContracts.StartActivityForResult()
            cpiLauncher = registerForActivityResult(contract1){
                if(it?.resultCode == AppCompatActivity.RESULT_OK){
                    val storage = FirebaseStorage.getInstance()

                    //해당 유저 번호 폴더 안 챌린지 번호 폴더 안 cpi 폴더에 저장하게 경로 설정
                    val fileName = "cpi/${cpiChallenge.idx}/${mainActivity.userPosition}_${System.currentTimeMillis()}.jpg"
                    val fileRef = storage.reference.child(fileName)
                    fileRef.putFile(it.data?.data!!).addOnSuccessListener{
                        val cpiClass = CPIClass(0,cpiChallenge.idx,mainActivity.userPosition,fileName)
                        CpiDAO.insertData(mainActivity,cpiClass)

                        fragmentCPIBinding.run {
                            CPITempImage.visibility = View.GONE

                            // 데이터를 가져올 수 있는 경로를 가져온다.
                            fileRef.downloadUrl.addOnCompleteListener {
                                thread {
                                    // 파일에 접근할 수 있는 경로를 이용해 URL 객체를 생성한다.
                                    val url = URL(it.result.toString())
                                    // 접속한다.
                                    val httpURLConnection = url.openConnection() as HttpURLConnection
                                    // 이미지 객체를 생성한다.
                                    val bitmap = BitmapFactory.decodeStream(httpURLConnection.inputStream)

                                    mainActivity.runOnUiThread{

                                        CPIImage.setImageBitmap(bitmap)
                                    }
                                }
                            }

                            CPIImage.visibility = View.VISIBLE

                            btnCPI.run {
                                text = "제출 완료"
                                setBackgroundResource(R.drawable.button_round_disabled)
                                isClickable = false
                            }

                            Snackbar.make(fragmentCPIBinding.root, "업로드가 완료되었습니다", Snackbar.LENGTH_SHORT).show()


                        }

                    }

                }
            }

            btnCPI.setOnClickListener {
                val newIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                newIntent.setType("image/*")
                val mimeType = arrayOf("image/*")
                newIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
                cpiLauncher.launch(newIntent)

            }
        }

        return fragmentCPIBinding.root


    }

}