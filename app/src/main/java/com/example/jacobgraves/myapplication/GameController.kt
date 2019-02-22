package com.example.jacobgraves.myapplication

import android.os.Bundle
import android.support.constraint.ConstraintLayout
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
    lateinit var playerBullets:Array<ImageView>

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
        val constraintLayout = findViewById(R.id.constraintLayout) as ConstraintLayout
        playerBullets = Array(100){ImageView(this)}
        for (i in playerBullets){
            constraintLayout.addView(i)
        }
        enemyImage.setImageResource(gameEngine.enemy.image)
        enemyImage.x = 1000f
        enemyImage.y = 300f
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

        fireButton.setOnClickListener {
            gameEngine.player.shoot("right")
        }
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
        enemyImage.x = gameEngine.enemy.getXPosition()
        enemyImage.y = gameEngine.enemy.getYPosition()
        tempImageResource = gameEngine.player.image
        if (tempImageResource < 0){
            tempImageResource *= -1
            playerImage.setImageResource(tempImageResource)
            playerImage.rotationY = 180f
        }else{
            playerImage.rotationY = 0f
            playerImage.setImageResource(gameEngine.player.image)
        }
        var count = 0
        for (bullet in gameEngine.player.bulletArray){
            if (bullet != null){
                playerBullets[count].setImageResource(bullet.image)
                playerBullets[count].x = bullet.xPosition + (playerImage.width/2)
                playerBullets[count].y = bullet.yPosition + (playerImage.height/2)
                //playerBullets[count].isEnabled = true
                //playerBullets[count].isActivated = true
            }
            count++
        }
    }
}
