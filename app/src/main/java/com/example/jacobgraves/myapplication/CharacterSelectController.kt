package com.example.jacobgraves.myapplication

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.character_select_view.*
import java.util.*

class CharacterSelectController : AppCompatActivity() {
    var timer = Timer()
    val task = CharacterSelectTask(this)

    var name = ""
    lateinit var player1Image: ImageView
    lateinit var player1AnimationSet: IntArray
    var player1Counter = 0

    var screenXRatio:Float = 0f
    var screenYRatio:Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_select_view)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        runSetup()
    }

    fun runSetup(){
        var displayMetrics = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics)
        screenXRatio = displayMetrics.widthPixels.toFloat() / 1920f
        screenYRatio = displayMetrics.heightPixels.toFloat() / 1080f
        println(screenXRatio * 1920f)

        configureButtons()
        setupCharacterImages()

        timer.scheduleAtFixedRate(task,Date(),200)

        println("Setup Complete in character select")
    }

    fun configureButtons(){
        val buttonWidth = 600
        val buttonHeight = 200
        characterSelectButton.layoutParams.width = buttonWidth
        characterSelectButton.layoutParams.height = buttonHeight
        characterSelectButton.x = (1920f/2f - buttonWidth/2) * screenXRatio
        characterSelectButton.y = (1080f - (buttonHeight + 100)) * screenYRatio
        characterSelectButton.setOnClickListener {
            val intent = Intent(this, GameController::class.java)
            name = "Reggie"
            intent.putExtra("name", name)
            this.startActivity(intent)
        }
    }

    fun setupCharacterImages(){
        player1Image = imageView0
        player1Image.setImageResource(R.drawable.player_1_dance_1)
        //player1Image.setBackgroundColor(Color.RED)
        player1Image.layoutParams.width= 400
        player1Image.layoutParams.height = 400
        player1Image.x = (1920f/2f - player1Image.layoutParams.width/2) * screenXRatio
        player1Image.y = (1080f/2f - player1Image.layoutParams.height/2) * screenYRatio
        //println(screenXRatio)
        player1AnimationSet = IntArray(4)
        player1AnimationSet[0] = R.drawable.player_1_dance_2
        player1AnimationSet[1] = R.drawable.player_1_dance_3
        player1AnimationSet[2] = R.drawable.player_1_dance_1
        player1AnimationSet[3] = R.drawable.player_1_dance_3
    }

    fun update(){
        runOnUiThread(Runnable() {
            run() {
                updateAnimations()
            }
        });
    }

    fun updateAnimations(){
        //player 1 animations
        if (player1Counter < player1AnimationSet.size){
            player1Image.setImageResource(player1AnimationSet[player1Counter])
        }else {
            player1Counter = 0
            player1Image.setImageResource(player1AnimationSet[player1Counter])
        }
        player1Counter++
    }
}