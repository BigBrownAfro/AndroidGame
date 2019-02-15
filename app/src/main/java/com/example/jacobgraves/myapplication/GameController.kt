package com.example.jacobgraves.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.jacobgraves.myapplication.model.Engine
import com.example.jacobgraves.myapplication.model.player
import kotlinx.android.synthetic.main.character_select_view.*

class game_controller : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_view)

        initModel()
    }

    fun initModel(){
        val gameEngine = Engine()

    }
}