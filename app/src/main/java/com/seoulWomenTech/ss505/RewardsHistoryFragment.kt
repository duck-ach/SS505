package com.seoulWomenTech.ss505

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.seoulWomenTech.ss505.Adapter.HistoryAdapter
import com.seoulWomenTech.ss505.databinding.FragmentRewardsBinding
import com.seoulWomenTech.ss505.databinding.FragmentRewardsHistoryBinding
import com.seoulWomenTech.ss505.databinding.NavHeaderSidebarBinding
import com.seoulWomenTech.ss505.model.HistoryModelClass

class RewardsHistoryFragment : Fragment() {
    lateinit var fragmentRewardsHistoryBinding: FragmentRewardsHistoryBinding
    lateinit var mainActivity: MainActivity
    lateinit var navHeaderSidebarBinding: NavHeaderSidebarBinding
    val ListHistory = ArrayList<HistoryModelClass>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentRewardsHistoryBinding = FragmentRewardsHistoryBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        val userInfo = UserInfoDAO.selectData(mainActivity, mainActivity.userPosition)

        addPointData()

        fragmentRewardsHistoryBinding.run {
            toolbarRewardsHistory.run {
                title = "Safety Seoul"

                setNavigationOnClickListener {
                    drawerLayoutRewardsHistory.open()

                }

            }

            navigationViewRewardsHistory.run {
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
                    when (it.itemId) {
                        R.id.nav_main -> {
                            drawerLayoutRewardsHistory.close()
                            mainActivity.supportFragmentManager.popBackStackImmediate(
                                null,
                                FragmentManager.POP_BACK_STACK_INCLUSIVE
                            )
                        }

                        R.id.nav_safedata -> {
                            mainActivity.replaceFragment(
                                MainActivity.SAFETYDATA_FRAGMENT,
                                true,
                                null
                            )
                            drawerLayoutRewardsHistory.close()
                        }

                        R.id.mypage -> {
                            mainActivity.replaceFragment(MainActivity.MYPAGE_FRAGMENT, true, null)
                            drawerLayoutRewardsHistory.close()
                        }

                        R.id.nav_logout -> {
                            mainActivity.supportFragmentManager.popBackStackImmediate(
                                null,
                                FragmentManager.POP_BACK_STACK_INCLUSIVE
                            )
                            mainActivity.replaceFragment(MainActivity.LOGIN_FRAGMENT, false, null)

                        }

                        else -> {

                        }

                    }
                    false
                }
            }

            HistoryRecyclerView.run {
                layoutManager = LinearLayoutManager(requireContext())
                var adaptor = HistoryAdapter(ListHistory)
                adapter = adaptor
                setHasFixedSize(true)
            }


            val imgSrc = mainActivity.resources.getIdentifier(
                userInfo.image,
                "drawable",
                mainActivity.packageName
            )
            if (imgSrc != null) {
                rHistoryUserImg.setImageResource(imgSrc)
            }
            rHistoryUserName.text = userInfo.name
            rHistoryPointSum.text = userInfo.point.toString()

            return fragmentRewardsHistoryBinding.root
        }



    }

    fun addPointData(){
        // 임의의 데이터들
        ListHistory.add(HistoryModelClass("우리 동네 쓰레기 줍기","10:00, 07.24.2023", "30"))
        ListHistory.add(HistoryModelClass("안심 귀갓길 만들기","22:30, 07.25.2023", "20"))
        ListHistory.add(HistoryModelClass("어린이 등하교 친구 되어주기","15:00, 07.30.2023", "10"))
        ListHistory.add(HistoryModelClass("우리 동네 전봇대 전깃 줄 점검","13:30, 08.01.2023", "20"))
        ListHistory.add(HistoryModelClass("담배 꽁초 20개 줍기","11:30, 08.02.2023", "20"))
    }
}


