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
        // Perform any necessary actions to fetch the new image resource
        // For example, you can use an image loading library like Glide or Picasso

        // In this example, let's assume we have an array of image resources and a counter to keep track of the current image index
        val imageResources = arrayOf(R.drawable.bg12, R.drawable.bg3, R.drawable.bg)
        var currentImageIndex = 0

        // Set the new image resource
        imageView.setImageResource(imageResources[currentImageIndex])

        // Apply the fade-in animation
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        imageView.startAnimation(fadeInAnimation)

        // Increment the current image index for the next update
        currentImageIndex = (currentImageIndex + 1) % imageResources.size
    }

}