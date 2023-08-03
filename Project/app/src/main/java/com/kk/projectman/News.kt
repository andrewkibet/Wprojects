package com.kk.projectman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import com.kk.projectman.R
import android.content.Intent
import android.net.Uri



class News : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private val handler = Handler()
    private var currentImageIndex = 0
    private val imageResources = intArrayOf(R.drawable.rrgath, R.drawable.ltm, R.drawable.bg)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        imageView = findViewById(R.id.imgv3)

        // Start periodic updates every 5 seconds (5000 milliseconds)
        startPeriodicUpdates()
    }

    private fun startPeriodicUpdates() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                // Update the ImageView periodically here
                updateImageView()

                // Schedule the next update after 5 seconds
                handler.postDelayed(this, 5000)
            }
        }, 5000) // Initial delay of 5 seconds
    }

    private fun updateImageView() {
        // Set the new image resource
        imageView.setImageResource(imageResources[currentImageIndex])

        // Apply the fade-in animation
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        imageView.startAnimation(fadeInAnimation)

        // Increment the current image index for the next update
        currentImageIndex = (currentImageIndex + 1) % imageResources.size
    }

    private fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

}
