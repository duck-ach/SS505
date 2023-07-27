package com.seoulWomenTech.ss505


import android.content.ContentValues
import android.content.Context

class AddrDAO {

    companion object {
        // Create : 저장
        fun insertData(context: Context, data: AddrClass){
            // 컬럼이름과 데이터를 설정하는 객체
            val contentValues = ContentValues()
            // 컬럼 이름, 값을 지정한다.
            contentValues.put("G_NM", data.g_nm)
            contentValues.put("D_NM", data.d_nm)

            val dbHelper = DBHelper(context)

            dbHelper.writableDatabase.insert("ADDR", null, contentValues)
            dbHelper.close()
        }

        // Read Condition : 조건에 맞는 행 하나를 가져온다.
        fun selectData(context: Context, addr_id:Int):AddrClass{

            val dbHelper = DBHelper(context)
            val selection = "addr_id = ?"
            val args = arrayOf("$addr_id")
            val cursor = dbHelper.writableDatabase.query("ADDR", null, selection, args, null, null, null)

            cursor.moveToNext()

            val idx1 = cursor.getColumnIndex("ADDR_ID")
            val idx2 = cursor.getColumnIndex("G_NM")
            val idx3 = cursor.getColumnIndex("D_NM")

            val addr_id = cursor.getInt(idx1)
            val g_nm = cursor.getString(idx2)
            val d_nm = cursor.getString(idx3)


            val AddrClass = AddrClass(addr_id,g_nm,d_nm)

            dbHelper.close()
            return AddrClass
        }

        // Read All : 모든 행을 가져온다
        fun selectAllData(context: Context):MutableList<AddrClass>{

            val dbHelper = DBHelper(context)

            val cursor = dbHelper.writableDatabase.query("ADDR", null, null, null, null, null, null)

            val dataList = mutableListOf<AddrClass>()

            while(cursor.moveToNext()){
                val idx1 = cursor.getColumnIndex("addr_id")
                val idx2 = cursor.getColumnIndex("G_NM")
                val idx3 = cursor.getColumnIndex("D_NM")

                val addr_id = cursor.getInt(idx1)
                val g_nm = cursor.getString(idx2)
                val d_nm = cursor.getString(idx3)


                val AddrClass = AddrClass(addr_id,g_nm,d_nm)

                dataList.add(AddrClass)
            }


            dbHelper.close()

            return dataList
        }
        // Update : 조건에 맞는 행의 컬럼의 값을 수정한다.
        fun updateData(context:Context, obj:AddrClass){
            val cv = ContentValues()
            cv.put("g_nm", obj.g_nm)
            cv.put("d_nm", obj.d_nm)
            val condition = "addr_id = ?"
            val args = arrayOf("${obj.idx}")
            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.update("ADDR", cv, condition, args)
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