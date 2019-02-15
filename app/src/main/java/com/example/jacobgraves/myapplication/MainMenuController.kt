package com.example.jacobgraves.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.character_select_view.*
import kotlinx.android.synthetic.main.main_menu_view.*

class main_menu_controller : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu_view)

        configure_buttons()

    }

    fun configure_buttons(){
        play_button.setOnClickListener {
            play_button_clicked()
        }
        shop_button.setOnClickListener {
            shop_button_clicked()
        }
        settings_button.setOnClickListener {
            settings_button_clicked()
        }

        //settings_button.setOnTouchListener()
    }

    fun play_button_clicked(){
        val intent = Intent(this, character_select_controller::class.java)
        this.startActivity(intent)
    }

    fun shop_button_clicked(){
        val intent = Intent(this, shop_controller::class.java)
        this.startActivity(intent)
    }

    fun settings_button_clicked(){
        val intent = Intent(this, settings_controller::class.java)
        this.startActivity(intent)
    }
}
