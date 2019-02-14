package com.example.jacobgraves.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.character_select_view.*
import kotlinx.android.synthetic.main.game_view.*
import kotlinx.android.synthetic.main.main_menu_view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_view)

        damage_button.setOnClickListener {
            health_image_view.setImageResource(R.drawable.test_heart)




            }
        }



    }

