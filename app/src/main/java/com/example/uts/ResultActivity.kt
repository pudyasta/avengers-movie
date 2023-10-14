package com.example.uts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uts.databinding.ActivityPurchaseBinding
import com.example.uts.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val poster = intent.getIntExtra("poster",0)

        with(binding){
            poster1.setImageResource(poster)
            title1.text = intent.getStringExtra("title")
            bioskop1.text = intent.getStringExtra("bioskop")
            date1.text = intent.getStringExtra("date")
            harga.text= intent.getStringExtra("seat")
            seatType1.text= intent.getStringExtra("seat")
            harga1.text= intent.getStringExtra("price")
            actual1.text=intent.getStringExtra("price")
            paymnet1.text =
                """${intent.getStringExtra("payment")} (${intent.getStringExtra("bank")})"""
        }
    }
}