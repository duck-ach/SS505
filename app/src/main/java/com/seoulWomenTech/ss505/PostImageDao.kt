package com.seoulWomenTech.ss505

import android.content.ContentValues
import android.content.Context
import android.util.Log

class PostImageDao {
        companion object {
            // Create : 저장
            fun insertData(context: Context, data: PostImageClass){
                // 컬럼이름과 데이터를 설정하는 객체
                val contentValues = ContentValues()
                // 컬럼 이름, 값을 지정한다.
                contentValues.put("PI_ID", data.pi_id)
                contentValues.put("POST_ID", data.post_id)
                contentValues.put("PI_UN", data.pi_un)
                contentValues.put("PI_CN", data.pi_cn)
                contentValues.put("PI_URL", data.pi_url)

                val dbHelper = DBHelper(context)

                dbHelper.writableDatabase.insert("PostImage", null, contentValues)
                dbHelper.close()
            }

            // Read Condition : 조건에 맞는 행 모두를 가져온다.
            fun selectData(context: Context, post_id:Int):MutableList<PostImageClass>{

                val dbHelper = DBHelper(context)
                val selection = "post_id = ?"
                val args = arrayOf("$post_id")
                val dataList = mutableListOf<PostImageClass>()

                val cursor = dbHelper.writableDatabase.query("PostImage", null, selection, args, null, null, null)

                while(cursor.moveToNext()){

                val idx1 = cursor.getColumnIndex("PI_ID")
                val idx2 = cursor.getColumnIndex("POST_ID")
                val idx3 = cursor.getColumnIndex("PI_UN")
                val idx4 = cursor.getColumnIndex("PI_CN")
                val idx5 = cursor.getColumnIndex("PI_URL")

                val pi_id = cursor.getInt(idx1)
                val post_id = cursor.getInt(idx2)
                val pi_un = cursor.getString(idx3)
                val pi_cn = cursor.getString(idx4)
                val pi_url = cursor.getString(idx5)


                val PostImageClass =PostImageClass(pi_id,post_id,pi_un,pi_cn,pi_url)
                dataList.add(PostImageClass)
                }
                dbHelper.close()
                return dataList
            }

            // Read All : 모든 행을 가져온다
            fun selectAllData(context: Context):MutableList<PostImageClass>{

                val dbHelper = DBHelper(context)

                val cursor = dbHelper.writableDatabase.query("PostImage", null, null, null, null, null, null)

                val dataList = mutableListOf<PostImageClass>()

                while(cursor.moveToNext()){
                    val idx1 = cursor.getColumnIndex("PI_ID")
                    val idx2 = cursor.getColumnIndex("POST_ID")
                    val idx3 = cursor.getColumnIndex("PI_UN")
                    val idx4 = cursor.getColumnIndex("PI_CN")
                    val idx5 = cursor.getColumnIndex("PI_URL")

                    Log.d("쿼리",idx1.toString())
                    Log.d("쿼리",idx2.toString())

                    val pi_id = cursor.getInt(idx1)
                    val post_id = cursor.getInt(idx2)
                    val pi_un = cursor.getString(idx3)
                    val pi_cn = cursor.getString(idx4)
                    val pi_url = cursor.getString(idx5)


                    val PostImageClass =PostImageClass(pi_id,post_id,pi_un,pi_cn,pi_url)

                    dataList.add(PostImageClass)
                }


                dbHelper.close()

                return dataList
            }
            // Update : 조건에 맞는 행의 컬럼의 값을 수정한다.
            fun updateData(context: Context, obj:PostImageClass){
                val cv = ContentValues()
                cv.put("PI_ID", obj.pi_id)
                cv.put("POST_ID", obj.post_id)
                cv.put("PI_UN", obj.pi_un)
                cv.put("PI_CN", obj.pi_cn)
                cv.put("PI_URL", obj.pi_url)

                val condition = "pi_id = ?"
                val args = arrayOf("${obj.pi_id}")
                val dbHelper = DBHelper(context)
                dbHelper.writableDatabase.update("PostImage", cv, condition, args)
                dbHelper.close()
            }


            // Delete : 조건 맞는 행을 삭제한다.
            fun deleteData(context: Context, pi_id:Int){
                val condition = "pi_id = ?"
                val args = arrayOf("$pi_id")

                val dbHelper = DBHelper(context)
                dbHelper.writableDatabase.delete("PostImage", condition, args)
                dbHelper.close()
            }
        }

}