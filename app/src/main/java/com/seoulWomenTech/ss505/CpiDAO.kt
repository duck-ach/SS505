package com.seoulWomenTech.ss505


import android.content.ContentValues
import android.content.Context
import android.util.Log

class CpiDAO {

    companion object {
        // Create : 저장
        fun insertData(context: Context, data: CPIClass) {
            val contentValues = ContentValues()
            contentValues.put("CLG_ID", data.clg_id)
            contentValues.put("USER_ID", data.user_id)
            contentValues.put("CPI_URL", data.url)

            val dbHelper = DBHelper(context)
            val db = dbHelper.writableDatabase

            db.insert("CPI", null, contentValues)
            db.close()
        }

        // Read Condition : 조건에 맞는 행 하나를 가져온다.
        fun selectData(context: Context, cpi_id: Int): CPIClass? {
            val dbHelper = DBHelper(context)
            val selection = "CPI_ID = ?"
            val args = arrayOf("$cpi_id")
            val db = dbHelper.writableDatabase

            val cursor = db.query("CPI", null, selection, args, null, null, null)


            cursor.moveToNext()
            val idx1 = cursor.getColumnIndex("CPI_ID")
            val idx2 = cursor.getColumnIndex("CLG_ID")
            val idx3 = cursor.getColumnIndex("USER_ID")
            val idx4 = cursor.getColumnIndex("CPI_URL")


            val cpi_id = cursor.getInt(idx1)
            val clg_id = cursor.getInt(idx2)
            val user_id = cursor.getInt(idx3)
            val cpi_url = cursor.getString(idx4)


            var cpiClass = CPIClass(
                cpi_id,
                clg_id,
                user_id,
                cpi_url,

                )
            

            cursor.close()
            db.close()
            return cpiClass
        }


        // Read by CLG_ID : CLG_ID로 데이터를 검색하는 메서드
        fun selectDataByClgId(context: Context, clg_id: Int): MutableList<CPIClass> {
            val dbHelper = DBHelper(context)
            val selection = "CLG_ID = ?"
            val args = arrayOf("$clg_id")
            val db = dbHelper.writableDatabase

            val cursor = db.query("CPI", null, selection, args, null, null, null)

            val dataList = mutableListOf<CPIClass>()

            while (cursor.moveToNext()) {
                val idx1 = cursor.getColumnIndex("CPI_ID")
                val idx2 = cursor.getColumnIndex("CLG_ID")
                val idx3 = cursor.getColumnIndex("USER_ID")
                val idx4 = cursor.getColumnIndex("CPI_URL")


                val cpi_id = cursor.getInt(idx1)
                val clg_id = cursor.getInt(idx2)
                val user_id = cursor.getInt(idx3)
                val cpi_url = cursor.getString(idx4)


                val cpiClass = CPIClass(
                    cpi_id,
                    clg_id,
                    user_id,
                    cpi_url,

                    )

                dataList.add(cpiClass)
            }

            cursor.close()
            db.close()

            return dataList
        }

        // Read by USER_ID : USER_ID로 데이터를 검색하는 메서드
        fun selectDataByUserId(context: Context, user_id: Int): MutableList<CPIClass> {
            val dbHelper = DBHelper(context)
            val selection = "USER_ID = ?"
            val args = arrayOf("$user_id")
            val db = dbHelper.writableDatabase

            val cursor = db.query("CPI", null, selection, args, null, null, null)
            val dataList = mutableListOf<CPIClass>()

            while (cursor.moveToNext()) {
                val idx1 = cursor.getColumnIndex("CPI_ID")
                val idx2 = cursor.getColumnIndex("CLG_ID")
                val idx3 = cursor.getColumnIndex("USER_ID")
                val idx4 = cursor.getColumnIndex("CPI_URL")


                val cpi_id = cursor.getInt(idx1)
                val clg_id = cursor.getInt(idx2)
                val user_id = cursor.getInt(idx3)
                val cpi_url = cursor.getString(idx4)


                val cpiClass = CPIClass(
                    cpi_id,
                    clg_id,
                    user_id,
                    cpi_url,

                    )

                Log.d("인증샷 쿼리", cpiClass.toString())

                dataList.add(cpiClass)
            }

            cursor.close()
            db.close()

            Log.d("인증샷 쿼리2", dataList.toString())
            return dataList
        }


        // Read All : 모든 행을 가져온다
        fun selectAllData(context: Context): MutableList<CPIClass> {
            val dbHelper = DBHelper(context)
            val db = dbHelper.writableDatabase

            val cursor = db.query("CPI", null, null, null, null, null, null)

            val dataList = mutableListOf<CPIClass>()

            while (cursor.moveToNext()) {
                val idx1 = cursor.getColumnIndex("CPI_ID")
                val idx2 = cursor.getColumnIndex("CLG_ID")
                val idx3 = cursor.getColumnIndex("USER_ID")
                val idx4 = cursor.getColumnIndex("CPI_URL")

                val cpi_id = cursor.getInt(idx1)
                val clg_id = cursor.getInt(idx2)
                val user_id = cursor.getInt(idx3)
                val cpi_url = cursor.getString(idx4)


                val cpiClass = CPIClass(
                    cpi_id,
                    clg_id,
                    user_id,
                    cpi_url,
                )

                dataList.add(cpiClass)
            }

            cursor.close()
            db.close()

            return dataList
        }

        // Update : 조건에 맞는 행의 컬럼의 값을 수정한다.
        fun updateData(context: Context, obj: CPIClass) {
            val cv = ContentValues()
            cv.put("CLG_ID", obj.clg_id)
            cv.put("USER_ID", obj.user_id)
            cv.put("CPI_URL", obj.url)

            val condition = "CPI_ID = ?"
            val args = arrayOf(obj.idx.toString())

            val dbHelper = DBHelper(context)
            val db = dbHelper.writableDatabase

            db.update("CPI", cv, condition, args)

            db.close()
        }

        // Delete : 조건에 맞는 행을 삭제한다.
        fun deleteData(context: Context, cpi_id: Int) {
            val condition = "CPI_ID = ?"
            val args = arrayOf(cpi_id.toString())

            val dbHelper = DBHelper(context)
            val db = dbHelper.writableDatabase

            db.delete("CPI", condition, args)

            db.close()
        }
    }
}
