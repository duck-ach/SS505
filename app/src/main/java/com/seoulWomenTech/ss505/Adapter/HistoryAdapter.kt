package com.seoulWomenTech.ss505.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seoulWomenTech.ss505.databinding.HistoryitemBinding
import com.seoulWomenTech.ss505.model.HistoryModelClass


// RecyclerView에 데이터 (historyitem) 넣는 작업
class HistoryAdapter(var ListHistory:ArrayList<HistoryModelClass>) : RecyclerView.Adapter<HistoryAdapter.HistoryPointViewHolder>() {
    class HistoryPointViewHolder(var binding: HistoryitemBinding) :RecyclerView.ViewHolder(binding.root){

    }

    // RecyclerView가 새로운 ViewHolder 필요할 경우
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryPointViewHolder {
        return HistoryPointViewHolder(HistoryitemBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    // ListHistory 데이터셋의 전체 아이템수 리턴
    override fun getItemCount() = ListHistory.size

    // 레이아웃에 원하는 포멧 (시간, 날짜, 포인트 수)으로 데이터 보이도록
    override fun onBindViewHolder(holder: HistoryPointViewHolder, position: Int) {
        holder.binding.Time.text=ListHistory[position].timeAndDate
        holder.binding.Point.text=ListHistory[position].point
    }
}


