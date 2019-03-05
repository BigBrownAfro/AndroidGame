package com.example.jacobgraves.myapplication.model

import android.support.constraint.ConstraintLayout
import android.widget.ImageView
import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import kotlinx.android.synthetic.main.game_view.*

abstract class RoomTile(var gameController: GameController) {
    var image : Int
    var imageView : ImageView
    var tileX : Int
    var tileY : Int
    var isWall : Boolean
    var isGround : Boolean
    lateinit var animationSet: IntArray
    var animationCounter: Int

    init{
        tileX = 0
        tileY = 0
        isWall = false
        isGround = false
        animationCounter = 0
        assignAnimationPictures()

        image = R.drawable.isaac
        imageView = ImageView(gameController)
        setupImageView()
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
                imageView.setImageResource(image)
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