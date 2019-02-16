package com.example.jacobgraves.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.example.jacobgraves.myapplication.model.Engine
import kotlinx.android.synthetic.main.game_view.*
import java.util.*
import kotlin.concurrent.schedule

class GameController : AppCompatActivity() {
    lateinit var gameEngine: Engine
    var timer = Timer()
    lateinit var joystickListener:View.OnTouchListener
    var timerSetup = false
    var tempImageResource = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_view)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        runInitSetup()
    }


    fun runInitSetup() {
        gameEngine = Engine(intent.getStringExtra("name"))
        timer.schedule(1,16) {
            update()
        }
        setupImages()
        setupButtons()
        setupListeners()
        println("Init Game Setup Complete")
    }

    fun setupListeners(){
        joystickListener = View.OnTouchListener(function = { view, motionEvent ->

            if (motionEvent.action == MotionEvent.ACTION_MOVE) {

                view.y = motionEvent.rawY - view.height/2
                view.x = motionEvent.rawX - view.width/2
                if (view.y < 300F){
                    view.y = 300F
                }
                if (view.y > 900F){
                    view.y = 900F
                }
                if (view.x < -10F){
                    view.x = -10F
                }
                if (view.x > 600F){
                    view.x = 600F
                }
            }
            true
        })
        joystickImage.setOnTouchListener(joystickListener)
    }

    fun setupImages(){
        playerImage.setImageResource(gameEngine.player.image)
        playerImage.scaleX = .5f
        playerImage.scaleY = .5f
        playerImage.x = gameEngine.player.getXPosition()
        playerImage.y = gameEngine.player.getYPosition()

        joystickImage.scaleX = .5f
        joystickImage.scaleY = .5f
    }

    fun setupButtons(){

    }

    override fun onBackPressed() {
        super.onBackPressed()
        println("Did you press the back button?")
        timer.cancel()
    }

    fun update(){
        runOnUiThread(Runnable() {
            run() {
                gameEngine.update()
                movePlayer()
                updateImages()
            }
        });
    }

    fun movePlayer(){
        if (joystickImage.y < 400){
            gameEngine.player.moveUp()
        }
        if (joystickImage.y > 800){
            gameEngine.player.moveDown()
        }
        if (joystickImage.x < 0){
            gameEngine.player.moveLeft()
        }
        if (joystickImage.x > 500){
            gameEngine.player.moveRight()
        }
        //print("" + joystickImage.x + " ")
        //println(joystickImage.y)
        playerImage.x = gameEngine.player.getXPosition()
        playerImage.y = gameEngine.player.getYPosition()
    }

    fun updateImages(){
        tempImageResource = gameEngine.player.image
        if (tempImageResource < 0){
            tempImageResource *= -1
            playerImage.setImageResource(tempImageResource)
            playerImage.rotationY = 180f
        }else{
            playerImage.rotationY = 0f
            playerImage.setImageResource(gameEngine.player.image)
        }
    }
}
