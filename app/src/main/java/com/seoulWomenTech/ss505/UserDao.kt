package com.seoulWomenTech.ss505

import android.content.ContentValues
import android.content.Context
import android.util.Log

class UserDao {
        companion object {
            // Create : 저장
            fun insertData(context: Context, data:UserClass){
                // 컬럼이름과 데이터를 설정하는 객체
                val contentValues = ContentValues()
                // 컬럼 이름, 값을 지정한다.
                contentValues.put("USER_ID", data.user_id)
                contentValues.put("USER_NAME", data.name)
                contentValues.put("USER_EMAIL", data.email)
                contentValues.put("USER_PWD", data.pwd)
                contentValues.put("USER_ROLE", data.role)
                contentValues.put("USER_GENDER", data.gender)
                contentValues.put("USER_POINT", data.point)
                contentValues.put("USER_PHONE", data.phone)
                contentValues.put("USER_SNS", data.sns)
                contentValues.put("USER_ADDR", data.addr)
                contentValues.put("USER_DATE", data.date)
                contentValues.put("USER_IMG", data.user_image)

                val dbHelper = DBHelper(context)

                dbHelper.writableDatabase.insert("UserInfo", null, contentValues)
                dbHelper.close()
            }

            // Read Condition : 조건에 맞는 행 하나를 가져온다.
            fun selectData(context: Context, challenge_id:Int):UserClass{

                val dbHelper = DBHelper(context)
                val selection = "USER_ID = ?"
                val args = arrayOf("$challenge_id")
                val cursor = dbHelper.writableDatabase.query("UserInfo", null, selection, args, null, null, null)

                cursor.moveToNext()

                val idx1 = cursor.getColumnIndex("USER_ID")
                val idx2 = cursor.getColumnIndex("USER_NAME")
                val idx3 = cursor.getColumnIndex("USER_EMAIL")
                val idx4 = cursor.getColumnIndex("USER_PWD")
                val idx5 = cursor.getColumnIndex("USER_ROLE")
                val idx6 = cursor.getColumnIndex("USER_GENDER")
                val idx7 = cursor.getColumnIndex("USER_POINT")
                val idx8 = cursor.getColumnIndex("USER_PHONE")
                val idx9 = cursor.getColumnIndex("USER_SNS")
                val idx10 = cursor.getColumnIndex("USER_ADDR")
                val idx11 = cursor.getColumnIndex("USER_DATE")
                val idx12 = cursor.getColumnIndex("USER_IMG")

                val user_id = cursor.getInt(idx1)
                val name = cursor.getString(idx2)
                val email = cursor.getString(idx3)
                val pwd = cursor.getString(idx4)
                val role = cursor.getInt(idx5)
                val gender = cursor.getInt(idx6)
                val point = cursor.getInt(idx7)
                val phone = cursor.getString(idx8)
                val sns = cursor.getString(idx9)
                val addr = cursor.getString(idx10)
                val date = cursor.getString(idx11)
                val user_image = cursor.getString(idx12)


                val userClass =UserClass(user_id,name,email,pwd,role,gender,point,phone,sns,addr,date,user_image)

                dbHelper.close()
                return userClass
            }

            // Read All : 모든 행을 가져온다
            fun selectAllData(context: Context):MutableList<UserClass>{

                val dbHelper = DBHelper(context)

                val cursor = dbHelper.writableDatabase.query("UserInfo", null, null, null, null, null, null)

                val dataList = mutableListOf<UserClass>()

                while(cursor.moveToNext()){
                    val idx1 = cursor.getColumnIndex("USER_ID")
                    val idx2 = cursor.getColumnIndex("USER_NAME")
                    val idx3 = cursor.getColumnIndex("USER_EMAIL")
                    val idx4 = cursor.getColumnIndex("USER_PWD")
                    val idx5 = cursor.getColumnIndex("USER_ROLE")
                    val idx6 = cursor.getColumnIndex("USER_GENDER")
                    val idx7 = cursor.getColumnIndex("USER_POINT")
                    val idx8 = cursor.getColumnIndex("USER_PHONE")
                    val idx9 = cursor.getColumnIndex("USER_SNS")
                    val idx10 = cursor.getColumnIndex("USER_ADDR")
                    val idx11 = cursor.getColumnIndex("USER_DATE")
                    val idx12 = cursor.getColumnIndex("USER_IMG")

                    Log.d("쿼리",idx1.toString())
                    Log.d("쿼리",idx2.toString())

                    val user_id = cursor.getInt(idx1)
                    val name = cursor.getString(idx2)
                    val email = cursor.getString(idx3)
                    val pwd = cursor.getString(idx4)
                    val role = cursor.getInt(idx5)
                    val gender = cursor.getInt(idx6)
                    val point = cursor.getInt(idx7)
                    val phone = cursor.getString(idx8)
                    val sns = cursor.getString(idx9)
                    val addr = cursor.getString(idx10)
                    val date = cursor.getString(idx11)
                    val user_image = cursor.getString(idx12)


                    val userClass =UserClass(user_id,name,email,pwd,role,gender,point,phone,sns,addr,date,user_image)

                    dataList.add(userClass)
                }


                dbHelper.close()

                return dataList
            }
            // Update : 조건에 맞는 행의 컬럼의 값을 수정한다.
            fun updateData(context: Context, obj:UserClass){
                val cv = ContentValues()

                cv.put("USER_ID", obj.user_id)
                cv.put("USER_NAME", obj.name)
                cv.put("USER_EMAIL", obj.email)
                cv.put("USER_PWD", obj.pwd)
                cv.put("USER_ROLE", obj.role)
                cv.put("USER_GENDER", obj.gender)
                cv.put("USER_POINT", obj.point)
                cv.put("USER_PHONE", obj.phone)
                cv.put("USER_SNS", obj.sns)
                cv.put("USER_ADDR", obj.addr)
                cv.put("USER_DATE", obj.date)
                cv.put("USER_IMG", obj.user_image)

                val condition = "user_id = ?"
                val args = arrayOf("${obj.user_id}")
                val dbHelper = DBHelper(context)
                dbHelper.writableDatabase.update("UserInfo", cv, condition, args)
                dbHelper.close()
            }


            // Delete : 조건 맞는 행을 삭제한다.
            fun deleteData(context: Context, user_id:Int){
                val condition = "user_id = ?"
                val args = arrayOf("$user_id")

                val dbHelper = DBHelper(context)
                dbHelper.writableDatabase.delete("UserInfo", condition, args)
                dbHelper.close()
            }
        }
}