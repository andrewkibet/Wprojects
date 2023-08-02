package com.kk.projectman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class HomeActivity : AppCompatActivity() {

    private lateinit var txvadmin: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        txvadmin = findViewById(R.id.admin)
        txvadmin.setOnClickListener { val intent =Intent(this,Tendepreneurs:: class.java)
        startActivity(intent)}
    }
}