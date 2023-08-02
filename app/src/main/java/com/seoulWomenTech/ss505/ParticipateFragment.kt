package com.seoulWomenTech.ss505

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seoulWomenTech.ss505.databinding.FragmentParticipateBinding
import com.seoulWomenTech.ss505.databinding.RowParticipateBinding

class ParticipateFragment : Fragment() {

    lateinit var fragmentParticipateBinding: FragmentParticipateBinding
    lateinit var mainActivity: MainActivity

    var participantsList = mutableListOf<UserInfo>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentParticipateBinding = FragmentParticipateBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        // 임시 유저 클래스( 현재 로그인한 유저 정보를 알 수 있는 방법이 없으므로 )
        val userTemp = UserInfoDAO.selectData(mainActivity, mainActivity.userPosition)

        // 현재 챌린지 정보 가져오기
        val challenge = ChallengeDAO.selectData(mainActivity,mainActivity.rowPosition)


        fragmentParticipateBinding.run {

            toolbarParticipate.run{
                title = "Safety Seoul"

                
                setNavigationOnClickListener {
                    // 네비게이션 뷰를 보여준다.


                }


            }

            val imgSrc = mainActivity.resources.getIdentifier(challenge.img, "drawable", mainActivity.packageName)

//            Log.d("이미지",imgSrc.)
            if(imgSrc!=null){
                participateImg.setImageResource(imgSrc)
            }

            participateImg.clipToOutline = true


            participateTitle.text = challenge.name
            participateDate.append("${challenge.progDate} ${challenge.progTime}")
            participateMaxUserText.append("${challenge.maxUser}명")
            participateLocation.append(challenge.location)
            participateContent.append(challenge.content)


            // 첼린지에 참여하는 유저 정보 가져오기
            var participantsTemp = ParticipantsDAO.selectByClgId(mainActivity,challenge.idx)

            //현재 로그인 한 유저가 참여 중인지 확인
            var isParticipate = participantsTemp.filter { p -> p.user_id == userTemp!!.idx }.size

            Log.d("사용자","$isParticipate")

            if (isParticipate>0){
                btnParticipate.text = "참여 취소"
                btnParticipate.setBackgroundResource(R.drawable.button_round_red)
            }

            participantsList = participantsTemp.map { p -> UserInfoDAO.selectData(mainActivity,p.user_id)} as MutableList<UserInfo>

//            Log.d("참여자리스트",participantsTemp.map { p -> UserInfoDAO.selectData(mainActivity,p.user_id)}.toString())

            val challengeAdmin = UserInfoDAO.selectData(mainActivity,challenge.admin_id)

            Log.d("관리자",UserInfoDAO.selectData(mainActivity,challenge.admin_id).toString())

            val challengeAdminImgSrc = mainActivity.resources.getIdentifier(challengeAdmin?.image, "drawable", mainActivity.packageName)
            if(challengeAdminImgSrc!=null){
                participateAdminImg.setImageResource(challengeAdminImgSrc)
            }

            val crownUnicode = 0x1F451
            val emojiText = "${String(Character.toChars(crownUnicode))}"
            participateAdminName.text = "$emojiText ${challengeAdmin?.name}"

            participateMaxUser.text = ("참여자( ${participantsList.size} / ${challenge.maxUser} )")

            btnParticipate.setOnClickListener {
                val participant = ParticipantsClass(challenge.idx, userTemp!!.idx)
                if(isParticipate==0){
                    ParticipantsDAO.insertData(mainActivity,participant)
                    participantsTemp = ParticipantsDAO.selectByClgId(mainActivity,challenge.idx)
                    isParticipate = 1
                    btnParticipate.text = "참여 취소"
                    btnParticipate.setBackgroundResource(R.drawable.button_round_red)
                } else {
                    ParticipantsDAO.deleteData(mainActivity,participant.clg_id,participant.user_id)
                    participantsTemp = ParticipantsDAO.selectByClgId(mainActivity,challenge.idx)
                    isParticipate =0
                    btnParticipate.text = "참여"
                    btnParticipate.setBackgroundResource(R.drawable.button_round)

                }

                participantsTemp = ParticipantsDAO.selectByClgId(mainActivity,challenge.idx)

                participantsList = participantsTemp.map { p -> UserInfoDAO.selectData(mainActivity,p.user_id)} as MutableList<UserInfo>

//            Log.d("참여자리스트",participantsTemp.map { p -> UserInfoDAO.selectData(mainActivity,p.user_id)}.toString())

                participateMaxUser.text = ("참여자( ${participantsList.size} / ${challenge.maxUser} )")

                // 리사이클러뷰 갱신자
                recyclerViewParticipate.adapter?.notifyDataSetChanged()

            }


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
            var textViewRowParticipateUserImg : ImageView
            var textViewRowParticipateUserName : TextView

            init {
                textViewRowParticipateUserImg = rowParticipateBinding.participateRowUserImg
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
            val participantImgSrc = mainActivity.resources.getIdentifier(participantsList[position].image, "drawable", mainActivity.packageName)

//            Log.d("이미지",imgSrc.)
            if(participantImgSrc!=null){
                holder.textViewRowParticipateUserImg.setImageResource(participantImgSrc)
            }

            holder.textViewRowParticipateUserName.text = participantsList[position].name
        }
    }

    override fun onResume() {
        super.onResume()


        // 리사이클러뷰 갱신
        fragmentParticipateBinding.recyclerViewParticipate.adapter?.notifyDataSetChanged()
    }



}