package com.seoulWomenTech.ss505

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.seoulWomenTech.ss505.databinding.FragmentWriteBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WriteFragment : Fragment() {

    private lateinit var fragmentWriteBinding: FragmentWriteBinding
    private var selectedCategory: String? = null
    private lateinit var mainActivity: MainActivity
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentWriteBinding = FragmentWriteBinding.inflate(inflater, container, false)

        val categories = arrayOf("[우리동네 자랑]", "[방범후기]")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fragmentWriteBinding.spinnerCategory.adapter = adapter
        fragmentWriteBinding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedCategory = categories[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        // 등록 버튼 클릭 리스너 설정
        fragmentWriteBinding.buttonSubmit.setOnClickListener {
            val title = fragmentWriteBinding.editTextTitle.text.toString()
            val content = fragmentWriteBinding.editTextContent.text.toString()

            if (selectedCategory.isNullOrEmpty() || title.isEmpty() || content.isEmpty()) {
                Toast.makeText(requireContext(), "모든 필드를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                // TODO: 데이터베이스에 글 등록 로직 구현
                val post = PostClass(
                    post_id = 5,
                    user_id = 1, // 예시로 현재 사용자 ID 사용
                    post_title = selectedCategory+"\n"+title,
                    post_content = content,
                    post_date = getCurrentDate() // 예시로 현재 날짜 사용
                )

                PostDAO.insertData(mainActivity, post)

                // 등록이 성공하면 리스트 화면으로 이동
                val mainActivity = activity as MainActivity
                mainActivity.replaceFragment(MainActivity.POST_FRAGMENT, true, null)
            }
        }

        return fragmentWriteBinding.root
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }
}