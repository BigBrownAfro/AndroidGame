package com.example.jacobgraves.myapplication.model.guns

import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import com.example.jacobgraves.myapplication.model.Bullet
import java.util.*

class Shotgun(gameController: GameController): Gun(gameController) {
    init {
        setMaxAmmo(50)
        setCurrentAmmo(50)
        maxReload = 15
        setAccuracy(20)
        isEquipped = true
        isPickedUp = true

        assignImages()
    }

    override fun assignImages(){
        image = R.drawable.shotgun_right
        shootAnimationSet = IntArray(3)
        //gameController.gameEngine.player.moveRightAnimationSet[0] = R.drawable.testgun
        // gameController.gameEngine.player.moveRightAnimationSet[1] = R.drawable.testgunfire
        // gameController.gameEngine.player.moveRightAnimationSet[2] = R.drawable.testgun
    }

    fun updateImages(){
        if(gameController.gameEngine.player.lastDirection.equals("up")){
            image = R.drawable.shotgun_right
            imageView.x = 5000f
        }else if(gameController.gameEngine.player.lastDirection.equals("down")){
            image = R.drawable.shotgun_down
            imageView.x = (gameController.gameEngine.player.getXPosition() - (getWidth()/2)) * gameController.screenXRatio
            imageView.y = (gameController.gameEngine.player.getYPosition()) * gameController.screenYRatio
        }else if(gameController.gameEngine.player.lastDirection.equals("left")){
            image = R.drawable.shotgun_left
            imageView.x = (gameController.gameEngine.player.getXPosition() - getWidth()) * gameController.screenXRatio
            imageView.y = (gameController.gameEngine.player.getYPosition()) * gameController.screenYRatio
        }else if(gameController.gameEngine.player.lastDirection.equals("right")){
            image = R.drawable.shotgun_right
            imageView.x = gameController.gameEngine.player.getXPosition() * gameController.screenXRatio
            imageView.y = gameController.gameEngine.player.getYPosition() * gameController.screenYRatio
        }else{
            image = R.drawable.shotgun_right
        }
    }

    override fun shoot(angle: Float){
        if(reloadTime <= 0){
            if (gameController.gameEngine.player.bulletCounter >= gameController.gameEngine.player.bulletArray.size){
                bulletCounter = 0
            }
            val x = Random()
            var d = x.nextInt(getAccuracy()) + 1
            d = d - (getAccuracy()/2)
            val result = d / 100.0
            gameController.gameEngine.player.bulletArray[gameController.gameEngine.player.bulletCounter] = Bullet(gameController, gameController.gameEngine.player, (angle + result).toFloat())
            gameController.gameEngine.player.bulletArray[gameController.gameEngine.player.bulletCounter+1] = Bullet(gameController, gameController.gameEngine.player, (angle + (result*(result*13))).toFloat())
            gameController.gameEngine.player.bulletArray[gameController.gameEngine.player.bulletCounter+2] = Bullet(gameController, gameController.gameEngine.player, (angle + (result*(result*28))).toFloat())
            gameController.gameEngine.player.bulletCounter++
            gameController.gameEngine.player.bulletCounter++
            gameController.gameEngine.player.bulletCounter++
            gameController.soundManager.soundPool.play(gameController.soundManager.pisto1,gameController.volume,gameController.volume,5,0,1f)
            reloadTime = maxReload
        }
        reloadTime--
    }



}