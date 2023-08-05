package com.seoulWomenTech.ss505.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seoulWomenTech.ss505.R
import com.seoulWomenTech.ss505.model.SafetyResourceModelClass

// link SafetyRecyclerView from fragment_safety.xml with safetyresource.xml based on SafetyResourceModelClass.kt
class SafetyResourceAdapter (var ListSafetyResource:ArrayList<SafetyResourceModelClass>) : RecyclerView.Adapter<SafetyResourceAdapter.SafetyResourceViewHolder>(){
    class SafetyResourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var safetyImage: ImageView
        var safetyVideoName: TextView

        init {
            safetyImage = itemView.findViewById(R.id.safety_resource)
            safetyVideoName = itemView.findViewById(R.id.safety_video_name)
        }
    }

    // RecyclerView가 새로운 ViewHolder 필요할 경우
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SafetyResourceViewHolder {
        return SafetyResourceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.safetyresource, parent, false)
        )
    }

    // ListSafetyResource 데이터셋의 전체 아이템수 리턴
    override fun getItemCount() = ListSafetyResource.size

    // 레이아웃에 원하는 포멧 (사진, 비디오 이름)으로 데이터 보이도록
    override fun onBindViewHolder(holder: SafetyResourceViewHolder, position: Int) {
        holder.safetyImage.setImageResource(ListSafetyResource[position].image)
        holder.safetyVideoName.text = ListSafetyResource[position].videoname
    }


}