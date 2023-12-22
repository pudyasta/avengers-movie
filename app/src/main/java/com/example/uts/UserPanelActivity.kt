package com.example.uts

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentPagerAdapter
import com.example.uts.databinding.ActivityUserPanelBinding
import com.example.uts.userpanel.UserPanelTabAdapter

class UserPanelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserPanelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("shared", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("ROLE", null)

        with(binding){
            viewPager.adapter = UserPanelTabAdapter(supportFragmentManager,this@UserPanelActivity)
            tabLayout.setupWithViewPager(viewPager)
            if (savedString == "ADMIN") {
                tabLayout.setupWithViewPager(viewPager)
            } else {
                tabLayout.visibility = View.GONE
            }
        }
    }
}