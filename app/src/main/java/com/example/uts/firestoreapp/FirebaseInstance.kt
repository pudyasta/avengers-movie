package com.example.uts.firestoreapp

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage

class FirebaseInstance {
    companion object {
        val APP = FirebaseFirestore.getInstance()
        val FIREAUTH = FirebaseAuth.getInstance()
        val MOVIES = APP.collection("movies")
        val USERS = APP.collection("users")
        val STORAGE = FirebaseStorage.getInstance().reference.child("images")
    }
}