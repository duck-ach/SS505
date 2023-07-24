package com.seoulWomenTech.ss505


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context:Context) : SQLiteOpenHelper(context, "SS505.db", null, 1) {

    override fun onCreate(sqliteDatabase: SQLiteDatabase?) {

        // 유저 테이블 생성
        val sql = """create table UserInfo
            (USER_ID integer primary key autoincrement,
            USER_NAME text,
            USER_EMAIL text,
            USER_PWD text,
            USER_ROLE integer,
            USER_GENDER integer,
            USER_POINT integer,
            USER_PHONE text,
            USER_SNS text,
            USER_ADDR text,
            USER_DATE text,
            USER_IMG text)
        """.trimIndent()

        // 주소 테이블 생성
        val sql2 = """create table ADDR
            (ADDR_ID integer primary key autoincrement,
            G_NM text,
            D_NM text)
        """.trimIndent()

        // 게시글 테이블 생성
        val sql3 = """create table Post
            (POST_ID integer primary key autoincrement,
            WRITER_ID integer,
            POST_TITLE text,
            POST_CONTENT text,
            POST_DATE text,
            foreign key (WRITER_ID) references UserInfo(USER_ID))
        """.trimIndent()

        // 게시글 이미지 테이블 생성
        val sql31 = """create table PostImage
            (PI_ID integer primary key autoincrement,
            POST_ID integer,
            PI_UN text,
            PI_CN text,
            PI_URL text,
            foreign key (POST_ID) references POST(POST_ID))
        """.trimIndent()

        // 댓글 테이블 생성
        val sql32 = """create table Comment
            (C_ID integer primary key autoincrement,
            POST_ID integer,
            C_WRITER_ID integer,
            C_CONTENT text,
            C_DATE text,
            foreign key (POST_ID) references Post(USER_ID),
            foreign key (C_WRITER_ID) references UserInfo(USER_ID)
)
        """.trimIndent()


        // 안전 자료 테이블 생성
        val sql4 = """create table SafetyData
            (SD_ID integer primary key autoincrement,
            ADMIN_ID integer,
            SD_TITLE text,
            SD_CONTENT text,
            SD_DATE text,
            foreign key (ADMIN_ID) references UserInfo(USER_ID))
        """.trimIndent()

        // 안전 자료 이미지 테이블 생성
        val sql41 = """create table SafetyImage
            (SDI_ID integer primary key autoincrement,
            SD_ID integer,
            SDI_URL text,
            SDI_UN text,
            SDI_CN text,
            foreign key (SD_ID) references SafetyData(SD_ID))
        """.trimIndent()

        // 챌린지 테이블 생성
        val sql5 = """create table Challenge
            (CLG_ID integer primary key autoincrement,
            ADMIN_ID integer,
            ADDR_ID integer,
            CLG_NM text,
            CLG_CONTENT text,
            CLG_POST_DATE text,
            CLG_PROG_DATE text,
            CLG_PROG_TIME text,
            CLG_MAX_USER integer,
            IS_CLG_ACTIVE integer,
            CLG_REWORD integer,
            CLG_IMG text,
            foreign key (ADMIN_ID) references UserInfo(USER_ID),
            foreign key (ADDR_ID) references ADDR(ADDR_ID))
        """.trimIndent()

        // 챌린지 참여 테이블 생성
        val sql6 = """create table Participants
            (CLG_ID integer,
            USER_ID integer,
            primary key (CLG_ID,USER_ID),
            foreign key (CLG_ID) references Challenge(CLG_ID),
            foreign key (USER_ID) references UserInfo(USER_ID))
        """.trimIndent()

        // 챌린지 인증샷 테이블 생성
        val sql61 = """create table CPI
            (CPI_ID integer primary key autoincrement,
            CLG_ID integer,
            USER_ID integer,
            CPI_URL text,
            CPI_UN text,
            CPI_CN text,
            foreign key (CLG_ID) references Challenge(CLG_ID),
            foreign key (USER_ID) references UserInfo(USER_ID))
        """.trimIndent()


        sqliteDatabase?.execSQL(sql)
        sqliteDatabase?.execSQL(sql2)
        sqliteDatabase?.execSQL(sql3)
        sqliteDatabase?.execSQL(sql31)
        sqliteDatabase?.execSQL(sql32)
        sqliteDatabase?.execSQL(sql4)
        sqliteDatabase?.execSQL(sql41)
        sqliteDatabase?.execSQL(sql5)
        sqliteDatabase?.execSQL(sql6)
        sqliteDatabase?.execSQL(sql61)
    }


    override fun onUpgrade(sqliteDatabase: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}