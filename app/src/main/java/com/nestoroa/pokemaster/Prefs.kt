package com.nestoroa.pokemaster

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Prefs(val context: Context) {

    var SHARED_NAME = "PokemonTrainerPreferences"
    var SHARED_USER_NAME = "trainer_name"
    var SHARED_USER_PIC = "trainer_pic"
    var SHARED_POKEMONS = "trainer_team"

    val storage = context.getSharedPreferences(SHARED_NAME, 0)

    fun saveName(name: String) = storage.edit().putString(SHARED_USER_NAME, name).apply()

    fun savePicURL(gender: String) = storage.edit().putString(SHARED_USER_PIC, gender).apply()

    fun savePokemons(team: MutableList<Pokemon>) {
        val teamJson = Gson().toJson(team)
        print(teamJson)
        storage.edit().putString(SHARED_POKEMONS, teamJson.toString()).apply()
    }

    fun getName(): String? = storage.getString(SHARED_USER_NAME, "")
    fun getPicURL(): String? = storage.getString(SHARED_USER_PIC, "")
    fun getPokemons(): MutableList<Pokemon> {
        val gson = Gson()
        val teamJson = storage.getString(SHARED_POKEMONS, "[]")
        val listType: Type = object : TypeToken<ArrayList<Pokemon?>?>() {}.type
        return gson.fromJson(teamJson, listType)
    }

    fun wipe() = storage.edit().clear().apply()
}
