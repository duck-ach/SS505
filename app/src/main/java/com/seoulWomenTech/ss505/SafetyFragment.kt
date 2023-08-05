package com.seoulWomenTech.ss505

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seoulWomenTech.ss505.Adapter.SafetyResourceAdapter
import com.seoulWomenTech.ss505.databinding.FragmentSafetyBinding
import com.seoulWomenTech.ss505.model.SafetyResourceModelClass

class SafetyFragment : Fragment() {
    val binding by lazy {
        FragmentSafetyBinding.inflate(layoutInflater)
    }
    private var safetydatalist = ArrayList<SafetyResourceModelClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 임의의 데이터들
        safetydatalist.add(SafetyResourceModelClass(R.drawable.cpr, "심폐소생술 안전자료"))
        safetydatalist.add(SafetyResourceModelClass(R.drawable.cpr, "심폐소생술 안전자료"))
        safetydatalist.add(SafetyResourceModelClass(R.drawable.video, "비디오 1 입니다."))
        safetydatalist.add(SafetyResourceModelClass(R.drawable.video, "비디오 2 입니다."))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.SafetyRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        var adaptor = SafetyResourceAdapter(safetydatalist)
        binding.SafetyRecyclerView.adapter = adaptor
        binding.SafetyRecyclerView.setHasFixedSize(true)

        return binding.root
    }

    companion object {

    }
}
