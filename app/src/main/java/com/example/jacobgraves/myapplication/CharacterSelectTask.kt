package com.example.jacobgraves.myapplication

import com.example.jacobgraves.myapplication.CharacterSelectController
import java.util.*

class CharacterSelectTask (private val selectScreen: CharacterSelectController): TimerTask() {

    override fun run() {
        selectScreen.update()
    }
}