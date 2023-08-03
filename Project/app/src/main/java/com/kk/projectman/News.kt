package com.kk.projectman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView


class News : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private val handler = Handler()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)


        imageView = findViewById(R.id.imgv3)

        // Start periodic updates every 5 seconds (5000 milliseconds)
        startPeriodicUpdates()
    }
}