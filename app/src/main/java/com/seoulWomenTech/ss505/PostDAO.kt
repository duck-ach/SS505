package com.seoulWomenTech.ss505

import android.content.ContentValues
import android.content.Context
import android.util.Log

class PostDAO {
    companion object {
        // Create : 저장
        fun insertData(context: Context, data: PostClass){
            // 컬럼이름과 데이터를 설정하는 객체
            val contentValues = ContentValues()
            // 컬럼 이름, 값을 지정한다.
            contentValues.put("POST_ID", data.post_id)
            contentValues.put("WRITER_ID", data.user_id)
            contentValues.put("POST_TITLE", data.post_title)
            contentValues.put("POST_CONTENT", data.post_content)
            contentValues.put("POST_DATE", data.post_date)
            //contentValues.put("POST_BRACKET", data.post_bracket)

            val dbHelper = DBHelper(context)

            dbHelper.writableDatabase.insert("Post", null, contentValues)
            dbHelper.close()
        }

        // Read Condition : 조건에 맞는 행 하나를 가져온다.
        fun selectData(context: Context, post_Id:Int):PostClass{

            val dbHelper = DBHelper(context)
            val selection = "post_id = ?"
            val args = arrayOf("$post_Id")
            val cursor = dbHelper.writableDatabase.query("Post", null, selection, args, null, null, null)

            cursor.moveToNext()

            val idx1 = cursor.getColumnIndex("POST_ID")
            val idx2 = cursor.getColumnIndex("WRITER_ID")
            val idx3 = cursor.getColumnIndex("POST_TITLE")
            val idx4 = cursor.getColumnIndex("POST_CONTENT")
            val idx5 = cursor.getColumnIndex("POST_DATE")
           // val idx6 = cursor.getColumnIndex("POST_BRACKET")

            Log.d("쿼리",idx1.toString())
            Log.d("쿼리",idx2.toString())

            val post_id = cursor.getInt(idx1)
            val user_id = cursor.getInt(idx2)
            val post_title = cursor.getString(idx3)
            val post_content = cursor.getString(idx4)
            val post_date = cursor.getString(idx5)
           // val post_bracket = cursor.getString(idx6)


            val PostClass =PostClass(post_id,user_id,post_title,post_content,post_date)

            dbHelper.close()
            return PostClass
        }

        // Read All : 모든 행을 가져온다
        fun selectAllData(context: Context):MutableList<PostClass>{

            val dbHelper = DBHelper(context)

            val cursor = dbHelper.writableDatabase.query("Post", null, null, null, null, null, null)

            val dataList = mutableListOf<PostClass>()

            while(cursor.moveToNext()){
                val idx1 = cursor.getColumnIndex("POST_ID")
                val idx2 = cursor.getColumnIndex("WRITER_ID")
                val idx3 = cursor.getColumnIndex("POST_TITLE")
                val idx4 = cursor.getColumnIndex("POST_CONTENT")
                val idx5 = cursor.getColumnIndex("POST_DATE")
               // val idx6 = cursor.getColumnIndex("POST_BRACKET")

                Log.d("쿼리",idx1.toString())
                Log.d("쿼리",idx2.toString())

                val post_id = cursor.getInt(idx1)
                val user_id = cursor.getInt(idx2)
                val post_title = cursor.getString(idx3)
                val post_content = cursor.getString(idx4)
                val post_date = cursor.getString(idx5)
                //val post_bracket = cursor.getString(idx6)


                val PostClass =PostClass(post_id,user_id,post_title,post_content,post_date)

                dataList.add(PostClass)
            }


            dbHelper.close()

            return dataList
        }
        // Update : 조건에 맞는 행의 컬럼의 값을 수정한다.
        fun updateData(context:Context, obj:PostClass){
            val cv = ContentValues()
            cv.put("POST_ID", obj.post_id)
            cv.put("WRITER_ID", obj.user_id)
            cv.put("POST_TITLE", obj.post_title)
            cv.put("POST_CONTENT", obj.post_content)
            cv.put("POST_DATE", obj.post_date)
          //  cv.put("POST_BRACKET", obj.post_bracket)

            val condition = "post_id = ?"
            val args = arrayOf("${obj.post_id}")
            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.update("Post", cv, condition, args)
            dbHelper.close()
        }


        // Delete : 조건 맞는 행을 삭제한다.
        fun deleteData(context:Context, post_id:Int){
            val condition = "post_id = ?"
            val args = arrayOf("$post_id")

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.delete("Post", condition, args)
            dbHelper.close()
        }
    }

}
