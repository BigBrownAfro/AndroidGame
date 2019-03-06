package com.example.jacobgraves.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import com.example.jacobgraves.myapplication.model.Engine
import com.example.jacobgraves.myapplication.model.SoundManager
import kotlinx.android.synthetic.main.game_view.*
import java.util.*
import kotlin.math.*

class GameController : AppCompatActivity() {
    lateinit var gameEngine: Engine

    val timer = Timer()
    val gameTask = GameLoopTask(this)

    lateinit var joystickListener:View.OnTouchListener
    lateinit var joystick2Listener:View.OnTouchListener

    var joystickOriginX = 300f
    var joystickOriginY = 1080f-300f
    var joyStickX = joystickOriginX
    var joyStickY = joystickOriginY
    var joystickMoveRadius = 100f
    var joystickPlayerMoveRadius = 50f

    var joystick2OriginX = 1920f-300f
    var joystick2OriginY = 1080f-300f
    var joyStick2X = joystick2OriginX
    var joyStick2Y = joystick2OriginY
    var joystick2MoveRadius = 100f
    var joystick2PlayerMoveRadius = 50f

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

        runInitSetup()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        println("Did you press the back button?")
        timer.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
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
    }

    fun setupButtons(){

    }

    //Updating------------------------------------------------------------------------------------------
    fun update(){
        gameEngine.update()
        updateGUI()
        movePlayer()
        shootPlayer()
        runOnUiThread(Runnable() {
            run() {
                updateImages()
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
    }
}