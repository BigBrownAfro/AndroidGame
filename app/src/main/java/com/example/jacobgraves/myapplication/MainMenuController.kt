package com.example.jacobgraves.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.jacobgraves.myapplication.model.Player
import kotlinx.android.synthetic.main.main_menu_view.*

class MainMenuController : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu_view)

        runSetup()

    }

    fun runSetup(){
        configureButtons()
    }

    fun configureButtons(){
        playButton.setOnClickListener {
            playButtonClicked()
        }
        shopButton.setOnClickListener {
            shopButtonClicked()
        }
        settingsButton.setOnClickListener {
            settingsButtonClicked()
        }

        //settings_button.setOnTouchListener()
    }

    fun playButtonClicked(){
        val intent = Intent(this, CharacterSelectController::class.java)
        this.startActivity(intent)
    }

    fun shopButtonClicked(){
        val intent = Intent(this, ShopController::class.java)
        this.startActivity(intent)
    }

    fun settingsButtonClicked(){
        val intent = Intent(this, SettingsController::class.java)
        this.startActivity(intent)
    }
}
