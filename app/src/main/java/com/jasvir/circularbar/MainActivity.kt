package com.jasvir.circularbar

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.circularbar.R
import com.jasvir.seekbar.SemiCircleArcProgressBar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // uncomment this for xml
        //setDataForXMl()
        // This sets the Compose UI content
        setDataForCompose()
    }

    fun setDataForCompose(){
        setContent {
            CircularProgressScreen()
        }
    }

    fun setDataForXMl(){
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

    @Composable
    fun CircularProgressScreen() {
        // Material 3 Inspired Palette
        val PrimaryColor = Color(0xFFFBEAEB)
        val TrackColor = Color(0xFF2F3C7E)
        val stages = remember { arrayListOf(0,50,100,150) }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AndroidView(
                modifier = Modifier
                    .size(300.dp) // Maintain aspect ratio
                    .padding(16.dp),
                factory = { context ->
                    // 1. Initialize the view
                    SemiCircleArcProgressBar(context).apply {
                        setData(stages)
                        setProgressBarColor(PrimaryColor.toArgb())
                        setProgressPlaceHolderColor(TrackColor.toArgb())
                        setProgressBarWidth(10.dpToPx())
                        setProgressPlaceHolderWidth(14.dpToPx())
                    }
                },
                update = { view ->
                    // 2. Perform updates here (this runs on recomposition)
                    view.setPercentWithAnimation(125)
                }
            )
        }
    }
}

fun Int.dpToPx(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()
}