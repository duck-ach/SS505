package com.seoulWomenTech.ss505

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seoulWomenTech.ss505.databinding.FragmentParticipateBinding
import com.seoulWomenTech.ss505.databinding.RowChallengeBinding
import com.seoulWomenTech.ss505.databinding.RowParticipateBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ParticipateFragment : Fragment() {

    lateinit var fragmentParticipateBinding: FragmentParticipateBinding
    lateinit var mainActivity: MainActivity

    val participantsList = mutableListOf<UserInfo>()
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

            val participantsTemp = ParticipantsDAO.selectByClgId(mainActivity,challenge.idx)

            for (p in participantsTemp){
                val user = UserInfoDAO.selectData(mainActivity,p.user_id)
                if (user != null) {
                    participantsList.add(user)
                }
            }


            participateMaxUser.append("( ${participantsList.size} / ${challenge.maxUser} )")

            recyclerViewParticipate.run {
                adapter = ParticipateRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
                addItemDecoration(DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL))
            }

        }

        return fragmentParticipateBinding.root
    }

    inner class ParticipateRecyclerViewAdapter : RecyclerView.Adapter<ParticipateRecyclerViewAdapter.ParticipateViewHolderClass>(){

        inner class ParticipateViewHolderClass(rowParticipateBinding: RowParticipateBinding) : RecyclerView.ViewHolder(rowParticipateBinding.root){
            var textViewRowParticipateUserName : TextView

            init {
                textViewRowParticipateUserName  = rowParticipateBinding.participateRowUserName
            }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipateViewHolderClass {
            val rowParticipateBinding = RowParticipateBinding.inflate(layoutInflater)
            val ParticipateViewHolderClass = ParticipateViewHolderClass(rowParticipateBinding)

            rowParticipateBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return ParticipateViewHolderClass
        }

        override fun getItemCount(): Int {
            return participantsList.size
        }

        override fun onBindViewHolder(holder: ParticipateViewHolderClass, position: Int) {
            holder.textViewRowParticipateUserName.text = participantsList[position].name
        }
    }

    override fun onResume() {
        super.onResume()


        // 리사이클러뷰 갱신
        fragmentParticipateBinding.recyclerViewParticipate.adapter?.notifyDataSetChanged()
    }



}