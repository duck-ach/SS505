package com.seoulWomenTech.ss505

import android.os.Bundle
import android.util.Log
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
import java.util.function.Predicate


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
        Log.d("인증샷챌린지",challengeList.toString())

        val cpiChallengeList = CpiDAO.selectDataByUserId(mainActivity,mainActivity.userPosition).map{ cpi -> cpi.clg_id } as MutableList<Int>

        Log.d("인증샷모두",CpiDAO.selectAllData(mainActivity).toString())
        Log.d("인증샷",CpiDAO.selectDataByUserId(mainActivity,2).toString())

        // cpiChallengList에 challenge 번호가 없는 것들은 인증샷 제출하지 않은 것이라 active로 제출 된 것은 disabled로
        challengeActiveList = challengeList.filterNot { it.idx in cpiChallengeList } as MutableList<ChallengeClass>
        challengeDisabledList = challengeList.filter { it.idx in cpiChallengeList } as MutableList<ChallengeClass>


        fragmentMyChallengeBinding.run {
            recyclerViewMyChallengeActive.run {
                adapter = MyChallengeActiveRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
                addItemDecoration(DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL))
            }

            recyclerViewmyChallengeDisabled.run {
                adapter = MyChallengeDisabledRecyclerViewAdapter()
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
                        mainActivity.rowPosition = challengeActiveList[adapterPosition].idx
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
            return challengeActiveList.size
        }

        override fun onBindViewHolder(holder: MyChallengeFragment.MyChallengeActiveRecyclerViewAdapter.MyChallengeActiveViewHolderClass, position: Int) {
            holder.textViewRowChallengeDeadLine.text = challengeActiveList[position].progDate
            holder.textViewRowChallengeTitle.text = challengeActiveList[position].name
        }
    }


    inner class MyChallengeDisabledRecyclerViewAdapter : RecyclerView.Adapter<MyChallengeDisabledRecyclerViewAdapter.MyChallengeDisabledViewHolderClass>(){

        inner class MyChallengeDisabledViewHolderClass(rowChallengeBinding: RowChallengeBinding) : RecyclerView.ViewHolder(rowChallengeBinding.root){
            var textViewRowChallengeDeadLine: TextView
            var textViewRowChallengeTitle: TextView
            var challengeBtnParticipate : Button

            init {
                textViewRowChallengeDeadLine = rowChallengeBinding.challengeRowDeadLine
                textViewRowChallengeTitle = rowChallengeBinding.challengeRowTitle
                challengeBtnParticipate = rowChallengeBinding.challengeBtnParticipate
                challengeBtnParticipate.text = "인증샷 제출 완료"


            }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyChallengeFragment.MyChallengeDisabledRecyclerViewAdapter.MyChallengeDisabledViewHolderClass {
            val rowChallengeBinding = RowChallengeBinding.inflate(layoutInflater)
            val ChallengeViewHolderClass = MyChallengeDisabledViewHolderClass(rowChallengeBinding)

            rowChallengeBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return ChallengeViewHolderClass
        }

        override fun getItemCount(): Int {
            return challengeDisabledList.size
        }

        override fun onBindViewHolder(holder: MyChallengeFragment.MyChallengeDisabledRecyclerViewAdapter.MyChallengeDisabledViewHolderClass, position: Int) {
            holder.textViewRowChallengeDeadLine.text = challengeDisabledList[position].progDate
            holder.textViewRowChallengeTitle.text = challengeDisabledList[position].name
        }
    }

    override fun onResume() {
        super.onResume()

        val cpiChallengeList = CpiDAO.selectDataByUserId(mainActivity,mainActivity.userPosition).map{ cpi -> cpi.clg_id } as MutableList<Int>
        challengeActiveList = challengeList.filterNot { it.idx in cpiChallengeList } as MutableList<ChallengeClass>
        challengeDisabledList = challengeList.filter { it.idx in cpiChallengeList } as MutableList<ChallengeClass>

        // 리사이클러뷰 갱신
        fragmentMyChallengeBinding.recyclerViewMyChallengeActive.adapter?.notifyDataSetChanged()
        fragmentMyChallengeBinding.recyclerViewmyChallengeDisabled.adapter?.notifyDataSetChanged()
    }



}