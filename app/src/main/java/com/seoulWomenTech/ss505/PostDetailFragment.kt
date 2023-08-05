package com.seoulWomenTech.ss505

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.android.material.snackbar.Snackbar
import com.seoulWomenTech.ss505.databinding.FragmentPostDetailBinding
import com.seoulWomenTech.ss505.databinding.RowCommentBinding
import com.seoulWomenTech.ss505.databinding.RowPostImageBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PostDetailFragment : Fragment() {

    private lateinit var fragmentPostDetailBinding: FragmentPostDetailBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var post: PostClass
    private lateinit var user: UserInfo
    private var postImageList = mutableListOf<PostImageClass>()
    private var commentList = mutableListOf<CommentClass>()
    private var userList = mutableListOf<UserInfo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentPostDetailBinding = FragmentPostDetailBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        // 선택된 post_id와 일치하는 데이터 가져오기
        post = PostDAO.selectData(mainActivity, mainActivity.rowPosition)
        user = UserInfoDAO.selectData(mainActivity, post.user_id)
        userList = UserInfoDAO.selectAllData(mainActivity)
        postImageList = PostImageDao.selectData(mainActivity, post.post_id)
        commentList = CommentDAO.selectData(mainActivity, post.post_id)

        val postImageUrls = postImageList.map { it.pi_url }

        // UI에 데이터 표시
        displayPostDetails()

        // ViewPager2 설정
        val viewPager: RecyclerView = fragmentPostDetailBinding.recyclerViewPostImages
        viewPager.adapter = PostImageAdapter(postImageUrls)

        // 댓글 리스트 설정
        val recyclerViewComment: RecyclerView = fragmentPostDetailBinding.recyclerViewComment
        recyclerViewComment.adapter = CommentRecyclerViewAdapter(commentList, userList)
        recyclerViewComment.layoutManager = LinearLayoutManager(mainActivity)

        fragmentPostDetailBinding.buttonAddComment.setOnClickListener {
            val content = fragmentPostDetailBinding.editTextComment.text.toString()

            if (content.isEmpty()) {
                Toast.makeText(requireContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                // TODO: 데이터베이스에 글 등록 로직 구현
                val comment = CommentClass(
                    post_id = post.post_id,
                    content = content,
                    date = getCurrentDate(),
                    writer_id = 1,
                    c_id = 5
                )

                CommentDAO.insertData(mainActivity, comment)

                val commentAdapter = recyclerViewComment.adapter
                commentList.add(comment)
                commentAdapter?.notifyItemInserted(commentList.size - 1)
                recyclerViewComment.scrollToPosition(commentList.size - 1)
                
                // EditText 초기화
                fragmentPostDetailBinding.editTextComment.text.clear()
            }
        }
        return fragmentPostDetailBinding.root
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(Date())
    }

    private fun displayPostDetails() {
        post?.let {
            // Display user information
            user?.let {

                // Glide.with(fragmentPostDetailBinding.root)
                //     .load(it.image)
                //     .transform(CircleCrop())
                //     .placeholder(R.drawable.placeholder_image)
                //     .into(fragmentPostDetailBinding.imageViewUser)
                val imgSrc = mainActivity.resources.getIdentifier(it.image, "drawable", mainActivity.packageName)

                if(imgSrc!=null){
                    fragmentPostDetailBinding.imageViewUser.setImageResource(imgSrc)
                }

                fragmentPostDetailBinding.imageViewUser.clipToOutline = true
                fragmentPostDetailBinding.textViewUserName.text = it.name
            }
            Log.d(post.post_date, post.post_title)
            fragmentPostDetailBinding.textViewPostDate.text = post.post_date
            fragmentPostDetailBinding.textViewPostTitle.text = post.post_title
            fragmentPostDetailBinding.textViewPostContent.text = post.post_content
        }
    }

    private inner class PostImageAdapter(private val images: List<String>) :
        RecyclerView.Adapter<PostImageAdapter.ImageViewHolder>() {

        inner class ImageViewHolder(private val rowPostImageBinding: RowPostImageBinding) :
            RecyclerView.ViewHolder(rowPostImageBinding.root) {

            fun bind(imageUrl: String) {
                // Glide.with(rowPostImageBinding.root)
                //     .load(imageUrl)
                //     .placeholder(R.drawable.placeholder_2)
                //     .into(rowPostImageBinding.imageViewPostImage)
                val imgSrc = mainActivity.resources.getIdentifier(imageUrl, "drawable", mainActivity.packageName)

                if(imgSrc!=null){
                    rowPostImageBinding.imageViewPostImage.setImageResource(imgSrc)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
            val rowPostImageBinding = RowPostImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ImageViewHolder(rowPostImageBinding)
        }

        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
            holder.bind(images[position])
        }

        override fun getItemCount(): Int {
            return images.size
        }
    }

    private inner class CommentRecyclerViewAdapter(
        private val commentList: List<CommentClass>,
        private val userList: List<UserInfo>
    ) : RecyclerView.Adapter<CommentRecyclerViewAdapter.CommentViewHolder>() {

        inner class CommentViewHolder(private val rowCommentBinding: RowCommentBinding) :
            RecyclerView.ViewHolder(rowCommentBinding.root) {

            fun bindData(comment: CommentClass, user: UserInfo?) {
                user?.let {
                    val imgSrc = mainActivity.resources.getIdentifier(it.image, "drawable", mainActivity.packageName)

                    if(imgSrc!=null){
                        rowCommentBinding.imageViewProfile.setImageResource(imgSrc)
                    }
                    //  Glide.with(rowCommentBinding.root)
                    //      .load(it.image)
                    //     .transform(CircleCrop())
                    //     .placeholder(R.drawable.placeholder_image)
                    //    .into(rowCommentBinding.imageViewProfile)
                    rowCommentBinding.textViewUserName.text = it.name
                }
                Log.d(comment.content, comment.date)
                rowCommentBinding.textViewContent.text = comment.content
                rowCommentBinding.textViewDate.text = comment.date
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
            val rowCommentBinding = RowCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return CommentViewHolder(rowCommentBinding)
        }

        override fun getItemCount(): Int {
            return commentList.size
        }

        override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
            val comment = commentList[position]

            // Get the user for the current comment (comment.writer_id) from the userList
            val user = userList.find { it.idx == comment.writer_id }

            holder.bindData(comment, user)
        }
    }

    override fun onResume() {
        super.onResume()
        userList = UserInfoDAO.selectAllData(mainActivity)
        commentList = CommentDAO.selectData(mainActivity, post.post_id)
    }
}