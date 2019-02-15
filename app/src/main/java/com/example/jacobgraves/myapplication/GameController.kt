package com.example.jacobgraves.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.example.jacobgraves.myapplication.model.Engine
import kotlinx.android.synthetic.main.game_view.*
import kotlinx.android.synthetic.main.main_menu_view.*
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

        var listener = View.OnTouchListener(function = { view, motionEvent ->


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


        joystickImageView.setOnTouchListener(listener)



    }


    fun runInitSetup() {
        gameEngine = Engine(intent.getStringExtra("name"))
        timer.schedule(1,16) {
            update()

        }
        setupImages()
        println("Init Game Setup Complete")

        moveUpButton.setOnClickListener {
            playerMoveUp()
        }
        moveDownButton.setOnClickListener {
            playerMoveDown()
        }
        moveRightButton.setOnClickListener {
            playerMoveRight()
        }
        moveLeftButton.setOnClickListener() {
            playerMoveLeft()
        }
    }

    fun runSetup() {

        timer.schedule(1,16) {
            gameEngine.update()
        }
        println("Regular Game Setup Complete")
    }

    fun setupImages(){
        image = charimageView
    }

    override fun onBackPressed() {
        super.onBackPressed()
        println("Did you press the back button?")
        timer.cancel()
    }

    fun update(){
        gameEngine.update()

        if (joystickImageView.x > 500){
            playerMoveRight()
        }
        if (joystickImageView.x < 0){
            playerMoveLeft()
        }
        if (joystickImageView.y > 800){
            playerMoveDown()
        }
        if (joystickImageView.y < 400){
            playerMoveUp()
        }
       // image.x = gameEngine.imageX
       // image.y = gameEngine.imageY
    }
    fun playerMoveUp(){
        image.y = image.y - 10
    }
    fun playerMoveDown(){
        image.y = image.y + 10
    }
    fun playerMoveRight(){
        image.x = image.x + 10
    }
    fun playerMoveLeft(){
        image.x = image.x - 10
    }


}
