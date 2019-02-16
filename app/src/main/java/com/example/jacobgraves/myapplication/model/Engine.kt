package com.example.jacobgraves.myapplication.model

import com.example.jacobgraves.myapplication.R

class Engine {
    var player: Player
    var frameCount: Int

    constructor(name:String){
        player = Player(name)
        frameCount = 0
    }

    fun update(){
        if (frameCount == 60){
            //println("It's been 60 frames")
            frameCount = 0
        }
        if (frameCount % 10 == 0){
            updateAnimations()
        }
        frameCount += 1

        player.decelerate()
    }

    fun checkHitboxes(){

    }

    fun updateAnimations(){
        if(player.accelerationX > 0){
            player.image = player.moveRightAnimationSet[player.animationCounter]
            player.animationCounter += 1
            if (player.animationCounter == player.moveRightAnimationSet.size){
                player.animationCounter = 0
            }
        }
        if(player.accelerationX < 0){
            player.image = player.moveLeftAnimationSet[player.animationCounter]
            player.animationCounter += 1
            if (player.animationCounter == player.moveLeftAnimationSet.size){
                player.animationCounter = 0
            }
        }
        if(player.accelerationX == 0f){
            player.image = R.drawable.mario_stand
        }
    }
}