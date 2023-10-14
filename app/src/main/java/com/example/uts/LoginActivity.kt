package com.example.uts

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.uts.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences:SharedPreferences = getSharedPreferences("shared",Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("STRING_KEY",null)
        if (savedString==null){
            val binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            with(binding){

                buttonLog.setOnClickListener {
                    val username = usernameField.text.toString()
                    val pass = passwordField.text.toString()
                    Log.d("MyTag", username)
                    if (username.equals("pudyasta") and pass.equals("12345678")){
                        val sharedPreferences:SharedPreferences = getSharedPreferences("shared",Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.apply{
                            putString("STRING_KEY","Pudyasta")
                        }.apply()
                        val intent = Intent(this@LoginActivity,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@LoginActivity, "Username atau Password Salah",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }else{
            val intent = Intent(this@LoginActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }


}