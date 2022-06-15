package com.nestoroa.pokemaster

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nestoroa.pokemaster.databinding.ItemPokemonMiniBinding

class PokemonListAdapter(private val listener: OnClickListener) :
    ListAdapter<Pokemon, PokemonListAdapter.ViewHolder>(PokemonDiffCallback()){

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val view = LayoutInflater.from(context).inflate(R.layout.item_pokemon_mini, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = getItem(position)

        with(holder as ViewHolder){
            binding.tvPokemonId.text = "#${pokemon.id.toString()}"
            holder.binding.tvPokemonName.text = pokemon.name
            holder.binding.tvPokemonType.text = pokemon.types

            Glide.with(context)
                .load(pokemon.spriteURL)
                .into(binding.imgPokemonSprite)
        }

    }

    inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val binding = ItemPokemonMiniBinding.bind(view)
    }

    class PokemonDiffCallback: DiffUtil.ItemCallback<Pokemon>() {
        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean = oldItem == newItem

    }

}