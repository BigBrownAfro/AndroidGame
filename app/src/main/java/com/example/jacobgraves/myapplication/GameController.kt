package com.example.jacobgraves.myapplication

import android.graphics.Color
import android.graphics.RectF
import android.os.Bundle
import android.os.PersistableBundle
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
    lateinit var joystick2Listener:View.OnTouchListener
    var tempImageResource = 0
    var healthImageTemp = 0
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
    var lastLoopStart:Long = 0
    var xDifference:Float = 0f
    var yDifference:Float = 0f
    var hypotenuse:Float = 0f
    var angleC:Float = 0f
    //val constraintLayout = findViewById(R.id.constraintLayout) as ConstraintLayout
    var count = 0

    //timing
    var start1 :Long = 0
    var start2 :Long = 0
    var start3 :Long = 0
    var dur1 :Long = 0
    var dur2 :Long = 0
    var dur3 :Long = 0
    var buffer :Long = 0
    var end1 :Long = 0
    var end3 :Long = 0
    var longest1:Long = 0
    var longest2:Long = 0
    var longest3:Long = 0


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
        // I don't want to do this for you, so I'll leave a note here.
        // I figured it out, so I'll give you some hints.
        // Don't use timer.schedule. Use timer.scheduleAtFixedRate.
        // You'll need to pass in a TimerTask, which is abstract so
        // You'll need to make a new class, (I called mine GameTask)
        // and pass a lambda function to call inside the 'run' method.
        // use the code you have here as the lambda function, but make 
        // sure to put it all inside runOnUiThread { ... } 
        // If you have questions, we can talk about it in class!
        timer.schedule(1,16) {//60 frames per second
            start2 = System.currentTimeMillis()
            dur2 = start2 - start1;
            if(dur2 > longest2 && start1 != buffer){
                longest2 = dur2
            }
            start1 = System.currentTimeMillis()
            update()
            end1 = System.currentTimeMillis()
            dur1 = end1-start1
            if(dur1 > longest1){
                longest1 = dur1
            }
        }

        /*
        timer.schedule(1,16) {//60 frames per second
            runOnUiThread(Runnable() {
                run() {
                    start2 = System.currentTimeMillis()
                    if (start2 - start > longest && start > buffer){
                        longest = start2 - start
                    }
                    frameTimeText.text = "frame space: " + (start2 - start) + "\nLongest: " + longest
                    start = System.currentTimeMillis()
                    update()
                    end = System.currentTimeMillis()
                    if (end - start > longest2 && start > buffer){
                        longest2 = end - start
                    }
                    frameTimeText2.text = "update time: " + (end - start) + "\nLongest: " + longest2
                }
            });
        }
         */

    }

    fun setupListeners(){
        joystickListener = View.OnTouchListener(function = { view, motionEvent ->

            if (motionEvent.action == MotionEvent.ACTION_MOVE) {

                joyStickX = motionEvent.rawX
                joyStickY = motionEvent.rawY

                xDifference = motionEvent.rawX - joystickOriginX
                yDifference = joystickOriginY - motionEvent.rawY
                hypotenuse = hypot(xDifference, yDifference)

                if(hypotenuse > joystickMoveRadius){
                    angleC = acos(xDifference/hypotenuse)

                    if(joyStickY < joystickOriginY){
                    }else{
                        angleC *= (-1f)
                    }

                    joyStickX = joystickOriginX + joystickMoveRadius * cos(angleC)
                    joyStickY = joystickOriginY - joystickMoveRadius * sin(angleC)
                }

                joystickImage.x = joyStickX - joystickImage.width/2
                joystickImage.y = joyStickY - joystickImage.height/2

            }else if(motionEvent.action == MotionEvent.ACTION_UP){
                joyStickX = joystickOriginX
                joyStickY = joystickOriginY
                joystickImage.x = joyStickX - joystickImage.width/2
                joystickImage.y = joyStickY - joystickImage.height/2
            }
            true
        })

        joystick2Listener = View.OnTouchListener(function = { view, motionEvent ->

            if (motionEvent.action == MotionEvent.ACTION_MOVE) {

                joyStick2X = motionEvent.rawX
                joyStick2Y = motionEvent.rawY

                xDifference = motionEvent.rawX - joystick2OriginX
                yDifference = joystick2OriginY - motionEvent.rawY
                hypotenuse = hypot(xDifference, yDifference)

                if(hypotenuse > joystick2MoveRadius){
                    angleC = acos(xDifference/hypotenuse)

                    if(joyStick2Y < joystick2OriginY){
                    }else{
                        angleC *= (-1f)
                    }

                    joyStick2X = joystick2OriginX + joystick2MoveRadius * cos(angleC)
                    joyStick2Y = joystick2OriginY - joystick2MoveRadius * sin(angleC)
                }

                joystickImage2.x = joyStick2X - joystickImage2.width/2
                joystickImage2.y = joyStick2Y - joystickImage2.height/2

            }else if(motionEvent.action == MotionEvent.ACTION_UP){
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

        count = 0
        for(enemy in gameEngine.freezos) {
            enemies[count].setImageResource(enemy.image)
            enemies[count].getLayoutParams().width = enemy.getWidth()
            enemies[count].getLayoutParams().height = enemy.getHeight()
            enemies[count].x = enemy.getXPosition()
            enemies[count].y = enemy.getYPosition()
            count ++
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
        playerBullets = Array(500){ImageView(this)}
        for (i in playerBullets){
            constraintLayout.addView(i)
        }
        enemyBullets = Array(1000){ImageView(this)}
        for (i in enemyBullets){
            constraintLayout.addView(i)
            /*
            i.setImageResource(R.drawable.bullet)
            i.x = -500f
            i.y = -500f
            i.getLayoutParams().width = 1
            i.getLayoutParams().height = 1
            */
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
    fun gameLoop(){ //Doesn't Run
        var run = true
        while(run){
            if(System.currentTimeMillis() - lastLoopStart >= 16){
                lastLoopStart = System.currentTimeMillis()
                update()
            }else{
                Thread.sleep(lastLoopStart + 16 - System.currentTimeMillis())
            }
        }
    }

    fun update(){
        gameEngine.update()
        updateGUI()
        movePlayer()
        shootPlayer()
        runOnUiThread(Runnable() {
            run() {
                start3 = System.currentTimeMillis()
                updateImages()
                frameTimeText.text = "Update time: " + (dur1) + "\nLongest: " + longest1
                frameTimeText2.text = "frame space: " + (dur2) + "\nLongest: " + longest2
                end3 = System.currentTimeMillis()
                dur3 = end3 - start3
                if(dur3 > longest3){
                    longest3 = dur3
                }
                frameTimeText3.text = "Update Image Time: " + (dur3) + "\nLongest: " + longest3
            }
        });
    }

    fun updateGUI(){

    }

    fun movePlayer(){

        xDifference = joyStickX - joystickOriginX
        yDifference = joystickOriginY - joyStickY
        hypotenuse = hypot(xDifference, yDifference)

        if(hypotenuse > joystickPlayerMoveRadius){
            angleC = acos(xDifference/hypotenuse)

            if(joyStickY > joystickOriginY){
                angleC *= (-1f)
            }

            gameEngine.player.move(angleC)
        }
    }

    fun shootPlayer(){
        xDifference = joyStick2X - joystick2OriginX
        yDifference = joystick2OriginY - joyStick2Y
        hypotenuse = hypot(xDifference, yDifference)

        if(hypotenuse > joystick2PlayerMoveRadius){
            angleC = acos(xDifference/hypotenuse)

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
        count = 0
        for(enemy in gameEngine.freezos) {
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

        //update enemy bullet images
        count = 0
        for(enemy in gameEngine.freezos) {
            for (bullet in enemy.bulletArray){
                if (bullet != null && bullet.isAlive){
                    enemyBullets[count].setImageResource(bullet.image)
                    enemyBullets[count].x = bullet.xPosition - bullet.getWidth()/2
                    enemyBullets[count].y = bullet.yPosition - bullet.getHeight()/2
                    enemyBullets[count].getLayoutParams().width = bullet.getWidth()
                    enemyBullets[count].getLayoutParams().height = bullet.getHeight()

                }
                /*
                if(bullet != null && !bullet.isAlive){
                    count--
                }*/
                count++
            }
        }

        //update Joystick images
        joystickImage.x = joyStickX - joystickImage.width/2
        joystickImage.y = joyStickY - joystickImage.height/2
        joystickImage2.x = joyStick2X - joystickImage2.width/2
        joystickImage2.y = joyStick2Y - joystickImage2.height/2

        //update Healthbar Images
        healthImageTemp = gameEngine.player.playerHealthImage
        healthImage.setImageResource(healthImageTemp)

        //To Delete
        playerImage.setBackgroundColor(Color.rgb(0,200,50))
        //enemyImage.setBackgroundColor(Color.rgb(200,0,50))
    }
}
