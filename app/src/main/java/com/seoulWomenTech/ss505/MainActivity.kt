package com.seoulWomenTech.ss505

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.seoulWomenTech.ss505.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // Addr insert



    }
}

// 정보를 담을 객체
data class AddrClass(var idx :Int, var g_nm:String, var d_nm:String)