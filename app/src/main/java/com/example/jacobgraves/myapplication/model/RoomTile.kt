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
    var isDoor: Boolean
    lateinit var animationSet: IntArray
    var animationCounter: Int

    init{
        tileX = 32
        tileY = 32
        isWall = false
        isGround = false
        isDoor = false
        animationCounter = 0
        assignAnimationPictures()

        image = R.drawable.basic_floor
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

    abstract fun setupImageView()

    abstract fun updateImageView()
}