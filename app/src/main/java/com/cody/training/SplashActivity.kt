package com.cody.training

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.SplashTheme)

        val intent = Intent(this, TodoListActivity::class.java)
        startActivity(intent)
        finish()
    }
}