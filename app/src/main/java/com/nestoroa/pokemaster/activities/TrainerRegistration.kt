package com.nestoroa.pokemaster.activities

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.nestoroa.pokemaster.Pokemon
import com.nestoroa.pokemaster.PokemonTrainerApplication.Companion.prefs
import com.nestoroa.pokemaster.R
import com.nestoroa.pokemaster.databinding.ActivityTrainerRegistrationBinding

class TrainerRegistration : Activity() {
    private var pictureURL = ""
    private lateinit var binding: ActivityTrainerRegistrationBinding
    private val professorURL =
        "https://archives.bulbagarden.net/media/upload/thumb/3/30/FireRed_LeafGreen_Professor_Oak.png/481px-FireRed_LeafGreen_Professor_Oak.png"
    private val trainerNBURL =
        "https://static.wikia.nocookie.net/pokemon-uranium/images/0/0d/TrainerPluto.png"
    private val trainerMaleURL =
        "https://static.wikia.nocookie.net/pokemon-uranium/images/1/11/TrainerVitor.png"
    private val trainerFemaleURL =
        "https://static.wikia.nocookie.net/pokemon-uranium/images/2/27/TrainerNatalie.png"


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrainerRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setPictures()
        setButtons()

        val etTrainerName = findViewById<EditText>(R.id.etTrainerName)
        val btnRegister = findViewById<Button>(R.id.btnRegisterTrainer)

        btnRegister.setOnClickListener {
            if (etTrainerName.toString().isEmpty()) {
                // Toast solicitando nombre
            } else if (pictureURL.toString().isEmpty()) {
                // Toast solicitando avatar
            } else {
                prefs.saveName(etTrainerName.text.toString())
                prefs.savePicURL(pictureURL)
                prefs.savePokemons(mutableListOf(Pokemon()))
                finish()
            }
        }
    }

    private fun setPictures() {
        Glide.with(this)
            .load(professorURL)
            .into(binding.imgProfessorPicture)
        Glide.with(this)
            .load(trainerNBURL)
            .into(binding.btnTrainerNB)
        Glide.with(this)
            .load(trainerMaleURL)
            .into(binding.btnTrainerMale)
        Glide.with(this)
            .load(trainerFemaleURL)
            .into(binding.btnTrainerFemale)
    }

    private fun setButtons() {
        binding.btnTrainerNB.setBackgroundResource(R.drawable.white_button)
        binding.btnTrainerMale.setBackgroundResource(R.drawable.white_button)
        binding.btnTrainerFemale.setBackgroundResource(R.drawable.white_button)

        binding.btnTrainerNB.setOnClickListener {
            pictureURL = trainerNBURL
            binding.btnTrainerNB.setBackgroundResource(R.drawable.red_button)
            binding.btnTrainerMale.setBackgroundResource(R.drawable.white_button)
            binding.btnTrainerFemale.setBackgroundResource(R.drawable.white_button)
        }

        binding.btnTrainerMale.setOnClickListener {
            pictureURL = trainerMaleURL
            binding.btnTrainerMale.setBackgroundResource(R.drawable.red_button)
            binding.btnTrainerNB.setBackgroundResource(R.drawable.white_button)
            binding.btnTrainerFemale.setBackgroundResource(R.drawable.white_button)
        }

        binding.btnTrainerFemale.setOnClickListener {
            pictureURL = trainerFemaleURL
            binding.btnTrainerFemale.setBackgroundResource(R.drawable.red_button)
            binding.btnTrainerMale.setBackgroundResource(R.drawable.white_button)
            binding.btnTrainerNB.setBackgroundResource(R.drawable.white_button)

        }
    }
}