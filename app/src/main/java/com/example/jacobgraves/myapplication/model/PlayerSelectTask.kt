package com.example.jacobgraves.myapplication.model

import com.example.jacobgraves.myapplication.CharacterSelectController
import java.util.*

class PlayerSelectTask (private val selectScreen: CharacterSelectController): TimerTask() {

    override fun run() {
        selectScreen.update()
    }
}