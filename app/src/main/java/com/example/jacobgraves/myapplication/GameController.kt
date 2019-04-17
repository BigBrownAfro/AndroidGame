package com.example.jacobgraves.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import com.example.jacobgraves.myapplication.model.Engine
import com.example.jacobgraves.myapplication.model.SoundManager
import kotlinx.android.synthetic.main.game_view.*
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
    var pauseButtonX = 1920f-100f
    var pauseButtonY = 10f
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

    var useTouchControls:Boolean = true
    var keyUp:Boolean = false
    var keyDown:Boolean = false
    var keyLeft:Boolean = false
    var keyRight:Boolean = false

    var lastKeyUp:Long = 0
    var lastKeyDown:Long = 0
    var lastKeyLeft:Long = 0
    var lastKeyRight:Long = 0

    var shootUp:Boolean = false
    var shootDown:Boolean = false
    var shootLeft:Boolean = false
    var shootRight:Boolean = false

    var lastshootUp:Long = 0
    var lastshootDown:Long = 0
    var lastshootLeft:Long = 0
    var lastshootRight:Long = 0


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

    /*override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_W){
            keyUp = true
            lastKeyUp = System.currentTimeMillis()
            println("up pressed")
        }
        if (keyCode == KeyEvent.KEYCODE_S){
            keyDown = true
            lastKeyDown = System.currentTimeMillis()
        }
        if (keyCode == KeyEvent.KEYCODE_A){
            keyLeft = true
            lastKeyLeft = System.currentTimeMillis()
        }
        if (keyCode == KeyEvent.KEYCODE_D){
            keyRight= true
            lastKeyRight = System.currentTimeMillis()
        }
        if(keyUp || keyDown || keyLeft || keyRight){
            useTouchControls = false
            joystickImage.alpha = 0f
            joystickImage2.alpha = 0f
        }else{
            useTouchControls = true
            joystickImage.alpha = .4f
            joystickImage2.alpha = .4f
        }
        return super.onKeyDown(keyCode, event)
    }*/

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_W){
            keyUp = true
            lastKeyUp = System.currentTimeMillis()
        }
        if (keyCode == KeyEvent.KEYCODE_S){
            keyDown = true
            lastKeyDown = System.currentTimeMillis()
        }
        if (keyCode == KeyEvent.KEYCODE_A){
            keyLeft = true
            lastKeyLeft = System.currentTimeMillis()
        }
        if (keyCode == KeyEvent.KEYCODE_D){
            keyRight= true
            lastKeyRight = System.currentTimeMillis()
        }

        if (keyCode == KeyEvent.KEYCODE_DPAD_UP){
            shootUp = true
            lastshootUp = System.currentTimeMillis()
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN){
            shootDown = true
            lastshootDown = System.currentTimeMillis()
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT){
            shootLeft = true
            lastshootLeft = System.currentTimeMillis()
        }
        if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT){
            shootRight = true
            lastshootRight = System.currentTimeMillis()
        }

        if(keyUp || keyDown || keyLeft || keyRight){
            useTouchControls = false
            //joystickImage.alpha = 0f
            //joystickImage2.alpha = 0f
        }else{
            useTouchControls = true
            //joystickImage.alpha = .4f
            //joystickImage2.alpha = .4f
        }
        return super.onKeyUp(keyCode, event)
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
            useTouchControls = true
            joystickImage.alpha = .4f
            joystickImage2.alpha = .4f
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
        pauseButton.getLayoutParams().width = (70 * screenXRatio).toInt()
        pauseButton.getLayoutParams().height = (70 * screenYRatio).toInt()
        pauseButton.bringToFront()
    }

    fun setupButtons(){

        pauseButton.x = pauseButtonX * screenXRatio
        pauseButton.y = pauseButtonY * screenYRatio
        pauseButton.setOnClickListener {
            pauseButtonClicked()
        }

        resumeButton.x = (resumeButtonX) * screenXRatio
        resumeButton.y = (resumeButtonY) * screenYRatio
        resumeButton.isEnabled = false
        resumeButton.visibility = View.INVISIBLE
        resumeButton.setOnClickListener {
            resumeButtonClicked()
        }

        quitButton.x = (quitButtonX) * screenXRatio
        quitButton.y = (quitButtonY) * screenYRatio
        quitButton.isEnabled = false
        quitButton.visibility = View.INVISIBLE
        quitButton.setOnClickListener {
            quitButtonClicked()
        }
    }

    private fun pauseButtonClicked() {
        resumeButton.x = (resumeButtonX) * screenXRatio
        resumeButton.y = (resumeButtonY) * screenYRatio
        quitButton.x = (quitButtonX) * screenXRatio
        quitButton.y = (quitButtonY) * screenYRatio
        isPaused = !isPaused
        if (isPaused){
            resumeButton.isEnabled = true
            resumeButton.visibility = View.VISIBLE
            quitButton.isEnabled = true
            quitButton.visibility = View.VISIBLE
            MainMenuController.mediaPlayer.pause()
        }else{
            resumeButton.isEnabled = false
            resumeButton.visibility = View.INVISIBLE
            quitButton.isEnabled = false
            quitButton.visibility = View.INVISIBLE
            MainMenuController.mediaPlayer.start()
        }
    }
    private fun resumeButtonClicked(){
        isPaused = false
        resumeButton.isEnabled = false
        resumeButton.visibility = View.INVISIBLE
        quitButton.isEnabled = false
        quitButton.visibility = View.INVISIBLE
        MainMenuController.mediaPlayer.start()
    }

    private fun quitButtonClicked(){
        MainMenuController.mediaPlayer.start()
        onBackPressed()
        //val intent = Intent(this, MainMenuController::class.java)
        //this.startActivity(intent)
    }

    //Updating------------------------------------------------------------------------------------------
    fun update(){
        if(!isPaused) {
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
            gameEngine.hud.updateViews()
            //updatePauseMenu()
        }
    }

    fun updateGUI(){

    }

    fun updatePauseMenu(){

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

        if(keyRight && !keyUp && !keyLeft && !keyDown){
            gameEngine.player.move(0f)
        }
        if(keyRight && keyUp && !keyLeft && !keyDown){
            gameEngine.player.move((PI/4).toFloat())
        }
        if(!keyRight && keyUp && !keyLeft && !keyDown){
            gameEngine.player.move((PI/2).toFloat())
        }
        if(!keyRight && keyUp && keyLeft && !keyDown){
            gameEngine.player.move((3*PI/4).toFloat())
        }
        if(!keyRight && !keyUp && keyLeft && !keyDown){
            gameEngine.player.move((PI).toFloat())
        }
        if(!keyRight && !keyUp && keyLeft && keyDown){
            gameEngine.player.move((-3*PI/4).toFloat())
        }
        if(!keyRight && !keyUp && !keyLeft && keyDown){
            gameEngine.player.move((-PI/2).toFloat())
        }
        if(keyRight && !keyUp && !keyLeft && keyDown){
            gameEngine.player.move((-PI/4).toFloat())
        }

        if(shootRight && !shootUp && !shootLeft && !shootDown){
            gameEngine.player.shoot(0f)
        }
        if(shootRight && shootUp && !shootLeft && !shootDown){
            gameEngine.player.shoot((PI/4).toFloat())
        }
        if(!shootRight && shootUp && !shootLeft && !shootDown){
            gameEngine.player.shoot((PI/2).toFloat())
        }
        if(!shootRight && shootUp && shootLeft && !shootDown){
            gameEngine.player.shoot((3*PI/4).toFloat())
        }
        if(!shootRight && !shootUp && shootLeft && !shootDown){
            gameEngine.player.shoot((PI).toFloat())
        }
        if(!shootRight && !shootUp && shootLeft && shootDown){
            gameEngine.player.shoot((-3*PI/4).toFloat())
        }
        if(!shootRight && !shootUp && !shootLeft && shootDown){
            gameEngine.player.shoot((-PI/2).toFloat())
        }
        if(shootRight && !shootUp && !shootLeft && shootDown){
            gameEngine.player.shoot((-PI/4).toFloat())
        }

        var time = System.currentTimeMillis()
        if(time - lastKeyUp > 400){
            keyUp = false
        }
        if(time - lastKeyDown > 400){
            keyDown = false
        }
        if(time - lastKeyLeft > 400){
            keyLeft = false
        }
        if(time - lastKeyRight > 400){
            keyRight = false
        }

        if(time - lastshootUp > 200){
            shootUp = false
        }
        if(time - lastshootDown > 200){
            shootDown = false
        }
        if(time - lastshootLeft > 200){
            shootLeft = false
        }
        if(time - lastshootRight > 200){
            shootRight = false
        }
        /*
        if (useTouchControls){
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
        }else{
            if(keyRight && !keyUp && !keyLeft && !keyDown){
                gameEngine.player.move(0f)
            }
            if(keyRight && keyUp && !keyLeft && !keyDown){
                gameEngine.player.move((PI/4).toFloat())
            }
            if(!keyRight && keyUp && !keyLeft && !keyDown){
                gameEngine.player.move((PI/2).toFloat())
            }
            if(!keyRight && keyUp && keyLeft && !keyDown){
                gameEngine.player.move((3*PI/4).toFloat())
            }
            if(!keyRight && !keyUp && keyLeft && !keyDown){
                gameEngine.player.move((PI).toFloat())
            }
            if(!keyRight && !keyUp && keyLeft && keyDown){
                gameEngine.player.move((-3*PI/4).toFloat())
            }
            if(!keyRight && !keyUp && !keyLeft && keyDown){
                gameEngine.player.move((-PI/2).toFloat())
            }
            if(keyRight && !keyUp && !keyLeft && keyDown){
                gameEngine.player.move((-PI/4).toFloat())
            }

            var time = System.currentTimeMillis()
            if(time - lastKeyUp > 400){
                keyUp = false
            }
            if(time - lastKeyDown > 400){
                keyDown = false
            }
            if(time - lastKeyLeft > 400){
                keyLeft = false
            }
            if(time - lastKeyRight > 400){
                keyRight = false
            }
        }*/
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
        pauseButton.x = (pauseButtonX) * screenXRatio
        pauseButton.y = (pauseButtonY) * screenYRatio
    }
}