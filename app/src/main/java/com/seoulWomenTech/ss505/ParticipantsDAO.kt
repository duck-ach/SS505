package com.seoulWomenTech.ss505

import android.content.ContentValues
import android.content.Context
import android.util.Log

class ParticipantsDAO {

    companion object {
        // Create : 저장
        fun insertData(context: Context, data: ParticipantsClass) {
            // 컬럼 이름과 데이터를 설정하는 객체
            val contentValues = ContentValues()
            // 컬럼 이름, 값을 지정한다.
            contentValues.put("CLG_ID", data.clg_id)
            contentValues.put("USER_ID", data.user_id)

            val dbHelper = DBHelper(context)

            dbHelper.writableDatabase.insert("Participants", null, contentValues)
            dbHelper.close()
        }

        // Read Condition : 조건에 맞는 행을 가져온다.
        fun selectData(context: Context, clg_id: Int, user_id: Int): ParticipantsClass? {
            val dbHelper = DBHelper(context)
            val selection = "CLG_ID = ? AND USER_ID = ?"
            val args = arrayOf("$clg_id", "$user_id")
            val cursor = dbHelper.writableDatabase.query("Participants", null, selection, args, null, null, null)

            var participants: ParticipantsClass? = null

            if (cursor.moveToNext()) {
                val idx1 = cursor.getColumnIndex("CLG_ID")
                val idx2 = cursor.getColumnIndex("USER_ID")

                val clg_id = cursor.getInt(idx1)
                val user_id = cursor.getInt(idx2)

                participants = ParticipantsClass(clg_id, user_id)
            }

            cursor.close()
            dbHelper.close()

            return participants
        }

        // Read All : 모든 행을 가져온다
        fun selectAllData(context: Context): MutableList<ParticipantsClass> {
            val dbHelper = DBHelper(context)

            val cursor = dbHelper.writableDatabase.query("Participants", null, null, null, null, null, null)

            val dataList = mutableListOf<ParticipantsClass>()

            while (cursor.moveToNext()) {
                val idx1 = cursor.getColumnIndex("CLG_ID")
                val idx2 = cursor.getColumnIndex("USER_ID")

                val clg_id = cursor.getInt(idx1)
                val user_id = cursor.getInt(idx2)

                val participantsClass = ParticipantsClass(clg_id, user_id)

                dataList.add(participantsClass)
            }

            cursor.close()
            dbHelper.close()

            return dataList
        }

        // Delete : 조건에 맞는 행을 삭제한다.
        fun deleteData(context: Context, clg_id: Int, user_id: Int) {
            val condition = "CLG_ID = ? AND USER_ID = ?"
            val args = arrayOf("$clg_id", "$user_id")

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.delete("Participants", condition, args)
            dbHelper.close()
        }
    }
}
