package com.seoulWomenTech.ss505

import android.content.ContentValues
import android.content.Context

class UserInfoDAO {

    companion object {
        // Create : 저장
        fun insertData(context: Context, data: UserInfo) {
            // 컬럼 이름과 데이터를 설정하는 객체
            val contentValues = ContentValues()
            // 컬럼 이름, 값을 지정한다.
            contentValues.put("USER_NAME", data.name)
            contentValues.put("USER_EMAIL", data.email)
            contentValues.put("USER_PWD", data.password)
            contentValues.put("USER_ROLE", data.role)
            contentValues.put("USER_GENDER", data.gender)
            contentValues.put("USER_POINT", data.point)
            contentValues.put("USER_PHONE", data.phone)
            contentValues.put("USER_SNS", data.sns)
            contentValues.put("USER_ADDR", data.address)
            contentValues.put("USER_DATE", data.date)
            contentValues.put("USER_IMG", data.image)
            contentValues.put("ADMIN_OFFICE", data.admin_office)
            contentValues.put("admin_rank", data.admin_rank)

            val dbHelper = DBHelper(context)

            dbHelper.writableDatabase.insert("UserInfo", null, contentValues)
            dbHelper.close()
        }

        // Read Condition : 조건에 맞는 행 하나를 가져온다.
        fun selectData(context: Context, userId: Int): UserInfo? {
            val dbHelper = DBHelper(context)
            val selection = "USER_ID = ?"
            val args = arrayOf("$userId")
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
                val idx13 = cursor.getColumnIndex("ADMIN_OFFICE")
                val idx14 = cursor.getColumnIndex("ADMIN_RANK")

                val user_id = cursor.getInt(idx1)
                val name = cursor.getString(idx2)
                val email = cursor.getString(idx3)
                val password = cursor.getString(idx4)
                val role = cursor.getInt(idx5)
                val gender = cursor.getInt(idx6)
                val point = cursor.getInt(idx7)
                val phone = cursor.getString(idx8)
                val sns = cursor.getString(idx9)
                val address = cursor.getString(idx10)
                val date = cursor.getString(idx11)
                val image = cursor.getString(idx12)
                val admin_office = cursor.getString(idx13)
                val admin_rank = cursor.getString(idx14)

                val userInfo = UserInfo(user_id, name, email, password, role, gender, point, phone, sns, address, date, image, admin_office, admin_rank)



            cursor.close()
            dbHelper.close()

            return userInfo
        }

        // Read All : 모든 행을 가져온다
        fun selectAllData(context: Context): MutableList<UserInfo> {
            val dbHelper = DBHelper(context)

            val cursor = dbHelper.writableDatabase.query("UserInfo", null, null, null, null, null, null)

            val dataList = mutableListOf<UserInfo>()

            while (cursor.moveToNext()) {
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
                val idx13 = cursor.getColumnIndex("ADMIN_OFFICE")
                val idx14 = cursor.getColumnIndex("ADMIN_RANK")

                val user_id = cursor.getInt(idx1)
                val name = cursor.getString(idx2)
                val email = cursor.getString(idx3)
                val password = cursor.getString(idx4)
                val role = cursor.getInt(idx5)
                val gender = cursor.getInt(idx6)
                val point = cursor.getInt(idx7)
                val phone = cursor.getString(idx8)
                val sns = cursor.getString(idx9)
                val address = cursor.getString(idx10)
                val date = cursor.getString(idx11)
                val image = cursor.getString(idx12)
                val admin_office = cursor.getString(idx13)
                val admin_rank = cursor.getString(idx14)

                val userInfo = UserInfo(user_id, name, email, password, role, gender, point, phone, sns, address, date, image, admin_office, admin_rank)

                dataList.add(userInfo)
            }

            cursor.close()
            dbHelper.close()

            return dataList
        }

        // Update : 조건에 맞는 행의 컬럼의 값을 수정한다.
        fun updateData(context: Context, userInfo: UserInfo) {
            val cv = ContentValues()
            cv.put("USER_NAME", userInfo.name)
            cv.put("USER_EMAIL", userInfo.email)
            cv.put("USER_PWD", userInfo.password)
            cv.put("USER_ROLE", userInfo.role)
            cv.put("USER_GENDER", userInfo.gender)
            cv.put("USER_POINT", userInfo.point)
            cv.put("USER_PHONE", userInfo.phone)
            cv.put("USER_SNS", userInfo.sns)
            cv.put("USER_ADDR", userInfo.address)
            cv.put("USER_DATE", userInfo.date)
            cv.put("USER_IMG", userInfo.image)
            cv.put("ADMIN_OFFICE", userInfo.admin_office)
            cv.put("ADMIN_RANK", userInfo.admin_rank)

            val condition = "USER_ID = ?"
            val args = arrayOf("${userInfo.idx}")

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.update("UserInfo", cv, condition, args)
            dbHelper.close()
        }

        // Delete : 조건에 맞는 행을 삭제한다.
        fun deleteData(context: Context, userId: Int) {
            val condition = "USER_ID = ?"
            val args = arrayOf("$userId")

            val dbHelper = DBHelper(context)
            dbHelper.writableDatabase.delete("UserInfo", condition, args)
            dbHelper.close()
        }
    }
}
