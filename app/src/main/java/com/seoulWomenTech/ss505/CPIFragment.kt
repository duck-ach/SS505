package com.seoulWomenTech.ss505

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import com.seoulWomenTech.ss505.databinding.FragmentCPIBinding
import com.seoulWomenTech.ss505.databinding.FragmentMyChallengeBinding
import kotlinx.coroutines.delay

class CPIFragment : Fragment() {
    lateinit var fragmentCPIBinding: FragmentCPIBinding
    lateinit var mainActivity: MainActivity

    // 앨범 액티비티를 실행하기 위한 런처
    lateinit var cpiLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        fragmentCPIBinding = FragmentCPIBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        val cpiChallenge = ChallengeDAO.selectData(mainActivity,mainActivity.rowPosition)
        val userInfo = UserInfoDAO.selectData(mainActivity,mainActivity.userPosition)

        fragmentCPIBinding.run {
            toolbarCPI.run{
                title = "Safety Seoul"

                setNavigationIcon(R.drawable.menu_24)
                setNavigationOnClickListener {
                    // 네비게이션 뷰를 보여준다.

                }

                inflateMenu(R.menu.menu_main)
            }
            progressBarCPI.run {
                visibility = View.GONE

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
                    val fileName = "${userInfo?.idx}/${cpiChallenge.idx}/cpi/${System.currentTimeMillis()}.jpg"
                    val fileRef = storage.reference.child(fileName)
                    fileRef.putFile(it.data?.data!!).addOnCompleteListener{
                        fragmentCPIBinding.progressBarCPI.visibility=View.VISIBLE
                        val cpiClass = CPIClass(0,cpiChallenge.idx,mainActivity.userPosition,fileName)
                        CpiDAO.insertData(mainActivity,cpiClass)
                        Snackbar.make(fragmentCPIBinding.root, "업로드가 완료되었습니다", Snackbar.LENGTH_SHORT).show()
                        mainActivity.removeFragment(MainActivity.CPI_FRAGMENT)
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