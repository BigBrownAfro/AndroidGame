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
            if (player.animationCounter >= player.moveRightAnimationSet.size){
                player.animationCounter = 0
            }
            player.image = player.moveRightAnimationSet[player.animationCounter]
            player.lastDirection ="right"
            player.animationCounter += 1
        }else
        if(player.accelerationX < 0){
            if (player.animationCounter >= player.moveLeftAnimationSet.size){
                player.animationCounter = 0
            }
            player.image = player.moveLeftAnimationSet[player.animationCounter]
            player.lastDirection ="left"
            player.animationCounter += 1
        }else
        if(player.accelerationY < 0){
            if (player.animationCounter >= player.moveUpAnimationSet.size){
                player.animationCounter = 0
            }
            player.image = player.moveUpAnimationSet[player.animationCounter]
            player.lastDirection ="up"
            player.animationCounter += 1
        }else
        if(player.accelerationY > 0){
            if (player.animationCounter >= player.moveDownAnimationSet.size){
                player.animationCounter = 0
            }
            player.image = player.moveDownAnimationSet[player.animationCounter]
            player.lastDirection ="down"
            player.animationCounter += 1
        }else
        if(player.accelerationX == 0f){
            if(player.lastDirection.equals("up")){
                player.image = R.drawable.mario_run_up_1
            }else if(player.lastDirection.equals("down")){
                player.image = R.drawable.mario_face_forward
            }else if(player.lastDirection.equals("left")){
                player.image = R.drawable.mario_stand
            }else if(player.lastDirection.equals("right")){
                player.image = R.drawable.mario_stand * (-1)
            }else{
                player.image = R.drawable.mario_peace
            }
        }
    }
}