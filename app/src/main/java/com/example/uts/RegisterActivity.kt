package com.example.uts

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.uts.databinding.ActivityLoginBinding
import com.example.uts.databinding.ActivityRegisterBinding
import com.example.uts.firestoreapp.FirebaseInstance.Companion.FIREAUTH
import com.example.uts.firestoreapp.FirebaseInstance.Companion.USERS
import com.example.uts.model.Roles
import com.example.uts.model.User

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences: SharedPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString("STRING_KEY",null)
        if (savedString==null){
            binding = ActivityRegisterBinding.inflate(layoutInflater)
            setContentView(binding.root)
            setupListener()
        }else{
            val intent = Intent(this@RegisterActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupListener() {
        with(binding){
            buttonLog.setOnClickListener {
                if (usernameField.text.toString().isEmpty()){
                    usernameField.error = "Nama harus diisi"
                    return@setOnClickListener
                }
                if (emailField.text.toString().isEmpty()){
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

                if (passwordField.text.toString() != konfirmasiField.text.toString()){
                    konfirmasiField.error = "Password tidak cocok"
                    return@setOnClickListener
                }

                registerFireBase(usernameField.text.toString(),emailField.text.toString(),passwordField.text.toString())
            }

            logintoreg.setOnClickListener(){
                val intent = Intent(this@RegisterActivity,LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun registerFireBase(name: String, email: String, password: String) {
        FIREAUTH.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
            USERS.add(User("0",email, name, Roles.MEMBER)).addOnSuccessListener {
                Toast.makeText(this,"Akun berhasil dibuat",Toast.LENGTH_LONG).show()
                startActivity(Intent(this,LoginActivity::class.java))
            }.addOnFailureListener(){ex->
                Toast.makeText(this,ex.message.toString(),Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener(){ex->
            Toast.makeText(this,ex.message.toString(),Toast.LENGTH_LONG).show()
        }
    }
}