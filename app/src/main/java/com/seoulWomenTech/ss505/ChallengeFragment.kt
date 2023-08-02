package com.seoulWomenTech.ss505

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seoulWomenTech.ss505.databinding.FragmentChallengeBinding
import com.seoulWomenTech.ss505.databinding.RowChallengeBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


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

            recyclerViewChallenge.run{
                adapter = ChallengeRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
                addItemDecoration(DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL))
            }

        }


        // Inflate the layout for this fragment
        return fragmentChallengeBinding.root
    }

    inner class ChallengeRecyclerViewAdapter : RecyclerView.Adapter<ChallengeRecyclerViewAdapter.ChallengeViewHolderClass>(){

        inner class ChallengeViewHolderClass(rowChallengeBinding: RowChallengeBinding) : RecyclerView.ViewHolder(rowChallengeBinding.root){
            var textViewRowChallengeDeadLine: TextView
            var textViewRowChallengeDday: TextView
            var textViewRowChallengeTitle: TextView
            var challengeBtnParticipate : Button
            var textViewRowChallengeDate : TextView
            var textViewRowChallengeLocation : TextView
            var textViewRowChallengeParticipant : TextView


            init {
                textViewRowChallengeDeadLine = rowChallengeBinding.challengeRowDeadLine
                textViewRowChallengeDday = rowChallengeBinding.challengeRowDday
                textViewRowChallengeTitle = rowChallengeBinding.challengeRowTitle
                challengeBtnParticipate = rowChallengeBinding.challengeBtnParticipate

                textViewRowChallengeDate = rowChallengeBinding.challengeRowDate
                textViewRowChallengeLocation = rowChallengeBinding.challengeRowLocation
                textViewRowChallengeParticipant = rowChallengeBinding.challengeRowParticipant

                challengeBtnParticipate.setOnClickListener {
                    mainActivity.rowPosition = challengeList[adapterPosition].idx
                    mainActivity.replaceFragment(MainActivity.PARTICIPATE_FRAGMENT, true, null)

                }
            }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolderClass {
            val rowChallengeBinding = RowChallengeBinding.inflate(layoutInflater)
            val ChallengeViewHolderClass = ChallengeViewHolderClass(rowChallengeBinding)

            rowChallengeBinding.root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )

            return ChallengeViewHolderClass
        }

        override fun getItemCount(): Int {
            return challengeList.size
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun onBindViewHolder(holder: ChallengeViewHolderClass, position: Int) {
            holder.textViewRowChallengeDeadLine.text = challengeList[position].progDate


            val formatter = DateTimeFormatter.ISO_LOCAL_DATE
            val givenDate = LocalDate.parse(challengeList[position].progDate, formatter)

            // 현재 날짜
            val currentDate = LocalDate.now()

            // D-day 계산
            val daysUntilGivenDate = ChronoUnit.DAYS.between(currentDate, givenDate)

            holder.textViewRowChallengeDday.setText("D - $daysUntilGivenDate")

            holder.textViewRowChallengeTitle.text = challengeList[position].name
            holder.textViewRowChallengeDate.setText("일시 : ${challengeList[position].progDate} ${challengeList[position].progTime}")
            holder.textViewRowChallengeLocation.setText("위치 : ${challengeList[position].location}")
            holder.textViewRowChallengeParticipant.setText("모집 인원 : ${challengeList[position].maxUser}명")
        }
    }

    override fun onResume() {
        super.onResume()

        challengeList = ChallengeDAO.selectAllData(mainActivity)

        // 리사이클러뷰 갱신
        fragmentChallengeBinding.recyclerViewChallenge.adapter?.notifyDataSetChanged()
    }


}