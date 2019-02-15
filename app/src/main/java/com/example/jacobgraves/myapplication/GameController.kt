package com.example.jacobgraves.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.jacobgraves.myapplication.model.Engine
import java.util.*
import kotlin.concurrent.schedule

class GameController : AppCompatActivity() {
    lateinit var gameEngine: Engine
    var timer = Timer()
    lateinit var timerTask: TimerTask

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_view)

        runSetup()
    }

    fun runSetup() {
        gameEngine = Engine(intent.getStringExtra("name"))
        timer.schedule(1000,16) {
            gameEngine.update()
        }
        println("I made it passed the timer")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        println("Did you press the back button?")
        timer.cancel()
    }
}