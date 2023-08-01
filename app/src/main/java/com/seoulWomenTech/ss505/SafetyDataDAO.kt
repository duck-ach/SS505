package com.seoulWomenTech.ss505

import android.content.ContentValues
import android.content.Context

class SafetyDataDAO {

    companion object {
        // Create : 저장
        fun insertData(context: Context, data: SafetyData) {
            // 컬럼 이름과 데이터를 설정하는 객체
            val contentValues = ContentValues()
            // 컬럼 이름, 값을 지정한다.
            contentValues.put("ADMIN_ID", data.adminId)
            contentValues.put("SD_TITLE", data.title)
            contentValues.put("SD_CONTENT", data.content)
            contentValues.put("SD_DATE", data.date)

            val dbHelper = DBHelper(context)

            dbHelper.writableDatabase.insert("SafetyData", null, contentValues)
            dbHelper.close()
        }

        // Read Condition : 조건에 맞는 행 하나를 가져온다.
        fun selectData(context: Context, sdId: Int): SafetyData? {
            val dbHelper = DBHelper(context)
            val selection = "SD_ID = ?"
            val args = arrayOf("$sdId")
            val cursor = dbHelper.writableDatabase.query("SafetyData", null, selection, args, null, null, null)

            var safetyData: SafetyData? = null

            if (cursor.moveToNext()) {
                val idx1 = cursor.getColumnIndex("SD_ID")
                val idx2 = cursor.getColumnIndex("ADMIN_ID")
                val idx3 = cursor.getColumnIndex("SD_TITLE")
                val idx4 = cursor.getColumnIndex("SD_CONTENT")
                val idx5 = cursor.getColumnIndex("SD_DATE")

                val sdId = cursor.getInt(idx1)
                val adminId = cursor.getInt(idx2)
                val title = cursor.getString(idx3)
                val content = cursor.getString(idx4)
                val date = cursor.getString(idx5)

                safetyData = SafetyData(sdId, adminId, title, content, date)
            }

            cursor.close()
            dbHelper.close()

            return safetyData
        }

        // Read All : 모든 행을 가져온다
        fun selectAllData(context: Context): MutableList<SafetyData> {
            val dbHelper = DBHelper(context)

            val cursor = dbHelper.writableDatabase.query("SafetyData", null, null, null, null, null, null)

            val dataList = mutableListOf<SafetyData>()

            while (cursor.moveToNext()) {
                val idx1 = cursor.getColumnIndex("SD_ID")
                val idx2 = cursor.getColumnIndex("ADMIN_ID")
                val idx3 = cursor.getColumnIndex("SD_TITLE")
                val idx4 = cursor.getColumnIndex("SD_CONTENT")
                val idx5 = cursor.getColumnIndex("SD_DATE")

                val sdId = cursor.getInt(idx1)
                val adminId = cursor.getInt(idx2)
                val title = cursor.getString(idx3)
                val content = cursor.getString(idx4)
                val date = cursor.getString(idx5)

                val safetyData = SafetyData(sdId, adminId, title, content, date)

                dataList.add(safetyData)
            }

            cursor.close()
            dbHelper.close()

            return dataList
        }

        // Update : 조건에 맞는 행의 컬럼의 값을 수정한다.
        fun updateData(context: Context, safetyData: SafetyData) {
            val cv = ContentValues()
            cv.put("ADMIN_ID", safetyData.adminId)
            cv.put("SD_TITLE", safetyData.title)
            cv.put("SD_CONTENT", safetyData.content)
            cv.put("SD_DATE", safetyData.date)

            val condition = "SD_ID = ?"
            val args = arrayOf("${safetyData.idx}")

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.update("SafetyData", cv, condition, args)
            dbHelper.close()
        }

        // Delete : 조건에 맞는 행을 삭제한다.
        fun deleteData(context: Context, sdId: Int) {
            val condition = "SD_ID = ?"
            val args = arrayOf("$sdId")

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.delete("SafetyData", condition, args)
            dbHelper.close()
        }
    }
}
