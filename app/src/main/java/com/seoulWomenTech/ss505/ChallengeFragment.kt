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
import com.seoulWomenTech.ss505.databinding.FragmentChallengeBinding
import com.seoulWomenTech.ss505.databinding.RowChallengeBinding


class ChallengeFragment : Fragment() {

    lateinit var fragmentChallengeBinding: FragmentChallengeBinding
    lateinit var mainActivity: MainActivity

    var challengeList = mutableListOf<ChallengeClass>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentChallengeBinding = FragmentChallengeBinding.inflate(inflater)
        mainActivity = activity as MainActivity


        fragmentChallengeBinding.run{



//            recyclerViewChallenge.run{
//                adapter = ChallengeRecyclerViewAdapter()
//                layoutManager = LinearLayoutManager(mainActivity)
//                addItemDecoration(DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL))
//            }

        }


        // Inflate the layout for this fragment
        return fragmentChallengeBinding.root
    }

//    inner class ChallengeRecyclerViewAdapter : RecyclerView.Adapter<ChallengeRecyclerViewAdapter.ChallengeViewHolderClass>(){
//
//        inner class ChallengeViewHolderClass(rowChallengeBinding: RowChallengeBinding) : RecyclerView.ViewHolder(rowChallengeBinding.root){
//            var textViewRowChallengeDeadLine: TextView
//            var textViewRowChallengeTitle: TextView
//            var challengeBtnParticipate : Button
//
//            init {
//                textViewRowChallengeDeadLine = rowChallengeBinding.challengeRowDeadLine
//                textViewRowChallengeTitle = rowChallengeBinding.challengeRowTitle
//                challengeBtnParticipate = rowChallengeBinding.challengeBtnParticipate
//
//                rowChallengeBinding.root.setOnClickListener {
//                    mainActivity.rowPosition = adapterPosition
//                    challengeBtnParticipate.setOnClickListener {
//                        mainActivity.replaceFragment(MainActivity.PARTICIPATE_FRAGMENT, true, null)
//                    }
//                }
//            }
//
//
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolderClass {
//            val rowChallengeBinding = RowChallengeBinding.inflate(layoutInflater)
//            val ChallengeViewHolderClass = ChallengeViewHolderClass(rowChallengeBinding)
//
//            rowChallengeBinding.root.layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//
//            return ChallengeViewHolderClass
//        }
//
//        override fun getItemCount(): Int {
//            return challengeList.size
//        }
//
//        override fun onBindViewHolder(holder: ChallengeViewHolderClass, position: Int) {
//            holder.textViewRowChallengeDeadLine.text = challengeList[position].progDate
//            holder.textViewRowChallengeTitle.text = challengeList[position].name
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//
//        challengeList = ChallengeDAO.selectAllData(mainActivity)
//
//        // 리사이클러뷰 갱신
//        fragmentChallengeBinding.recyclerViewChallenge.adapter?.notifyDataSetChanged()
//    }


}