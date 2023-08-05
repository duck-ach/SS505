package com.seoulWomenTech.ss505

import android.content.ContentValues
import android.content.Context

class SafetyImageDAO {

    companion object {
        // Create : 저장
        fun insertData(context: Context, data: SafetyImage) {
            val contentValues = ContentValues()
            contentValues.put("SD_ID", data.sdId)
            contentValues.put("SDI_URL", data.url)

            val dbHelper = DBHelper(context)

            dbHelper.writableDatabase.insert("SafetyImage", null, contentValues)
            dbHelper.close()
        }

        // Read Condition : 조건에 맞는 행 하나를 가져온다.
        fun selectData(context: Context, sdiId: Int): SafetyImage? {
            val dbHelper = DBHelper(context)
            val selection = "SDI_ID = ?"
            val args = arrayOf("$sdiId")
            val cursor = dbHelper.writableDatabase.query(
                "SafetyImage",
                null,
                selection,
                args,
                null,
                null,
                null
            )


            cursor.moveToNext()
            val idx1 = cursor.getColumnIndex("SDI_ID")
            val idx2 = cursor.getColumnIndex("SD_ID")
            val idx3 = cursor.getColumnIndex("SDI_URL")

            val sdiId = cursor.getInt(idx1)
            val sdId = cursor.getInt(idx2)
            val sdiUrl = cursor.getString(idx3)

            val safetyImage = SafetyImage(sdiId, sdId, sdiUrl)


            cursor.close()
            dbHelper.close()

            return safetyImage
        }

        // Read All : 모든 행을 가져온다
        fun selectAllData(context: Context): MutableList<SafetyImage> {
            val dbHelper = DBHelper(context)
            val cursor =
                dbHelper.writableDatabase.query("SafetyImage", null, null, null, null, null, null)

            val dataList = mutableListOf<SafetyImage>()

            while (cursor.moveToNext()) {
                val idx1 = cursor.getColumnIndex("SDI_ID")
                val idx2 = cursor.getColumnIndex("SD_ID")
                val idx3 = cursor.getColumnIndex("SDI_URL")

                val sdiId = cursor.getInt(idx1)
                val sdId = cursor.getInt(idx2)
                val sdiUrl = cursor.getString(idx3)

                val safetyImage = SafetyImage(sdiId, sdId, sdiUrl)

                dataList.add(safetyImage)
            }

            cursor.close()
            dbHelper.close()

            return dataList
        }

        // Update : 조건에 맞는 행의 컬럼의 값을 수정한다.
        fun updateData(context: Context, safetyImage: SafetyImage) {
            val cv = ContentValues()
            cv.put("SD_ID", safetyImage.sdId)
            cv.put("SDI_URL", safetyImage.url)

            val condition = "SDI_ID = ?"
            val args = arrayOf("${safetyImage.idx}")

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.update("SafetyImage", cv, condition, args)
            dbHelper.close()
        }

        // Delete : 조건에 맞는 행을 삭제한다.
        fun deleteData(context: Context, sdiId: Int) {
            val condition = "SDI_ID = ?"
            val args = arrayOf("$sdiId")

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.delete("SafetyImage", condition, args)
            dbHelper.close()
        }
    }
}
