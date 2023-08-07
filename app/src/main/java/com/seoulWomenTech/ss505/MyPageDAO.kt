package com.seoulWomenTech.ss505

import android.content.Context

class MyPageDAO {

    companion object {

        fun selectMyPostData(context: Context, user_id:Int):MutableList<PostClass> {

            val dbHelper = DBHelper(context)
            val selection = "user_id"
            val cursor = dbHelper.writableDatabase.query("Post", null, selection, null, null, null, null)

            val dataList = mutableListOf<PostClass>()

            while(cursor.moveToNext()) {
                val idx1 = cursor.getColumnIndex("POST_ID")
                val idx2 = cursor.getColumnIndex("USER_ID")
                val idx3 = cursor.getColumnIndex("POST_TITLE")
                val idx4 = cursor.getColumnIndex("POST_CONTENT")
                val idx5 = cursor.getColumnIndex("POST_DATE")
                //val idx6 = cursor.getColumnIndex("BRACKET")

                val postId = cursor.getInt(idx1)
                val userId = cursor.getInt(idx2)
                val postTitle = cursor.getString(idx3)
                val postContent = cursor.getString(idx4)
                val postDate = cursor.getString(idx5)
                //val bracket = cursor.getString(idx6)

                val postClass = PostClass(postId, userId, postTitle, postContent, postDate)
                dataList.add(postClass)
            }
            dbHelper.close()
            return dataList
        }


    }

}