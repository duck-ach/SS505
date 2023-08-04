package com.seoulWomenTech.ss505


import android.content.ContentValues
import android.content.Context
import android.util.Log

class MyPostDAO {

    companion object {
        // Create : 저장
        fun insertData(context: Context, data: PostClass) {
            // 컬럼이름과 데이터를 설정하는 객체
            val contentValues = ContentValues()
            // 컬럼 이름, 값을 지정한다.
            contentValues.put("post_id", data.postId)
            contentValues.put("user_id", data.user_id)
            contentValues.put("post_title", data.post_title)
            contentValues.put("post_content", data.post_content)
            contentValues.put("post_date", data.post_date)
            contentValues.put("bracket", data.bracket)


            val dbHelper = DBHelper(context)

            dbHelper.writableDatabase.insert("Post", null, contentValues)
            dbHelper.close()
        }


    }

}