package com.seoulWomenTech.ss505

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.seoulWomenTech.ss505.Adapter.SafetyResourceAdapter
import com.seoulWomenTech.ss505.databinding.FragmentSafetyDataBinding
import com.seoulWomenTech.ss505.databinding.NavHeaderSidebarBinding
import com.seoulWomenTech.ss505.model.SafetyResourceModelClass

class SafetyDataFragment : Fragment() {
    lateinit var fragmentSafetyDataBinding: FragmentSafetyDataBinding
    lateinit var mainActivity: MainActivity
    lateinit var navHeaderSidebarBinding: NavHeaderSidebarBinding


    private var safetydatalist = ArrayList<SafetyResourceModelClass>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addSaftyData()


        fragmentSafetyDataBinding = FragmentSafetyDataBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        val userInfo = UserInfoDAO.selectData(mainActivity,mainActivity.userPosition)

        fragmentSafetyDataBinding.run {

            toolbarSafetyData.run{
                title = "Safety Seoul"


                setNavigationOnClickListener {
                    drawerLayoutSafetyData.open()

                }

            }

            navigationViewSafetyData.run {
                val headerView = getHeaderView(0)
                navHeaderSidebarBinding = NavHeaderSidebarBinding.bind(headerView)
                navHeaderSidebarBinding.run {
                    val imgSrc = mainActivity.resources.getIdentifier(
                        userInfo.image,
                        "drawable",
                        mainActivity.packageName
                    )
                    if (imgSrc != null) {
                        navHeaderUserImg.setImageResource(imgSrc)
                    }

                    navHeaderUserName.text = userInfo.name
                    navHeaderUserEmail.text = userInfo.email
                    navHeaderUserPoint.text = "POINT : ${userInfo.point}"

                }


                setNavigationItemSelectedListener {
                    when(it.itemId){
                        R.id.nav_main -> {
                            drawerLayoutSafetyData.close()
                            mainActivity.supportFragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                        }

                        R.id.nav_safedata -> {
                            mainActivity.replaceFragment(MainActivity.SAFETYDATA_FRAGMENT,true,null)
                            drawerLayoutSafetyData.close()
                        }

                        R.id.mypage -> {
                            mainActivity.replaceFragment(MainActivity.MYPAGE_FRAGMENT,true,null)
                            drawerLayoutSafetyData.close()
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

            SafetyRecyclerView.run {
                layoutManager = LinearLayoutManager(requireContext())
                var adaptor = SafetyResourceAdapter(safetydatalist)
                adapter = adaptor
                setHasFixedSize(true)
            }
        }


        return fragmentSafetyDataBinding.root
    }

    fun addSaftyData(){
        // 임의의 데이터들
        safetydatalist.add(SafetyResourceModelClass(R.drawable.cpr, "심폐소생술 안전자료"))
        safetydatalist.add(SafetyResourceModelClass(R.drawable.cpr, "심폐소생술 안전자료"))
        safetydatalist.add(SafetyResourceModelClass(R.drawable.video, "비디오 1 입니다."))
        safetydatalist.add(SafetyResourceModelClass(R.drawable.video, "비디오 2 입니다."))
    }
}
