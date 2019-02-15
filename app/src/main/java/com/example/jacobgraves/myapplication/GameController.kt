package com.example.jacobgraves.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.example.jacobgraves.myapplication.model.Engine
import kotlinx.android.synthetic.main.game_view.*
import java.util.*
import kotlin.concurrent.schedule

class GameController : AppCompatActivity() {
    lateinit var gameEngine: Engine
    var timer = Timer()
    lateinit var image: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_view)

        runInitSetup()

        println("x " + image.x)
        println("y " + image.y)
    }

    fun runInitSetup() {
        gameEngine = Engine(intent.getStringExtra("name"))
        timer.schedule(1,16) {
            update()
        }
        setupImages()
        println("Init Game Setup Complete")
    }

    fun runSetup() {
        timer.schedule(1,16) {
            gameEngine.update()
        }
        println("Regular Game Setup Complete")
    }

    fun setupImages(){
        image = imageView
    }

    override fun onBackPressed() {
        super.onBackPressed()
        println("Did you press the back button?")
        timer.cancel()
    }

    fun update(){
        gameEngine.update()
        image.x = gameEngine.imageX
        image.y = gameEngine.imageY
    }
}