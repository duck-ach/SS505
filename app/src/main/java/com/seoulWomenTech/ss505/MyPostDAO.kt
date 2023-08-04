package com.seoulWomenTech.ss505


import android.content.ContentValues
import android.content.Context
import android.util.Log

class MyPostDAO {

    companion object {
        // Create : 저장
        fun insertData(context: Context, data:PostClass){
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


        // Read All : 모든 행을 가져온다
        fun selectAllData(context: Context):MutableList<ChallengeClass>{

            val dbHelper = DBHelper(context)

            val cursor = dbHelper.writableDatabase.query("Challenge", null, null, null, null, null, null)

            val dataList = mutableListOf<ChallengeClass>()

            while(cursor.moveToNext()){
                val idx1 = cursor.getColumnIndex("CLG_ID")
                val idx2 = cursor.getColumnIndex("ADMIN_ID")
                val idx3 = cursor.getColumnIndex("ADDR_ID")
                val idx4 = cursor.getColumnIndex("CLG_NM")
                val idx5 = cursor.getColumnIndex("CLG_CONTENT")
                val idx6 = cursor.getColumnIndex("CLG_POST_DATE")
                val idx7 = cursor.getColumnIndex("CLG_PROG_DATE")
                val idx8 = cursor.getColumnIndex("CLG_PROG_TIME")
                val idx9 = cursor.getColumnIndex("CLG_MAX_USER")
                val idx10 = cursor.getColumnIndex("IS_CLG_ACTIVE")
                val idx11 = cursor.getColumnIndex("CLG_REWORD")
                val idx12 = cursor.getColumnIndex("CLG_IMG")

                Log.d("쿼리",idx1.toString())
                Log.d("쿼리",idx2.toString())

                val clg_id = cursor.getInt(idx1)
                val admin_id = cursor.getInt(idx2)
                val addr_id = cursor.getInt(idx3)
                val clg_nm = cursor.getString(idx4)
                val clg_content = cursor.getString(idx5)
                val clg_post_date = cursor.getString(idx6)
                val clg_prog_date = cursor.getString(idx7)
                val clg_prog_time = cursor.getString(idx8)
                val clg_max_user = cursor.getInt(idx9)
                val is_clg_active = cursor.getInt(idx10)
                val clg_reword = cursor.getInt(idx11)
                val clg_img = cursor.getString(idx12)


                val challengeClass =ChallengeClass(clg_id,admin_id,addr_id,clg_nm,clg_content,clg_post_date,clg_prog_date,clg_prog_time,clg_max_user,is_clg_active,clg_reword,clg_img)

                dataList.add(challengeClass)
            }


            dbHelper.close()

            return dataList
        }
        // Update : 조건에 맞는 행의 컬럼의 값을 수정한다.
        fun updateData(context:Context, obj:ChallengeClass){
            val cv = ContentValues()
            cv.put("ADMIN_ID", obj.admin_id)
            cv.put("ADDR_ID", obj.addr_id)
            cv.put("CLG_NM", obj.name)
            cv.put("CLG_CONTENT", obj.content)
            cv.put("CLG_POST_DATE", obj.postDate)
            cv.put("CLG_PROG_DATE", obj.progDate)
            cv.put("CLG_PROG_TIME", obj.progTime)
            cv.put("CLG_MAX_USER", obj.maxUser)
            cv.put("IS_CLG_ACTIVE", obj.active)
            cv.put("CLG_REWORD", obj.reword)
            cv.put("CLG_IMG", obj.img)
            val condition = "clg_id = ?"
            val args = arrayOf("${obj.idx}")
            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.update("Challenge", cv, condition, args)
            dbHelper.close()
        }


        // Delete : 조건 맞는 행을 삭제한다.
        fun deleteData(context:Context, addr_id:Int){
            val condition = "addr_id = ?"
            val args = arrayOf("$addr_id")

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.delete("ADDR", condition, args)
            dbHelper.close()
        }
    }

}