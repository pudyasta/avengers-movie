package com.example.uts

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uts.databinding.ItemFilmsBinding
import com.example.uts.model.Films
import com.squareup.picasso.Picasso

class FilmAdapter (
    var films: ArrayList<Films>
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
                Picasso.get().load(films[position].image).into(poster)
            }
        }
    }

    override fun getItemCount(): Int {
        return films.size
    }

    fun setData(list: List<Films>){
        films.clear()
        films.addAll(list)
        notifyDataSetChanged()
    }


}