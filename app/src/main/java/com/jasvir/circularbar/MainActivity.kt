package com.jasvir.circularbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jasvir.seekbar.SemiCircleArcProgressBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cvBar = findViewById<SemiCircleArcProgressBar>(R.id.cvBar)
        val list = ArrayList<Int>()
        list.add(0)
        list.add(50)
        list.add(100)
        list.add(150)
        cvBar.setData(list)
        cvBar.setPercentWithAnimation(125)
    }
}
