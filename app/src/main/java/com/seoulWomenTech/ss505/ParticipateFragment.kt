package com.seoulWomenTech.ss505

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seoulWomenTech.ss505.databinding.FragmentParticipateBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ParticipateFragment : Fragment() {

    lateinit var fragmentParticipateBinding: FragmentParticipateBinding
    lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentParticipateBinding = FragmentParticipateBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        fragmentParticipateBinding.run {
            toolbarParticipate.run{
                title = "Safety Seoul"

                setNavigationIcon(R.drawable.menu_24)
                setNavigationOnClickListener {
                    // 네비게이션 뷰를 보여준다.


                }

                inflateMenu(R.menu.menu_main)
            }

            val challenge = ChallengeDAO.selectData(mainActivity,mainActivity.rowPosition)

            participateTitle.text = challenge.name
            participateDate.text=challenge.progDate
            participateLocation.text = AddrDAO.selectData(mainActivity,challenge.addr_id).d_nm+" " + AddrDAO.selectData(mainActivity,challenge.addr_id).g_nm
            participateContent.text= challenge.content

        }

        return fragmentParticipateBinding.root
    }


}