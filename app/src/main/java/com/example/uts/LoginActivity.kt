package com.example.uts

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.uts.databinding.ActivityLoginBinding
import com.example.uts.firestoreapp.FirebaseInstance.Companion.FIREAUTH
import com.example.uts.firestoreapp.FirebaseInstance.Companion.USERS
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences:SharedPreferences = getSharedPreferences("shared",Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("STRING_KEY",null)
        if (savedString==null){
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)
            setupListener()
        }else{
            val intent = Intent(this@LoginActivity,UserPanelActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupListener(){
        with(binding){
            buttonLog.setOnClickListener {
                val email = emailField.text.toString()
                val pass = passwordField.text.toString()

                if (email.isEmpty()){
                    emailField.error = "Email harus diisi"
                    return@setOnClickListener
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(emailField.text).matches()){
                    emailField.error = "Email tidak valid"
                    return@setOnClickListener
                }
                if (passwordField.text.toString().length < 8){
                    passwordField.error = "Password minimal 8 karakter"
                    return@setOnClickListener
                }
                loginAuthFirebase(email,pass)
            }

            logintoreg.setOnClickListener{
                val intent = Intent(this@LoginActivity,RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun loginAuthFirebase(email: String, pass: String) {
        FIREAUTH.signInWithEmailAndPassword(email,pass).addOnSuccessListener{res->
            USERS.whereEqualTo("email",res.user?.email).get().addOnSuccessListener { documents->
                val sharedPreferences:SharedPreferences = getSharedPreferences("shared",Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.apply{
                    putString("STRING_KEY",documents.documents[0].getString("nama"))
                    putString("EMAIL",documents.documents[0].getString("email"))
                    putString("ROLE",documents.documents[0].getString("role"))
                }.apply()
                val intent = Intent(this@LoginActivity,UserPanelActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{ex->
                Toast.makeText(this,ex.message.toString(), Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener{ex->
            Toast.makeText(this,ex.message.toString(), Toast.LENGTH_LONG).show()
        }
    }


}