package com.example.jacobgraves.myapplication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.character_select_view.*

class CharacterSelectController : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_select_view)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        runSetup()
    }

    fun runSetup(){
        configureButtons()
        println("Setup Complete in character select")
    }

    fun configureButtons(){
        characterSelectButton.setOnClickListener {
            val intent = Intent(this, GameController::class.java)
            val name = "Reggie"
            intent.putExtra("name", name)
            this.startActivity(intent)
        }
    }
}