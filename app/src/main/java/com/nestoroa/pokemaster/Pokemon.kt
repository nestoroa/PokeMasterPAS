package com.nestoroa.pokemaster

import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class Pokemon(
    val id: Int = 25,
    val name: String = "Pikachu",
    val types: String = "Electric",
    val spriteURL: String = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png"
)


public fun randPokemon(): Pokemon {
    val url = "https://pokeapi.co/api/v2/pokemon/${(1..666).random()}"
    var pokemon = Pokemon()
    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, url, null,
        { response ->
            val pkName = response.getString("name")
            val pkTypes = response.getJSONArray("types")
            var pkType = pkTypes.getJSONObject(0).getJSONObject("type").getString("name")
            for (i in 1 until pkTypes.length()) {
                pkType = pkType + ", " + pkTypes.getJSONObject(i).getJSONObject("type").getString("name")
            }
            val pkNumber = response.getInt("id")
            val pkSpriteURL = response.getJSONObject("sprites").getString("front_default")
            Log.d("PokeLog","$pkName,$pkNumber,$pkType,$pkSpriteURL")
            pokemon = (Pokemon(
                name = pkName,
                id = pkNumber,
                types = pkType,
                spriteURL = pkSpriteURL
            ))
        },
        { error ->
            pokemon = Pokemon(
                name = "MissingNo",
                spriteURL = "http://images.wikia.com/unanything/images/c/c1/MISSINGNO.png",
                id = 999,
                types = error.toString()
            )

        }
    )
    NetworkSingleton.getInstance()
        .getInstance(this.context).addToRequestQueue(jsonObjectRequest)
    return pokemon
}