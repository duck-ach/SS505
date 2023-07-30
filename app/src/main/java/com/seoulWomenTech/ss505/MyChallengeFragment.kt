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
import com.seoulWomenTech.ss505.databinding.FragmentMyChallengeBinding
import com.seoulWomenTech.ss505.databinding.RowChallengeBinding
import com.seoulWomenTech.ss505.databinding.RowParticipateBinding


class MyChallengeFragment : Fragment() {
    lateinit var fragmentMyChallengeBinding: FragmentMyChallengeBinding
    lateinit var mainActivity: MainActivity

    var challengeList = mutableListOf<ChallengeClass>()
    var challengeActiveList = mutableListOf<ChallengeClass>()
    var challengeDisabledList = mutableListOf<ChallengeClass>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMyChallengeBinding = FragmentMyChallengeBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        var challengeParticipate = ParticipantsDAO.selectByUserId(mainActivity, mainActivity.userPosition)
        challengeList = challengeParticipate.map { p ->ChallengeDAO.selectData(mainActivity,p.clg_id) } as MutableList


        fragmentMyChallengeBinding.run {
            recyclerViewMyChallengeActive.run {
                adapter = MyChallengeActiveRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
                addItemDecoration(DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL))
            }
        }
        return fragmentMyChallengeBinding.root
    }

    inner class MyChallengeActiveRecyclerViewAdapter : RecyclerView.Adapter<MyChallengeActiveRecyclerViewAdapter.MyChallengeActiveViewHolderClass>(){

        inner class MyChallengeActiveViewHolderClass(rowChallengeBinding: RowChallengeBinding) : RecyclerView.ViewHolder(rowChallengeBinding.root){
            var textViewRowChallengeDeadLine: TextView
            var textViewRowChallengeTitle: TextView
            var challengeBtnParticipate : Button

            init {
                textViewRowChallengeDeadLine = rowChallengeBinding.challengeRowDeadLine
                textViewRowChallengeTitle = rowChallengeBinding.challengeRowTitle
                challengeBtnParticipate = rowChallengeBinding.challengeBtnParticipate
                challengeBtnParticipate.run {
                    text = "인증샷 제출"
                    setOnClickListener {
                        mainActivity.rowPosition = challengeList[adapterPosition].idx
                        mainActivity.replaceFragment(MainActivity.CPI_FRAGMENT, true, null)

                    }
                }

            }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyChallengeFragment.MyChallengeActiveRecyclerViewAdapter.MyChallengeActiveViewHolderClass {
            val rowChallengeBinding = RowChallengeBinding.inflate(layoutInflater)
            val ChallengeViewHolderClass = MyChallengeActiveViewHolderClass(rowChallengeBinding)

            rowChallengeBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return ChallengeViewHolderClass
        }

        override fun getItemCount(): Int {
            return challengeList.size
        }

        override fun onBindViewHolder(holder: MyChallengeFragment.MyChallengeActiveRecyclerViewAdapter.MyChallengeActiveViewHolderClass, position: Int) {
            holder.textViewRowChallengeDeadLine.text = challengeList[position].progDate
            holder.textViewRowChallengeTitle.text = challengeList[position].name
        }
    }

    override fun onResume() {
        super.onResume()

//        challengeList = ChallengeDAO.selectAllData(mainActivity)

        // 리사이클러뷰 갱신
        fragmentMyChallengeBinding.recyclerViewMyChallengeActive.adapter?.notifyDataSetChanged()
    }



}