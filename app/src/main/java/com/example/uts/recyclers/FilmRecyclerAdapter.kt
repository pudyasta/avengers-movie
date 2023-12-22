package com.example.uts.recyclers

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uts.databinding.ItemFilmPreviewBinding
import com.example.uts.databinding.ItemHeroBinding
import com.example.uts.model.Films
import com.example.uts.recyclers.Hero.Hero
import com.example.uts.recyclers.Hero.HeroAdapter
import com.squareup.picasso.Picasso

class FilmRecyclerAdapter(private var films:ArrayList<Films>,private val listener:OnAdapterListener): RecyclerView.Adapter<FilmRecyclerAdapter.FilmPreviewViewHolder>() {

    inner class FilmPreviewViewHolder(val binding: ItemFilmPreviewBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmPreviewViewHolder {
        val binding = ItemFilmPreviewBinding.inflate(LayoutInflater.from(parent.context))
        return FilmPreviewViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return films.size
    }

    override fun onBindViewHolder(holder: FilmPreviewViewHolder, position: Int) {
        holder.itemView.apply {
            with(holder.binding){
                filmTitle.text = films[position].title
                Log.d("tes1",films[position].title)
                Picasso.get().load(films[position].image).into(poster)
                editButton.setOnClickListener(){
                    listener.onUpdate(films[position])
                }
                deleteButton.setOnClickListener(){
                    listener.onDelete(films[position])
                }
            }
        }
    }

    fun setData(list: List<Films>){
        films.clear()
        films.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onClick(f: Films)
        fun onUpdate(f: Films)
        fun onDelete(f: Films)
    }

}