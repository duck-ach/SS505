package com.seoulWomenTech.ss505

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.seoulWomenTech.ss505.databinding.FragmentPostBinding
import com.seoulWomenTech.ss505.databinding.RowPostBinding

class PostFragment : Fragment() {

    private lateinit var fragmentPostBinding: FragmentPostBinding
    private lateinit var mainActivity: MainActivity
    private var postList = mutableListOf<PostClass>()
    private var userList = mutableListOf<UserInfo>()
    private var postImageList = mutableListOf<PostImageClass>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPostBinding = FragmentPostBinding.inflate(inflater, container, false)
        mainActivity = activity as MainActivity

        // postList, userList, postImageList 초기화 (데이터베이스에서 가져옴)
        postList = PostDAO.selectAllData(mainActivity)
        userList = UserInfoDAO.selectAllData(mainActivity)
        postImageList = PostImageDao.selectAllData(mainActivity)

        fragmentPostBinding.run {
            recyclerViewPost.run {
                adapter = PostRecyclerViewAdapter(postList, userList, postImageList)
                layoutManager = LinearLayoutManager(mainActivity)
                addItemDecoration(
                    DividerItemDecoration(
                        mainActivity,
                        DividerItemDecoration.VERTICAL
                    )
                )
            }
        }
        val buttonWrite: Button = fragmentPostBinding.buttonWrite
        buttonWrite.setOnClickListener {
            mainActivity.replaceFragment(MainActivity.WRITE_FRAGMENT, true, null)
        }
        return fragmentPostBinding.root
    }

    inner class PostRecyclerViewAdapter(
        private val postList: List<PostClass>,
        private val userList: List<UserInfo>,
        private val postImageList: List<PostImageClass>
    ) : RecyclerView.Adapter<PostRecyclerViewAdapter.PostViewHolderClass>() {

        inner class PostViewHolderClass(private val rowPostBinding: RowPostBinding) :
            RecyclerView.ViewHolder(rowPostBinding.root) {

            fun bindData(post: PostClass, user: UserInfo?, postImage: PostImageClass?) {
                user?.let {
                    rowPostBinding.textViewUserName.text = it.name
//                    Glide.with(rowPostBinding.root)
//                        .load(it.image)
//                        .transform(CircleCrop())
//                        .placeholder(R.drawable.placeholder_image)
//                        .into(rowPostBinding.imageViewUser)
                    val imgSrc = mainActivity.resources.getIdentifier(it.image, "drawable", mainActivity.packageName)

                    if(imgSrc!=null){
                        rowPostBinding.imageViewUser.setImageResource(imgSrc)
                    }

                    rowPostBinding.imageViewUser.clipToOutline = true
                }

                rowPostBinding.textViewPostTitle.text = post.post_title




                postImage?.let {
                    val imgSrc = mainActivity.resources.getIdentifier(it.pi_url, "drawable", mainActivity.packageName)

                    if(imgSrc!=null){
                        rowPostBinding.imageViewPostImg.setImageResource(imgSrc)
                    }
//                    Glide.with(rowPostBinding.root)
//                        .load(it.pi_url)
//                        .placeholder(R.drawable.placeholder_2)
//                        .into(rowPostBinding.imageViewPostImg)
                }

                rowPostBinding.root.setOnClickListener {
                    // 해당 게시물에 대한 상세 정보를 보여주는 화면으로 이동
                    mainActivity.rowPosition = postList[adapterPosition].post_id
                    mainActivity.replaceFragment(MainActivity.POSTDETAIL_FRAGMENT, true, null)
                }
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolderClass {
            val rowPostBinding = RowPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return PostViewHolderClass(rowPostBinding)
        }

        override fun getItemCount(): Int {
            return postList.size
        }

        override fun onBindViewHolder(holder: PostViewHolderClass, position: Int) {
            val post = postList[position]

            // 해당 게시물에 대한 사용자 정보 가져오기 (post.user_id와 userList.user_id를 비교하여 찾음)
            val user = userList.find { it.idx == post.user_id }

            // 해당 게시물에 대한 이미지 정보 가져오기 (post.post_id와 postImageList.post_id를 비교하여 찾음)
            val postImage = postImageList.find { it.post_id == post.post_id }

            holder.bindData(post, user, postImage)
        }
    }

    override fun onResume() {
        super.onResume()

        // 데이터베이스에서 최신 데이터를 가져옴
        postList = PostDAO.selectAllData(mainActivity)
        userList = UserInfoDAO.selectAllData(mainActivity)
        postImageList = PostImageDao.selectAllData(mainActivity)

        // 리사이클러뷰 어댑터 갱신
        fragmentPostBinding.recyclerViewPost.adapter?.notifyDataSetChanged()
    }

}
