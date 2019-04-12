package com.example.jacobgraves.myapplication.model.map

import android.support.constraint.ConstraintLayout
import android.widget.ImageView
import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import kotlinx.android.synthetic.main.game_view.*

abstract class RoomTile(var gameController: GameController, row:Int, col:Int) {
    var image : Int
    var imageView : ImageView
    var tileRow : Int
    var tileCol : Int
    var isWall : Boolean
    var isGround : Boolean
    var isDoor: Boolean
    lateinit var animationSet: IntArray
    var animationCounter: Int

    init{
        tileRow = row
        tileCol = col
        isWall = false
        isGround = false
        isDoor = false

        image = R.drawable.basic_floor
        animationCounter = 0

        imageView = ImageView(gameController)
    }

    abstract fun assignAnimationPictures()

    fun updateAnimations(){
        image = animationSet[animationCounter]
        animationCounter++
        if (animationCounter == animationSet.size){
            animationCounter = 0
        }
    }

    fun setupImageView(){
        gameController.runOnUiThread{
            run{
                gameController.constraintLayout.addView(imageView)
                imageView.layoutParams.width = 64
                imageView.layoutParams.height = 64
                imageView.setImageResource(image)
                imageView.x = (tileCol * 64f + Room.mapX) * gameController.screenXRatio
                imageView.y = (tileRow * 64f + Room.mapY) * gameController.screenYRatio
                if (tileCol == 0){
                    imageView.x -= 4
                }
            }
        }
    }

    fun updateImageView(){
        gameController.runOnUiThread{
            run{
                imageView.setImageResource(image)
            }
        }
    }
}