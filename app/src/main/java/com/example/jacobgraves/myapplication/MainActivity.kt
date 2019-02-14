package com.example.jacobgraves.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.character_select_view.*
import kotlinx.android.synthetic.main.main_menu_view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu_view)

        play_button.setOnClickListener {
            play_button.text = "Got Me!"
            setContentView(R.layout.character_select_view)
            character_select_button.setOnClickListener {
                setContentView(R.layout.game_view)
            }
        }



    }
}
