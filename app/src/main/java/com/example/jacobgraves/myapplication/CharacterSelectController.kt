package com.example.jacobgraves.myapplication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.character_select_view.*

class character_select_controller : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_select_view)

        configure_buttons()

    }

    fun configure_buttons(){
        character_select_button.setOnClickListener {
            val intent = Intent(this, game_controller::class.java)
            this.startActivity(intent)
        }
    }
}