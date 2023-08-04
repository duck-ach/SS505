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
            contentValues.put("D_NM", data.g_nm)
            contentValues.put("G_NM", data.d_nm)

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
            val idx2 = cursor.getColumnIndex("D_NM")
            val idx3 = cursor.getColumnIndex("G_NM")

            val addr_id = cursor.getInt(idx1)
            val d_nm = cursor.getString(idx2)
            val g_nm = cursor.getString(idx3)


            val addrClass = AddrClass(addr_id,d_nm,g_nm)

            dbHelper.close()
            return addrClass
        }

        // Read By G_NM
        fun selectByGNM(context: Context, g_nm:String):MutableList<AddrClass>{

            val dbHelper = DBHelper(context)
            val selection = "g_nm = ?"
            val args = arrayOf("$g_nm")
            val cursor = dbHelper.writableDatabase.query("ADDR", null, selection, args, null, null, null)

            val dataList = mutableListOf<AddrClass>()
            while (cursor.moveToNext()){
                val idx1 = cursor.getColumnIndex("ADDR_ID")
                val idx2 = cursor.getColumnIndex("D_NM")
                val idx3 = cursor.getColumnIndex("G_NM")

                val addr_id = cursor.getInt(idx1)
                val d_nm = cursor.getString(idx2)
                val g_nm = cursor.getString(idx3)


                val addrClass = AddrClass(addr_id,d_nm,g_nm)
                dataList.add(addrClass)
            }



            dbHelper.close()
            return dataList
        }

        // Read By D_NM
        fun selectByDNM(context: Context, d_nm:String):MutableList<AddrClass>{

            val dbHelper = DBHelper(context)
            val selection = "d_nm = ?"
            val args = arrayOf("$d_nm")
            val cursor = dbHelper.writableDatabase.query("ADDR", null, selection, args, null, null, null)

            val dataList = mutableListOf<AddrClass>()
            while (cursor.moveToNext()){
                val idx1 = cursor.getColumnIndex("ADDR_ID")
                val idx2 = cursor.getColumnIndex("D_NM")
                val idx3 = cursor.getColumnIndex("G_NM")

                val addr_id = cursor.getInt(idx1)
                val d_nm = cursor.getString(idx2)
                val g_nm = cursor.getString(idx3)


                val addrClass = AddrClass(addr_id,d_nm,g_nm)
                dataList.add(addrClass)
            }

            dbHelper.close()
            return dataList
        }

        // Read All : 모든 행을 가져온다
        fun selectAllData(context: Context):MutableList<AddrClass>{

            val dbHelper = DBHelper(context)

            val cursor = dbHelper.writableDatabase.query("ADDR", null, null, null, null, null, null)

            val dataList = mutableListOf<AddrClass>()

            while(cursor.moveToNext()){
                val idx1 = cursor.getColumnIndex("ADDR_ID")
                val idx2 = cursor.getColumnIndex("D_NM")
                val idx3 = cursor.getColumnIndex("G_NM")

                val addr_id = cursor.getInt(idx1)
                val d_nm = cursor.getString(idx2)
                val g_nm = cursor.getString(idx3)


                val addrClass = AddrClass(addr_id,d_nm,g_nm)

                dataList.add(addrClass)
            }


            dbHelper.close()

            return dataList
        }
        // Update : 조건에 맞는 행의 컬럼의 값을 수정한다.
        fun updateData(context:Context, obj:AddrClass){
            val cv = ContentValues()
            cv.put("d_nm", obj.g_nm)
            cv.put("g_nm", obj.d_nm)
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