package com.seoulWomenTech.ss505

import android.content.ContentValues
import android.content.Context
import android.util.Log

class CommentDAO {
    companion object {
        // Create : 저장
        fun insertData(context: Context, data: CommentClass){
            // 컬럼이름과 데이터를 설정하는 객체
            val contentValues = ContentValues()
            // 컬럼 이름, 값을 지정한다.
            contentValues.put("C_ID", data.c_id)
            contentValues.put("POST_ID", data.post_id)
            contentValues.put("C_WRITER_ID", data.writer_id)
            contentValues.put("C_CONTENT", data.content)
            contentValues.put("C_DATE", data.date)

            val dbHelper = DBHelper(context)

            dbHelper.writableDatabase.insert("Comment", null, contentValues)
            dbHelper.close()
        }

        // Read Condition : 조건에 맞는 행 하나를 가져온다.
        fun selectData(context: Context, post_id:Int):MutableList<CommentClass>{

            val dbHelper = DBHelper(context)
            val selection = "post_id = ?"
            val args = arrayOf("$post_id")
            val cursor = dbHelper.writableDatabase.query("Comment", null, selection, args, null, null, null)
            val dataList = mutableListOf<CommentClass>()
            while(cursor.moveToNext()){

            val idx1 = cursor.getColumnIndex("C_ID")
            val idx2 = cursor.getColumnIndex("POST_ID")
            val idx3 = cursor.getColumnIndex("C_WRITER_ID")
            val idx4 = cursor.getColumnIndex("C_CONTENT")
            val idx5 = cursor.getColumnIndex("C_DATE")

            Log.d("쿼리",idx1.toString())
            Log.d("쿼리",idx2.toString())

            val c_id = cursor.getInt(idx1)
            val post_id = cursor.getInt(idx2)
            val writer_id = cursor.getInt(idx3)
            val content = cursor.getString(idx4)
            val date = cursor.getString(idx5)


            val CommentClass =CommentClass(c_id,post_id,writer_id,content,date)
                dataList.add(CommentClass)
            }

            dbHelper.close()
            return dataList
        }

        // Read All : 모든 행을 가져온다
        fun selectAllData(context: Context):MutableList<CommentClass>{

            val dbHelper = DBHelper(context)

            val cursor = dbHelper.writableDatabase.query("Comment", null, null, null, null, null, null)

            val dataList = mutableListOf<CommentClass>()

            while(cursor.moveToNext()){
                val idx1 = cursor.getColumnIndex("C_ID")
                val idx2 = cursor.getColumnIndex("POST_ID")
                val idx3 = cursor.getColumnIndex("C_WRITER_ID")
                val idx4 = cursor.getColumnIndex("C_CONTENT")
                val idx5 = cursor.getColumnIndex("C_DATE")

                Log.d("쿼리",idx1.toString())
                Log.d("쿼리",idx2.toString())

                val c_id = cursor.getInt(idx1)
                val post_id = cursor.getInt(idx2)
                val writer_id = cursor.getInt(idx3)
                val content = cursor.getString(idx4)
                val date = cursor.getString(idx5)


                val CommentClass =CommentClass(c_id,post_id,writer_id,content,date)

                dataList.add(CommentClass)
            }


            dbHelper.close()

            return dataList
        }
        // Update : 조건에 맞는 행의 컬럼의 값을 수정한다.
        fun updateData(context: Context, obj:CommentClass){
            val cv = ContentValues()
            cv.put("C_ID", obj.c_id)
            cv.put("POST_ID", obj.post_id)
            cv.put("C_WRITER_ID", obj.writer_id)
            cv.put("C_CONTENT", obj.content)
            cv.put("C_DATE", obj.date)

            val condition = "c_id = ?"
            val args = arrayOf("${obj.c_id}")
            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.update("Comment", cv, condition, args)
            dbHelper.close()
        }


        // Delete : 조건 맞는 행을 삭제한다.
        fun deleteData(context: Context, c_id:Int){
            val condition = "c_id = ?"
            val args = arrayOf("$c_id")

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.delete("Comment", condition, args)
            dbHelper.close()
        }
    }

}