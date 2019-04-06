package com.example.jacobgraves.myapplication

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import com.example.jacobgraves.myapplication.model.Engine
import com.example.jacobgraves.myapplication.model.SoundManager
import kotlinx.android.synthetic.main.game_view.*
import kotlinx.android.synthetic.main.main_menu_view.*
import java.lang.Exception
import java.util.*
import kotlin.math.*

class GameController : AppCompatActivity() {
    lateinit var gameEngine: Engine

    var timer = Timer()
    val gameTask = GameLoopTask(this)

    lateinit var joystickListener:View.OnTouchListener
    lateinit var joystick2Listener:View.OnTouchListener

    var isPaused = false
    var joystickOriginX = 200f
    var joystickOriginY = 1080f-300f
    var joyStickX = joystickOriginX
    var joyStickY = joystickOriginY
    var pauseButtonX = 1920f-200f
    var pauseButtonY = 1080f-1000f
    var resumeButtonX = 1920f/2f
    var resumeButtonY = 1080f - 450f
    var quitButtonX = 1920f/2f
    var quitButtonY = 1080f - 650f

    var joystickMoveRadius = 100f
    var joystickPlayerMoveRadius = 30f

    var joystick2OriginX = 1920f-200f
    var joystick2OriginY = 1080f-300f
    var joyStick2X = joystick2OriginX
    var joyStick2Y = joystick2OriginY
    var joystick2MoveRadius = 100f
    var joystick2PlayerMoveRadius = 30f

    var xDifference:Float = 0f
    var yDifference:Float = 0f
    var hypotenuse:Float = 0f
    var angleC:Float = 0f

    var screenXRatio:Float = 0f
    var screenYRatio:Float = 0f

