package com.example.uts.firestoreapp

import android.util.Log
import android.widget.Toast
import com.example.uts.databinding.ItemFilmsBinding
import com.example.uts.model.Films
import com.example.uts.model.Movies

class MovieController {
//    public fun addBudget(movie: Films,binding: ItemFilmsBinding) {
//        FirebaseInstance.MOVIES.add(movie)
//            .addOnSuccessListener { documentReference ->
//                val createdBudgetId = documentReference.id
//                movie.id = createdBudgetId
//                documentReference.set(movie)
//                    .addOnFailureListener {
//                        Log.d("MainActivity", "Error updating budget ID: ", it)
//                    }
//            }
//            .addOnFailureListener {
//                Log.d("MainActivity", "Error adding budget: ", it)
//            }
//    }
//    public fun updateBudget(movie: Films) {
//        val movieRef = FirebaseInstance.MOVIES.document(movie.id)
//
//        val namaValue = editNama.text.toString()
//        val titleValue = editTitle.text.toString()
//        val isiValue = editNote.text.toString()
//
//        movieRef.update(mapOf(
//            "nama" to namaValue,
//            "judul" to titleValue,
//            "isi" to isiValue
//        ))
//            .addOnSuccessListener {
//                Toast.makeText(applicationContext, "Data successfully updated!", Toast.LENGTH_LONG).show()
//                finish()
//            }
//            .addOnFailureListener { e ->
//                Log.w("FirestoreUpdate", "Error updating document", e)
//            }
//
//    }
//    public fun deleteBudget(movie: Films) {
//        if (movie.id.isEmpty()) {
//            Log.d("MainActivity", "Error deleting: budget ID is empty!")
//            return
//        }
//        budgetCollectionRef.document(movie.id).delete()
//            .addOnFailureListener {
//                Log.d("MainActivity", "Error deleting budget: ", it)
//            }
//    }

}