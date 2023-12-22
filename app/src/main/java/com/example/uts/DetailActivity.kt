package com.example.uts

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.uts.databinding.ActivityDetailBinding
import com.example.uts.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val title1 = intent.getStringExtra("title").toString()
        val poster1 = Uri.parse(intent.getStringExtra("poster"))
        with(binding){
            title.text = title1
            genre.text = intent.getStringExtra("genre")
            desc.text = intent.getStringExtra("desc")
            age.text = intent.getStringExtra("age")
            year.text = intent.getStringExtra("year")
            rating.text = intent.getStringExtra("rating")
            duration.text = intent.getStringExtra("duration")
            Picasso.get().load(poster1).into(poster)

            buttonPurchase.setOnClickListener {e->
                val intent = Intent(this@DetailActivity,PurchaseActivity::class.java)
                intent.putExtra("title",title1)
                intent.putExtra("poster",poster1.toString())
                startActivity(intent)
            }
        }
    }
}