    //Sounds
    lateinit var soundManager:SoundManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_view)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        /*
        if(MainMenuController.gameEngine != null){
            println("Attempting to use Old Engine")
            runReSetup()
        }else{
            println("No Engine in there")
            runInitSetup()
        }*/

        runInitSetup()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        println("Did you press the back button?")
        timer.cancel()
    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
        println("Screen Paused")
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
        println("Screen Stopped")
    }

    override fun onDestroy() {
        timer.cancel()
        //gameEngine.destroyImages()
        //MainMenuController.gameEngine = gameEngine
        super.onDestroy()
        println("Screen Destroyed")
    }

    override fun onRestart() {
        super.onRestart()
        try{
            timer.cancel()
        }catch (e:Exception){
            println("tried to cancel timer" + e)
        }
        timer = Timer()
        timer.scheduleAtFixedRate(gameTask,Date(),16)
        println("Sreen Restarted")
    }



    //Setup------------------------------------------------------------------------------------------
    fun runInitSetup() {
        var displayMetrics = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        screenXRatio = displayMetrics.widthPixels.toFloat() / 1920f
        screenYRatio = displayMetrics.heightPixels.toFloat() / 1080f

        gameEngine = Engine(this, intent.getStringExtra("name"))
        setupImages()
        setupButtons()
        setupListeners()
        setupSounds()

        println("Init Game Setup Complete")

        timer.scheduleAtFixedRate(gameTask,Date(),16)
    }

    /*
    fun runReSetup(){
        var displayMetrics = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        screenXRatio = displayMetrics.widthPixels.toFloat() / 1920f
        screenYRatio = displayMetrics.heightPixels.toFloat() / 1080f

        gameEngine = MainMenuController.gameEngine!!
        gameEngine.refreshGameController(this)
        setupImages()
        setupButtons()
        setupListeners()
        setupSounds()

        println("Game Re-Setup Complete")

        timer.scheduleAtFixedRate(gameTask,Date(),16)
    }*/

    fun setupSounds(){
        soundManager = SoundManager(this)
    }

    fun setupListeners(){
        joystickOriginX *= screenXRatio
        joystickOriginY *= screenYRatio
        joyStickX = joystickOriginX
        joyStickY = joystickOriginY

        joystick2OriginX *= screenXRatio
        joystick2OriginY *= screenYRatio
        joyStick2X = joystick2OriginX
        joyStick2Y = joystick2OriginY

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
        //Joysticks images
        joystickImage.alpha = .4f
        joystickImage2.alpha = .4f
        joystickImage.getLayoutParams().width = (140 * screenXRatio).toInt()
        joystickImage.getLayoutParams().height = (140 * screenYRatio).toInt()
        joystickImage2.getLayoutParams().width = (140 * screenXRatio).toInt()
        joystickImage2.getLayoutParams().height = (140 * screenYRatio).toInt()
        pauseButton.getLayoutParams().width = (140 * screenXRatio).toInt()
        pauseButton.getLayoutParams().height = (140 * screenYRatio).toInt()
        pauseButton.bringToFront()
    }

    fun setupButtons(){

        pauseButton.setOnClickListener {
            pauseButtonClicked()

        }

        resumeButton.setOnClickListener {
            resumeButtonClicked()
        }

        quitButton.setOnClickListener {
            quitButtonClicked()
        }
    }

    private fun pauseButtonClicked() {
        if(isPaused == false){
            isPaused = true
        }else{
            isPaused = false
        }


    }
    private fun resumeButtonClicked(){
        isPaused = false
    }

    private fun quitButtonClicked(){
        val intent = Intent(this, MainMenuController::class.java)
        this.startActivity(intent)
    }

    //Updating------------------------------------------------------------------------------------------
    fun update(){
        if(isPaused==false) {
            gameEngine.update()
            updateGUI()
            movePlayer()
            shootPlayer()
            runOnUiThread(Runnable() {
                run() {
                    updateImages()
                }
            });
        }else{
            updatePauseMenu()
        }
    }

    fun updateGUI(){
        resumeButton.x = 5000f
        resumeButton.y = 5000f
        quitButton.x = 5000f
        quitButton.y = 5000f
    }
    fun updatePauseMenu(){

        resumeButton.x = 1920f-1150f
        resumeButton.y = 1080f - 700f
        quitButton.x = 1920f-1150f
        quitButton.y = 1080f - 500f
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
                gameEngine.player.shoot(0f)
            }
            if(angleC < PI*(3f/8f) && angleC >= PI*(1f/8f)){
                gameEngine.player.shoot(PI.toFloat()/4f)
            }
            if(angleC < PI*(5f/8f) && angleC >= PI*(3f/8f)){
                gameEngine.player.shoot(PI.toFloat()/2f)
            }
            if(angleC < PI*(7f/8f) && angleC >= PI*(5f/8f)){
                gameEngine.player.shoot(3f*PI.toFloat()/4f)
            }
            if(angleC >= PI*(7f/8f) || angleC < PI*(-7f/8f)){
                gameEngine.player.shoot(PI.toFloat())
            }
            if(angleC < PI*(-5f/8f) && angleC >= PI*(-7f/8f)){
                gameEngine.player.shoot(-3f*PI.toFloat()/4f)
            }
            if(angleC < PI*(-3f/8f) && angleC >= PI*(-5f/8f)){
                gameEngine.player.shoot(-PI.toFloat()/2f)
            }
            if(angleC < PI*(-1f/8f) && angleC >= PI*(-3f/8f)){
                gameEngine.player.shoot(-PI.toFloat()/4f)
            }
        }
    }

    fun updateImages(){
        //update Joystick images
        joystickImage.x = (joyStickX - joystickImage.width/2)
        joystickImage.y = (joyStickY - joystickImage.height/2)
        joystickImage2.x = (joyStick2X - joystickImage2.width/2)
        joystickImage2.y = (joyStick2Y - joystickImage2.height/2)
        pauseButton.x = (pauseButtonX - pauseButton.width/2)
        pauseButton.y = (pauseButtonY - pauseButton.height/2)
    }
}