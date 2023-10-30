package com.example.uts

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("shared", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("STRING_KEY", null)

        if (savedString == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            setCurrentFragment(HomeFragment())

            with(binding) {
                bottomNav.background = null
                bottomNav.setOnItemSelectedListener {
                    when (it.itemId) {
                        R.id.home -> setCurrentFragment(HomeFragment())
                        R.id.profile -> setCurrentFragment(ProfileFragment())
                    }
                    true
                }
                floatBar.setOnClickListener{
                    val intent = Intent(this@MainActivity,ReservedActivity::class.java)
                    startActivity(intent)
                }
            }
        }

    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}