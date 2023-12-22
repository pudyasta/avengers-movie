package com.example.uts

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uts.databinding.ActivityPurchaseBinding
import com.example.uts.databinding.ActivityResultBinding
import com.squareup.picasso.Picasso

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val poster = Uri.parse(intent.getStringExtra("poster"))

        with(binding){
            Picasso.get().load(poster).into(poster1)
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