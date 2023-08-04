package com.seoulWomenTech.ss505

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
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
    var challengeTemp = mutableListOf<ChallengeClass>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentChallengeBinding = FragmentChallengeBinding.inflate(inflater)
        mainActivity = activity as MainActivity


        val user = UserInfoDAO.selectData(mainActivity,mainActivity.userPosition)
        val userAddrNum = user.address?.split(",")?.map { n -> n.toInt() } as MutableList<Int>
        val userAddrList = mutableListOf<AddrClass>()
        var addrItem = mutableListOf<String>()

        for(i in userAddrNum) {
            userAddrList.add(AddrDAO.selectData(mainActivity, i))
            ChallengeDAO.selectByAddrID(mainActivity, i).forEach {
                challengeTemp.add(it)
            }
        }

        for(i in userAddrList){
            addrItem.add("${i.g_nm} ${i.d_nm}")
        }
        addrItem.add(0,"전체")


        fragmentChallengeBinding.run{

            recyclerViewChallenge.run{
                adapter = ChallengeRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
                addItemDecoration(DividerItemDecoration(mainActivity, DividerItemDecoration.VERTICAL))
            }

            if(challengeTemp.isEmpty()){
                challengeAddrList.visibility = View.GONE
                textViewChallenge.run {
                    visibility = View.VISIBLE
                }

            }

            val addrAdapter = ArrayAdapter(requireContext(), R.layout.list_item, addrItem)
            (challengeAddrList.editText as AutoCompleteTextView).setAdapter(addrAdapter)
            // AutoCompleteTextView의 값이 변경될 때마다 호출되는 리스너를 추가합니다.
            challengeAddrList.editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    var addrListItem = s.toString()
                    challengeList = if(addrListItem == "전체"){
                        challengeTemp
                    } else{
                        val dongIdx = AddrDAO.selectByDNM(mainActivity, addrListItem.split(" ")[1])
                            .map { addr -> addr.idx }[0]
                        ChallengeDAO.selectByAddrID(mainActivity,dongIdx)
                    }
                    fragmentChallengeBinding.recyclerViewChallenge.adapter?.notifyDataSetChanged()

                }
            })

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

        challengeList = challengeTemp

        // 리사이클러뷰 갱신
        fragmentChallengeBinding.recyclerViewChallenge.adapter?.notifyDataSetChanged()
    }


}