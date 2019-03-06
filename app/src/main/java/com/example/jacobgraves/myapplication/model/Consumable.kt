package com.example.jacobgraves.myapplication.model

import android.graphics.RectF
import android.widget.ImageView
import com.example.jacobgraves.myapplication.GameController
import kotlinx.android.synthetic.main.game_view.*

abstract class Consumable(var gameController: GameController, x: Float, y: Float){
    var name:String = ""
    var description:String = ""
    var movementSpeed:Float = 0.0f
    var accelerationX:Float = 0.0f
    var accelerationY:Float = 0.0f
    var width:Int = 0
    var height:Int = 0
    var xPosition:Float = 0f
    var yPosition:Float = 0f
    var hitBox: RectF
    abstract var image:Int
    val imageView:ImageView

    init {
        name = "Error"
        description = "Error"
        movementSpeed = 1f
        accelerationX = 0f
        accelerationY = 0f
        width = 20
        height = 20
        xPosition = 0f
        yPosition = 0f

        spawn(x,y)

        hitBox = RectF(xPosition-width/2,yPosition-height/2,xPosition+width/2,yPosition+height/2)

        imageView = ImageView(gameController)
        setupImageView()
    }

    abstract fun giveTo(player: Player)

    fun spawn(x:Float, y:Float){
        xPosition = x
        yPosition = y

        accelerationX = (Math.random().toFloat() - .5f) * 8 * movementSpeed
        accelerationY = -(Math.random().toFloat() - .5f) * 8 * movementSpeed
    }

    fun kill(){
        gameController.runOnUiThread{
            run{
                gameController.constraintLayout.removeView(imageView)
            }
        }
    }

    fun decelerate(){
        accelerationX *= .99f
        accelerationY *= .99f
        if(accelerationX < .1f && accelerationX > -.1f){
            accelerationX = 0f
        }
        if(accelerationY < .1f && accelerationY > -.1f){
            accelerationY = 0f
        }
        xPosition += accelerationX
        yPosition += accelerationY
    }

    fun updateHitbox(){
        hitBox.set(xPosition-width/2,yPosition-height/2,xPosition+width/2,yPosition+height/2)
    }

    fun setupImageView(){
        gameController.runOnUiThread{
            run{
                gameController.constraintLayout.addView(imageView)

                imageView.layoutParams.width = (width * gameController.screenXRatio).toInt()
                imageView.layoutParams.height = (height * gameController.screenYRatio).toInt()
                imageView.x = (xPosition - width/2f) * gameController.screenXRatio
                imageView.y = (yPosition - height/2f) * gameController.screenYRatio

                imageView.setImageResource(image)
                imageView.rotationY = Math.random().toFloat()*360
            }
        }
    }

    fun updateImageView(){
        gameController.runOnUiThread{
            run{
                imageView.layoutParams.width = (width * gameController.screenXRatio).toInt()
                imageView.layoutParams.height = (height * gameController.screenYRatio).toInt()
                imageView.x = (xPosition - width/2f) * gameController.screenXRatio
                imageView.y = (yPosition - height/2f) * gameController.screenYRatio

                imageView.setImageResource(image)
                imageView.rotationY += 5
            }
        }
    }
}