package com.example.jacobgraves.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.jacobgraves.myapplication.model.Engine
import com.example.jacobgraves.myapplication.model.Player
import kotlinx.android.synthetic.main.main_menu_view.*

class MainMenuController : AppCompatActivity() {

    companion object {
        var gameEngine:Engine? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu_view)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        runSetup()
    }

    fun runSetup(){
        configureButtons()
        println("Setup Complete in menu")
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
