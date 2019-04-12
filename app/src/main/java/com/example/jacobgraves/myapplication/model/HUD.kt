package com.example.jacobgraves.myapplication.model

import android.graphics.Color
import android.media.Image
import android.provider.ContactsContract
import android.widget.ImageView
import android.widget.TextView
import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import kotlinx.android.synthetic.main.game_view.*
import org.w3c.dom.Text
import kotlin.math.min

class HUD(var gameController: GameController) {
    var healthImage:Int
    val healthImageView: ImageView

    val gameStart:Long
    var time:Int
    var minutes:String
    var seconds:String
    val timeTextView:TextView

    var coinsImage:Int
    val coinsImageView:ImageView
    var coinsTextView: TextView

    var otherImage:Int
    val otherImageView:ImageView
    val otherTextView: TextView

    init {
        healthImage = R.drawable.health6
        healthImageView = ImageView(gameController)

        gameStart = System.currentTimeMillis()
        time = 0
        minutes = ""
        seconds = ""
        timeTextView = TextView(gameController)

        coinsImage = R.drawable.coin
        coinsImageView = ImageView(gameController)
        coinsTextView = TextView(gameController)

        otherImage = R.drawable.arrow
        otherImageView = ImageView(gameController)
        otherTextView = TextView(gameController)

        setupViews()
    }

    fun setupViews(){
        gameController.runOnUiThread{
            run{
                gameController.constraintLayout.addView(healthImageView)
                gameController.constraintLayout.addView(timeTextView)
                gameController.constraintLayout.addView(coinsImageView)
                gameController.constraintLayout.addView(coinsTextView)
                gameController.constraintLayout.addView(otherImageView)
                gameController.constraintLayout.addView(otherTextView)

                healthImageView.setImageResource(healthImage)
                healthImageView.layoutParams.width = (250 * gameController.screenXRatio).toInt()
                healthImageView.layoutParams.height = (90 * gameController.screenYRatio).toInt()
                healthImageView.x = 20f * gameController.screenXRatio
                healthImageView.y = 10f * gameController.screenYRatio

                timeTextView.text = String.format("%2d",time/60) + ":" + String.format("%2d",time%60)
                timeTextView.layoutParams.width = (400 * gameController.screenXRatio).toInt()
                timeTextView.layoutParams.height = (100 * gameController.screenYRatio).toInt()
                timeTextView.x = 880f * gameController.screenXRatio
                timeTextView.y = 38f * gameController.screenYRatio
                timeTextView.textSize = 20f
                timeTextView.setTextColor(Color.LTGRAY)

                coinsImageView.setImageResource(coinsImage)
                coinsImageView.layoutParams.width = (31 * gameController.screenXRatio).toInt()
                coinsImageView.layoutParams.height = (40 * gameController.screenYRatio).toInt()
                coinsImageView.x = 1700f * gameController.screenXRatio
                coinsImageView.y = 300f * gameController.screenYRatio

                coinsTextView.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
                coinsTextView.text = ""
                coinsTextView.layoutParams.width = (95 * gameController.screenXRatio).toInt()
                coinsTextView.layoutParams.height = coinsImageView.layoutParams.height
                coinsTextView.x = coinsImageView.x + coinsImageView.layoutParams.width + 10 * gameController.screenXRatio
                coinsTextView.y = coinsImageView.y + -5 * gameController.screenYRatio
                coinsTextView.setTextColor(Color.LTGRAY)

                otherImageView.setImageResource(otherImage)
                otherImageView.layoutParams.width = (23 * gameController.screenXRatio).toInt()
                otherImageView.layoutParams.height = (40 * gameController.screenYRatio).toInt()
                otherImageView.x = coinsImageView.x + (coinsImageView.layoutParams.width-otherImageView.layoutParams.width)/2 * gameController.screenXRatio
                otherImageView.y = coinsImageView.y + coinsImageView.layoutParams.height + 10 * gameController.screenYRatio

                otherTextView.textAlignment = TextView.TEXT_ALIGNMENT_TEXT_START
                otherTextView.text = ""
                otherTextView.layoutParams.width = (95 * gameController.screenXRatio).toInt()
                otherTextView.layoutParams.height = otherImageView.layoutParams.height
                otherTextView.x = coinsTextView.x
                otherTextView.y = otherImageView.y + -5 * gameController.screenYRatio
                otherTextView.setTextColor(Color.LTGRAY)
            }
        }
    }

    fun updateViews(){
        gameController.runOnUiThread{
            run{
                when(gameController.gameEngine.player.getHealthValue()){
                    6 -> healthImage = R.drawable.health6
                    5 -> healthImage = R.drawable.health5
                    4 -> healthImage = R.drawable.health4
                    3 -> healthImage = R.drawable.health3
                    2 -> healthImage = R.drawable.health2
                    1 -> healthImage = R.drawable.health1
                    0 -> healthImage = R.drawable.health0
                }
                healthImageView.setImageResource(healthImage)

                time = ((System.currentTimeMillis()-gameStart) / 1000).toInt()
                if(time/60 < 10){
                    minutes = "0" + time/60
                }else{
                    minutes = (time/60).toString()
                }
                if(time%60 < 10){
                    seconds = "0" + time%60
                }else{
                    seconds = (time%60).toString()
                }
                timeTextView.text = minutes + ":" + seconds

                coinsTextView.text = gameController.gameEngine.player.coins.toString()

                otherTextView.text = String.format("%.2f",gameController.gameEngine.player.getMovementSpeed())
            }
        }
    }
}