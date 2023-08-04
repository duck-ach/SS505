package com.seoulWomenTech.ss505

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.seoulWomenTech.ss505.Adapter.HistoryAdapter
import com.seoulWomenTech.ss505.databinding.FragmentRewardsHistoryBinding
import com.seoulWomenTech.ss505.model.HistoryModelClass

class RewardsHistoryFragment : Fragment() {
    val binding by lazy {
        FragmentRewardsHistoryBinding.inflate(layoutInflater)
    }
    private var ListHistory = ArrayList<HistoryModelClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 임의의 데이터들
        ListHistory.add(HistoryModelClass("11:30, 07.25.2023", "20"))
        ListHistory.add(HistoryModelClass("11:30, 07.30.2023", "10"))
        ListHistory.add(HistoryModelClass("11:30, 08.01.2023", "20"))
        ListHistory.add(HistoryModelClass("11:30, 08.09.2023", "20"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.HistoryRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        var adaptor = HistoryAdapter(ListHistory)
        binding.HistoryRecyclerView.adapter = adaptor
        binding.HistoryRecyclerView.setHasFixedSize(true)

        return binding.root
    }

    companion object {

    }
}


