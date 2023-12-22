package com.example.uts

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import com.example.uts.databinding.ActivityAddFilmBinding
import com.example.uts.firestoreapp.FirebaseInstance.Companion.MOVIES
import com.example.uts.firestoreapp.FirebaseInstance.Companion.STORAGE
import com.example.uts.model.Films
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception

class AddFilmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddFilmBinding
    private var noteId:String = "0"
    private val type = listOf<String>("Film","Series")
    private var imageUri:Uri? = null
    private lateinit var  notifManager:NotificationManager
    private val notifId = 0
    private val channelId = "FILM_NOTIF"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFilmBinding.inflate(layoutInflater)
        notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        setContentView(binding.root)
        setupSpinner()
        setupListener()
        setupView()

    }

    private fun setupSpinner()  {
        with(binding){
            val adapterTipe = ArrayAdapter(this@AddFilmActivity,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,type)
            adapterTipe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTipe.adapter= adapterTipe


        }
    }

    private fun setupListener() {
        with(binding){
            imagePreview.setOnClickListener{
                resultLauncher.launch("image/*")
            }

            buttonSave.setOnClickListener(){
                if (editTitle.text.toString().isEmpty()){
                    editTitle.error = "Judul harus diisi"
                    return@setOnClickListener
                }

                if (editNote.text.toString().isEmpty()){
                    editNote.error = "Deskripsi harus diisi"
                    return@setOnClickListener
                }

                if (yearNote.text.toString().isEmpty()){
                    yearNote.error = "Tahun harus diisi"
                    return@setOnClickListener
                }

                if (imageUri.toString().isEmpty()){
                    Toast.makeText(this@AddFilmActivity,"Gambar harus diupload",Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                if (ageNote.text.toString().isEmpty()){
                    ageNote.error = "Usia minimum harus diisi"
                    return@setOnClickListener
                }

                if (durationNote.text.toString().isEmpty()){
                    durationNote.error = "Durasi harus diisi"
                    return@setOnClickListener
                }

                if (ratingNote.text.toString().isEmpty()){
                    ratingNote.error = "Rating harus diisi"
                    return@setOnClickListener
                }

                if (genreNote.text.toString().isEmpty()){
                    genreNote.error = "Genre harus diisi"
                    return@setOnClickListener
                }

                val store =  STORAGE.child(System.currentTimeMillis().toString())
                imageUri?.let { it1 -> store.putFile(it1).addOnCompleteListener(){
                    if (it.isSuccessful){
                        store.downloadUrl.addOnSuccessListener { uri->

                            var film = Films("0",editTitle.text.toString(),
                                genreNote.text.toString(),
                                spinnerTipe.selectedItem.toString()
                                ,editNote.text.toString(),uri.toString(),
                                yearNote.text.toString().toInt(),
                                ageNote.text.toString().toInt(),
                                durationNote.text.toString().toInt(),
                                ratingNote.text.toString().toDouble())

                            MOVIES.add(film).addOnSuccessListener(){res->
                                film.id = res.id
                                res.set(film).addOnSuccessListener {
                                    Toast.makeText(this@AddFilmActivity,"Film berhasil ditambahkan",Toast.LENGTH_LONG).show()
                                    finish()
                                    setupNotif(imageUri.toString())
                                }.addOnFailureListener {
                                    Toast.makeText(applicationContext,"Terdapat Kesalahan",Toast.LENGTH_LONG).show()
                                }

                            }.addOnFailureListener(){err->
                                Toast.makeText(this@AddFilmActivity,err.message.toString(),Toast.LENGTH_LONG).show()
                            }
                        }
                    }else{
                        Toast.makeText(this@AddFilmActivity,it.exception.toString(),Toast.LENGTH_LONG).show()
                    }
                }
                }


            }

            buttonUpdate.setOnClickListener(){
                val film = MOVIES.document(noteId)

                val title = editTitle.text.toString()
                val genre = genreNote.text.toString()
                val type = spinnerTipe.selectedItem.toString()
                val year = yearNote.text.toString().toInt()
                val age = ageNote.text.toString().toInt()
                val duration = durationNote.text.toString().toInt()
                val rating = ratingNote.text.toString().toDouble()
                val store =  STORAGE.child(System.currentTimeMillis().toString())

                if (imageUri == null) {
                    film.update(
                        mapOf(
                            "title" to title,
                            "genre" to genre,
                            "type" to type,
                            "year" to year,
                            "age" to age,
                            "duration" to duration,
                            "rating" to rating
                        )
                    ).addOnSuccessListener {
                        Toast.makeText(applicationContext, "Data successfully updated!", Toast.LENGTH_LONG).show()
                        finish()
                    }.addOnFailureListener { e ->
                        Log.w("FirestoreUpdate", "Error updating document", e)
                    }
                } else {
                    imageUri?.let { it1 -> store.putFile(it1).addOnCompleteListener(){
                        if (it.isSuccessful){
                            store.downloadUrl.addOnSuccessListener { uri->
                                film.update(
                                    mapOf(
                                        "title" to title,
                                        "genre" to genre,
                                        "type" to type,
                                        "year" to year,
                                        "age" to age,
                                        "duration" to duration,
                                        "rating" to rating,
                                        "image" to uri.toString() // Assuming you have a field "imageUrl" in your Firestore document
                                    )
                                ).addOnSuccessListener {
                                    Toast.makeText(applicationContext, "Data successfully updated!", Toast.LENGTH_LONG).show()
                                    finish()
                                }.addOnFailureListener { e ->
                                    Log.w("FirestoreUpdate", "Error updating document", e)
                                }
                            }
                        }else{
                            Toast.makeText(this@AddFilmActivity,it.exception.toString(),Toast.LENGTH_LONG).show()
                        }
                    }
                    }}
            }
        }
    }

    private fun setupView(){
        val intentType = intent.getIntExtra("intentType",0)
        when(intentType){
//            View
            0->{
                binding.buttonSave.visibility = View.GONE
            }
//            Update
            2->{
                binding.buttonSave.visibility = View.GONE
                binding.buttonUpdate.visibility = View.VISIBLE
                loadFilm()

            }
        }

    }

    private fun loadFilm(){
        noteId = intent.getStringExtra("id").toString()
        with(binding){
            MOVIES.whereEqualTo("id",noteId).get().addOnSuccessListener { documents ->
                for (document in documents) {
                    editTitle.setText(document.getString("title"))
                    editNote.setText(document.getString("desc"))
                    durationNote.setText(document.getLong("duration").toString())
                    genreNote.setText(document.getString("genre"))
                    Picasso.get().load(document.getString("image")).into(imagePreview)
                    ratingNote.setText(document.getLong("rating").toString())
                    val i = type.indexOf(document.getString("type"))
                    spinnerTipe.setSelection(i)
                    yearNote.setText(document.getLong("year").toString())
                    ageNote.setText(document.getLong("age").toString())
                }
            }.addOnFailureListener { exception ->
            }
        }
    }

    private fun setupNotif(imageUrl: String) {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Use Picasso to load the image from the URL
            Picasso.get()
                .load(imageUrl)
                .into(object : Target {
                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        val builder = NotificationCompat.Builder(this@AddFilmActivity, channelId)
                            .setSmallIcon(R.drawable.baseline_notifications_24)
                            .setContentTitle("Film baru ditambahkan")
                            .setContentText("Segera cek film baru agar tidak kehabisan tiket")
                            .setStyle(
                                NotificationCompat.BigPictureStyle()
                                    .bigPicture(bitmap)
                            )
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent)

                        notifManager.notify(notifId, builder.build())
                    }

                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                        // Handle failure to load image
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                        // Handle loading preparation
                    }
                })
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()){
        imageUri = it
        binding.imagePreview.setImageURI(it)
    }


}