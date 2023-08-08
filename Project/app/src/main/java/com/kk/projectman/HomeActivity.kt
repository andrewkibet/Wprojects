package com.kk.projectman

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class HomeActivity : AppCompatActivity() {

    private lateinit var txvadmin: TextView
    private lateinit var button: Button
    private lateinit var textview: TextView
    private lateinit var news: TextView
    private lateinit var stakeholders: TextView
    private  lateinit var chat:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)



        button = findViewById(R.id.logout)
        button.setOnClickListener { val intent = Intent (this,LoginActivity::class.java)
            startActivity(intent) }

        news =  findViewById(R.id.newstv)
        news.setOnClickListener { val intent = Intent (this,News::class.java)
        startActivity(intent)}

        txvadmin = findViewById(R.id.amdtxv)
        txvadmin.setOnClickListener {
            val intent = Intent(this@HomeActivity.applicationContext, AdminPage::class.java)
            startActivity(intent)
        }
        stakeholders =  findViewById(R.id.stkholders)
        stakeholders.setOnClickListener {
            val intent = Intent(this@HomeActivity.applicationContext, Tendepreneurs::class.java)
            startActivity(intent)
        }
        textview = findViewById(R.id.prtext)
        textview.setOnClickListener {
            val intent = Intent(this@HomeActivity.applicationContext, ProjectList::class.java)
            startActivity(intent)
        }

    }
}