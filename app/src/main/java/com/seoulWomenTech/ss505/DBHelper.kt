package com.seoulWomenTech.ss505


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


class DBHelper(private val mContext: Context) : SQLiteOpenHelper(mContext, DB_NAME, null, 1) {

    companion object {
        private const val TAG = "DataBaseHelper"
        private var DB_PATH = ""
        private const val DB_NAME = "SS505.db"
    }

    init {
        DB_PATH = "/data/data/" + mContext.packageName + "/databases/"
        dataBaseCheck()
    }

    private fun dataBaseCheck() {
        val dbFile = File(DB_PATH + DB_NAME)
        if (!dbFile.exists()) {
            dbCopy()
            Log.d(TAG, "Database is copied.")
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // 테이블 구조 생성로직
        Log.d(TAG, "onCreate()")
    }

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        //Toast.makeText(mContext,"onOpen()",Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onOpen() : DB Opening!")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // 테이블 삭제하고 onCreate() 다시 로드시킨다.
        Log.d(TAG, "onUpgrade() : DB Schema Modified and Executing onCreate()")
    }

    // db를 assets에서 복사해온다.
    private fun dbCopy() {
        try {
            val folder = File(DB_PATH)
            if (!folder.exists()) {
                folder.mkdir()
            }

            val inputStream: InputStream = mContext.assets.open(DB_NAME)
            val out_filename = DB_PATH + DB_NAME
            val outputStream = FileOutputStream(out_filename)
            val mBuffer = ByteArray(1024)
            var mLength: Int
            while (inputStream.read(mBuffer).also { mLength = it } > 0) {
                outputStream.write(mBuffer, 0, mLength)
            }
            outputStream.flush()
            outputStream.close()
            inputStream.close()

        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("dbCopy", "IOException 발생함")
        }
    }
}
