package com.nestoroa.pokemaster

import android.app.Application

class PokemonTrainerApplication : Application() {

    companion object{
        lateinit var prefs:Prefs
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }
}