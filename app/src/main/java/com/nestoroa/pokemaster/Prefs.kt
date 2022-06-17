package com.nestoroa.pokemaster

import android.content.Context

class Prefs (val context:Context) {

    val SHARED_NAME = "Mydtb"
    val SHARED_USER_NAME = "username"
    val SHARED_USER_PIC = "https://static.wikia.nocookie.net/pokemon-uranium/images/1/11/TrainerVitor.png"
    val SHARED_POKEMONS = "Pikachu"

    val storage = context.getSharedPreferences(SHARED_NAME, 0)

    fun saveName(name:String) = storage.edit().putString(SHARED_USER_NAME, name).apply()

    fun saveGender(gender:String) = storage.edit().putString(SHARED_USER_PIC, gender).apply()

    fun saveName(team:String) = storage.edit().putString(SHARED_POKEMONS, team).apply()

    fun wipe() = storage.edit().clear().apply()

}