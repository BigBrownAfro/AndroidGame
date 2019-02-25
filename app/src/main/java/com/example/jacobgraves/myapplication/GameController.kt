package com.example.jacobgraves.myapplication

import android.graphics.Color
import android.graphics.RectF
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import com.example.jacobgraves.myapplication.model.Engine
import com.example.jacobgraves.myapplication.model.Player
import kotlinx.android.synthetic.main.game_view.*
import java.lang.Thread.sleep
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.*

class GameController : AppCompatActivity() {
    lateinit var gameEngine: Engine
    var timer = Timer()
    lateinit var joystickListener:View.OnTouchListener
    var tempImageResource = 0
    lateinit var playerBullets:Array<ImageView>
    lateinit var enemyBullets:Array<ImageView>
    var joystickOriginX = 300f
    var joystickOriginY = 1080f-300f
    var joyStickX = joystickOriginX
    var joyStickY = joystickOriginY
    var joystickMoveRadius = 100f
    var joystickPlayerMoveRadius = 50f
    var joystick2OriginX = 1920f-400f
    var joystick2OriginY = 1080f-300f
    var joyStick2X = joystick2OriginX
    var joyStick2Y = joystick2OriginY
    var joystick2MoveRadius = 100f
    var joystick2PlayerMoveRadius = 50f
    lateinit var enemies:Array<ImageView>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_view)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        runInitSetup()
    }

    //Setup------------------------------------------------------------------------------------------
    fun runInitSetup() {
        gameEngine = Engine(intent.getStringExtra("name"))
        setupImages()
        setupButtons()
        setupListeners()

        println("Init Game Setup Complete")
        Thread.sleep(3000)
        timer.schedule(1,16) {
            var start = System.currentTimeMillis()
            runOnUiThread(Runnable() {
                run() {
                    update()
                }
            });
            println(System.currentTimeMillis() - start)
        }
    }

    fun setupListeners(){
        joystickListener = View.OnTouchListener(function = { view, motionEvent ->

            if (motionEvent.action == MotionEvent.ACTION_MOVE) {

                joyStickX = motionEvent.rawX
                joyStickY = motionEvent.rawY

                var xDifference = motionEvent.rawX - joystickOriginX
                var yDifference = joystickOriginY - motionEvent.rawY
                var hypotenuse = hypot(xDifference, yDifference)

                if(hypotenuse > joystickMoveRadius){
                    var angleC = acos(xDifference/hypotenuse)

                    if(joyStickY < joystickOriginY){
                    }else{
                        angleC *= (-1f)
                    }

                    joyStickX = joystickOriginX + joystickMoveRadius * cos(angleC)
                    joyStickY = joystickOriginY - joystickMoveRadius * sin(angleC)
                }

                joystickImage.x = joyStickX - joystickImage.width/2
                joystickImage.y = joyStickY - joystickImage.height/2

            }else{
                joyStickX = joystickOriginX
                joyStickY = joystickOriginY
                joystickImage.x = joyStickX - joystickImage.width/2
                joystickImage.y = joyStickY - joystickImage.height/2
            }
            true
        })

        var joystick2Listener = View.OnTouchListener(function = { view, motionEvent ->

            if (motionEvent.action == MotionEvent.ACTION_MOVE) {

                joyStick2X = motionEvent.rawX
                joyStick2Y = motionEvent.rawY

                var xDifference = motionEvent.rawX - joystick2OriginX
                var yDifference = joystick2OriginY - motionEvent.rawY
                var hypotenuse = hypot(xDifference, yDifference)

                if(hypotenuse > joystick2MoveRadius){
                    var angleC = acos(xDifference/hypotenuse)

                    if(joyStick2Y < joystick2OriginY){
                    }else{
                        angleC *= (-1f)
                    }

                    joyStick2X = joystick2OriginX + joystick2MoveRadius * cos(angleC)
                    joyStick2Y = joystick2OriginY - joystick2MoveRadius * sin(angleC)
                }

                joystickImage2.x = joyStick2X - joystickImage2.width/2
                joystickImage2.y = joyStick2Y - joystickImage2.height/2

            }else{
                joyStick2X = joystick2OriginX
                joyStick2Y = joystick2OriginY
                joystickImage2.x = joyStick2X - joystickImage2.width/2
                joystickImage2.y = joyStick2Y - joystickImage2.height/2
            }
            true
        })

        joystickImage.setOnTouchListener(joystickListener)
        joystickImage2.setOnTouchListener(joystick2Listener)
    }

    fun setupImages(){
        val constraintLayout = findViewById(R.id.constraintLayout) as ConstraintLayout

        //Player images
        playerImage.setImageResource(gameEngine.player.image)
        playerImage.getLayoutParams().width = gameEngine.player.getWidth()
        playerImage.getLayoutParams().height = gameEngine.player.getHeight()
        playerImage.x = gameEngine.player.getXPosition()
        playerImage.y = gameEngine.player.getYPosition()

        //Enemies images
        enemies = Array(50){ ImageView(this) }
        for(image in enemies){
            constraintLayout.addView(image)
        }

        for(enemy in gameEngine.freezos) {
            enemyImage.setImageResource(enemy.image)
            enemyImage.getLayoutParams().width = enemy.getWidth()
            enemyImage.getLayoutParams().height = enemy.getHeight()
            enemyImage.x = enemy.getXPosition()
            enemyImage.y = enemy.getYPosition()
        }
        /*
        enemyImage.getLayoutParams().width = gameEngine.enemy.getWidth()
        enemyImage.getLayoutParams().height = gameEngine.enemy.getHeight()
        enemyImage.setImageResource(gameEngine.enemy.image)
        enemyImage.x = gameEngine.enemy.getXPosition()
        enemyImage.y = gameEngine.enemy.getYPosition()
        */

        //Joysticks images
        joystickImage.getLayoutParams().width = 100
        joystickImage.getLayoutParams().height = 100
        joystickImage2.getLayoutParams().width = 100
        joystickImage2.getLayoutParams().height = 100

        //Setup Bullets
        playerBullets = Array(200){ImageView(this)}
        for (i in playerBullets){
            constraintLayout.addView(i)
        }
        enemyBullets = Array(500){ImageView(this)}
        for (i in enemyBullets){
            constraintLayout.addView(i)
        }
    }

    fun setupButtons(){
    }

    override fun onBackPressed() {
        super.onBackPressed()
        println("Did you press the back button?")
        timer.cancel()
    }

    //Updating------------------------------------------------------------------------------------------
    fun update(){
        gameEngine.update()
        movePlayer()
        shootPlayer()
        updateImages()
    }

    fun movePlayer(){

        var xDifference = joyStickX - joystickOriginX
        var yDifference = joystickOriginY - joyStickY
        var hypotenuse = hypot(xDifference, yDifference)

        if(hypotenuse > joystickPlayerMoveRadius){
            var angleC = acos(xDifference/hypotenuse)

            if(joyStickY > joystickOriginY){
                angleC *= (-1f)
            }

            if(angleC < PI*(1f/8f) && angleC >= PI*(-1f/8f)){
                gameEngine.player.moveRight()
            }
            if(angleC < PI*(3f/8f) && angleC >= PI*(1f/8f)){
                gameEngine.player.moveRight()
                gameEngine.player.moveUp()
            }
            if(angleC < PI*(5f/8f) && angleC >= PI*(3f/8f)){
                gameEngine.player.moveUp()
            }
            if(angleC < PI*(7f/8f) && angleC >= PI*(5f/8f)){
                gameEngine.player.moveUp()
                gameEngine.player.moveLeft()
            }
            if(angleC >= PI*(7f/8f) || angleC < PI*(-7f/8f)){
                gameEngine.player.moveLeft()
            }
            if(angleC < PI*(-5f/8f) && angleC >= PI*(-7f/8f)){
                gameEngine.player.moveLeft()
                gameEngine.player.moveDown()
            }
            if(angleC < PI*(-3f/8f) && angleC >= PI*(-5f/8f)){
                gameEngine.player.moveDown()
            }
            if(angleC < PI*(-1f/8f) && angleC >= PI*(-3f/8f)){
                gameEngine.player.moveDown()
                gameEngine.player.moveRight()
            }

        }
    }

    fun shootPlayer(){
        var xDifference = joyStick2X - joystick2OriginX
        var yDifference = joystick2OriginY - joyStick2Y
        var hypotenuse = hypot(xDifference, yDifference)

        if(hypotenuse > joystick2PlayerMoveRadius){
            var angleC = acos(xDifference/hypotenuse)

            if(joyStick2Y > joystick2OriginY){
                angleC *= (-1f)
            }

            if(angleC < PI*(1f/8f) && angleC >= PI*(-1f/8f)){
                gameEngine.player.shoot("right")
            }
            if(angleC < PI*(3f/8f) && angleC >= PI*(1f/8f)){

            }
            if(angleC < PI*(5f/8f) && angleC >= PI*(3f/8f)){
                gameEngine.player.shoot("up")
            }
            if(angleC < PI*(7f/8f) && angleC >= PI*(5f/8f)){

            }
            if(angleC >= PI*(7f/8f) || angleC < PI*(-7f/8f)){
                gameEngine.player.shoot("left")
            }
            if(angleC < PI*(-5f/8f) && angleC >= PI*(-7f/8f)){

            }
            if(angleC < PI*(-3f/8f) && angleC >= PI*(-5f/8f)){
                gameEngine.player.shoot("down")
            }
            if(angleC < PI*(-1f/8f) && angleC >= PI*(-3f/8f)){

            }

        }
    }

    fun updateImages(){
        //update enemy images
        var count = 0
        for(enemy in gameEngine.freezos) {
            enemies[count].x
            enemies[count].x = enemy.getXPosition() - enemy.getWidth()/2f
            enemies[count].y = enemy.getYPosition() - enemy.getHeight()/2f
            tempImageResource = enemy.image
            if (tempImageResource < 0){
                tempImageResource *= -1
                enemies[count].setImageResource(tempImageResource)
                enemies[count].rotationY = 180f
            }else{
                enemies[count].rotationY = 0f
                enemies[count].setImageResource(enemy.image)
            }
            count++
        }
        /* Old method using hardcoded enemy
        enemyImage.x = gameEngine.enemy.getXPosition() - gameEngine.enemy.getWidth()/2f
        enemyImage.y = gameEngine.enemy.getYPosition() - gameEngine.enemy.getHeight()/2f
        tempImageResource = gameEngine.enemy.image
        if (tempImageResource < 0){
            tempImageResource *= -1
            enemyImage.setImageResource(tempImageResource)
            enemyImage.rotationY = 180f
        }else{
            enemyImage.rotationY = 0f
            enemyImage.setImageResource(gameEngine.enemy.image)
        }
        */

        //update player image
        playerImage.x = gameEngine.player.getXPosition() - gameEngine.player.getWidth()/2f
        playerImage.y = gameEngine.player.getYPosition() - gameEngine.player.getHeight()/2f
        tempImageResource = gameEngine.player.image
        if (tempImageResource < 0){
            tempImageResource *= -1
            playerImage.setImageResource(tempImageResource)
            playerImage.rotationY = 180f
        }else{
            playerImage.rotationY = 0f
            playerImage.setImageResource(gameEngine.player.image)
        }

        //update player bullet images
        count = 0
        for (bullet in gameEngine.player.bulletArray){
            if (bullet != null){
                playerBullets[count].setImageResource(bullet.image)
                playerBullets[count].x = bullet.xPosition //+ (playerImage.width/2)
                playerBullets[count].y = bullet.yPosition //+ (playerImage.height/2)
                playerBullets[count].getLayoutParams().width = bullet.getWidth()
                playerBullets[count].getLayoutParams().height = bullet.getHeight()
            }
            count++
        }

        //update player bullet images
        count = 0
        for(enemy in gameEngine.freezos) {
            for (bullet in enemy.bulletArray){
                if (bullet != null){
                    enemyBullets[count].setImageResource(bullet.image)
                    enemyBullets[count].x = bullet.xPosition - bullet.getWidth()/2
                    enemyBullets[count].y = bullet.yPosition - bullet.getHeight()/2
                    enemyBullets[count].getLayoutParams().width = bullet.getWidth()
                    enemyBullets[count].getLayoutParams().height = bullet.getHeight()
                }
                count++
            }
        }
        /* Old method using hardcoded enemy
        count = 0
        for (bullet in gameEngine.enemy.bulletArray){
            if (bullet != null){
                enemyBullets[count].setImageResource(bullet.image)
                enemyBullets[count].x = bullet.xPosition - bullet.getWidth()/2
                enemyBullets[count].y = bullet.yPosition - bullet.getHeight()/2
                enemyBullets[count].getLayoutParams().width = bullet.getWidth()
                enemyBullets[count].getLayoutParams().height = bullet.getHeight()
            }
            count++
        }
        */

        //update Joystick images
        joystickImage.x = joyStickX - joystickImage.width/2
        joystickImage.y = joyStickY - joystickImage.height/2
        joystickImage2.x = joyStick2X - joystickImage2.width/2
        joystickImage2.y = joyStick2Y - joystickImage2.height/2

        //To Delete
        playerImage.setBackgroundColor(Color.rgb(0,200,50))
        enemyImage.setBackgroundColor(Color.rgb(200,0,50))
    }
}
