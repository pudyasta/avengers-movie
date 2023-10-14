package com.example.uts

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.uts.databinding.ActivityMainBinding
import com.example.uts.databinding.ItemFilmsBinding

class FilmAdapter (
    var films: List<Films>
        ):RecyclerView.Adapter<FilmAdapter.FilmViewHolder>() {
    inner class FilmViewHolder(val binding:ItemFilmsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val binding = ItemFilmsBinding.inflate(LayoutInflater.from(parent.context))
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context,DetailActivity::class.java)
            intent.putExtra("title",films[position].title)
            intent.putExtra("poster",films[position].image)
            intent.putExtra("genre",films[position].genre)
            intent.putExtra("rating",films[position].rating.toString())
            intent.putExtra("year",films[position].year.toString())
            intent.putExtra("age",films[position].age.toString())
            intent.putExtra("duration",films[position].duration.toString())
            intent.putExtra("desc",films[position].desc)
            context.startActivity(intent)
        }
        holder.itemView.apply {
            with(holder.binding){
                filmTitle.text=films[position].title
                poster.setImageResource(films[position].image)
                genre.text=films[position].genre
                rating.text=films[position].rating.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return films.size
    }

}