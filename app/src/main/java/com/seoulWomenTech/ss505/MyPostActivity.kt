package com.seoulWomenTech.ss505

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MyPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

// 정보 담을 객체
data class PostClass(var postId: Int, var user_id: Int, var post_title: String, var post_content: String, var post_date: String, var bracket: String)
data class CommentClass(var c_id: Int, var post_id: Int, var user_id: Int, var c_content: String, var c_date: String)
data class PostImageClass(var pi_id: Int, var post_id: Int, var pi_un: String, var pi_cn: String, var pi_url: String)