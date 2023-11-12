package com.example.uts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uts.api.network.ApiClient
import com.example.uts.databinding.ActivityHeroBinding
import com.example.uts.recyclers.Hero.Hero
import com.example.uts.recyclers.Hero.HeroAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HeroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHeroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val client=ApiClient.getInstance()
        val res = client.getAllHeros()
        val heroList = ArrayList<Hero>()

        res.enqueue(object :Callback<com.example.uts.api.model.Hero>{
            override fun onResponse(
                call: Call<com.example.uts.api.model.Hero>,
                response: Response<com.example.uts.api.model.Hero>
            ) {
                for (i in response.body()!!.result!!){
                    heroList.add(Hero(i?.image,i?.id,i?.title))
                }
                val adapter = HeroAdapter(heroList)

                with(binding) {
                    recycler.adapter = adapter
                    recycler.layoutManager = LinearLayoutManager(this@HeroActivity)
                }
            }

            override fun onFailure(call: Call<com.example.uts.api.model.Hero>, t: Throwable) {
                Toast.makeText(this@HeroActivity, "Koneksi error",
                    Toast.LENGTH_LONG).show()

            }

        })
    }
}