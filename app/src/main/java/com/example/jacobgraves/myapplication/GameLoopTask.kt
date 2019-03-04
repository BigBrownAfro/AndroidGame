package com.example.jacobgraves.myapplication

import java.util.*

class GameLoopTask(private val gameController: GameController):TimerTask() {

    override fun run() {
        gameController.update()
    }
}