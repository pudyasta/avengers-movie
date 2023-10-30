package com.example.uts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ReservedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserved)
        supportActionBar?.show()
    }
}