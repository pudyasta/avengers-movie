package com.example.uts.recyclers.Hero

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uts.databinding.ItemHeroBinding
import com.squareup.picasso.Picasso


class HeroAdapter(var heros:List<Hero>):RecyclerView.Adapter<HeroAdapter.HeroViewHolder>()
{
    inner class HeroViewHolder(val binding: ItemHeroBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        val binding = ItemHeroBinding.inflate(LayoutInflater.from(parent.context))
        return HeroViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return heros.size
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.itemView.apply {
            with(holder.binding){
                hero.text=heros[position].title
                Picasso.get().load(heros[position].image).into(poster)

            }
        }
    }


}