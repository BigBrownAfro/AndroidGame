package com.example.jacobgraves.myapplication.model.guns

import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import com.example.jacobgraves.myapplication.model.Bullet
import java.util.*

class StartingPistol(gameController: GameController): Gun(gameController) {
    init {
        setMaxAmmo(50)
        setCurrentAmmo(50)
        maxReload = 15
        setAccuracy(1)
        isEquipped = true
        isPickedUp = true

        //var directionRight = gameController.gameEngine.player.getXPosition()

        assignImages()
    }

    override fun assignImages(){
        image = R.drawable.starting_pistol_right
        shootAnimationSet = IntArray(3)
        //gameController.gameEngine.player.moveRightAnimationSet[0] = R.drawable.testgun
       // gameController.gameEngine.player.moveRightAnimationSet[1] = R.drawable.testgunfire
       // gameController.gameEngine.player.moveRightAnimationSet[2] = R.drawable.testgun
    }

    fun updateImages(){
        if(isEquipped == true) {
            /*  if(gameController.gameEngine.player.accelerationX > 0){
            image = R.drawable.starting_pistol_right
           // gameController.gameEngine.player.lastDirection ="right"
        }else
            if(gameController.gameEngine.player.accelerationX < 0){
                image = R.drawable.starting_pistol_left
               // gameController.gameEngine.player.lastDirection ="left"
            }else
                if(gameController.gameEngine.player.accelerationY < 0){
                    image = R.drawable.starting_pistol_right
                   // gameController.gameEngine.player.lastDirection ="up"
                }else
                    if(gameController.gameEngine.player.accelerationY > 0){
                        image = R.drawable.starting_pistol_down
                       // gameController.gameEngine.player.lastDirection ="down"
                    }else
*/
            if (gameController.gameEngine.player.lastDirection.equals("up")) {
                image = R.drawable.starting_pistol_right
                imageView.x = 5000f
            } else if (gameController.gameEngine.player.lastDirection.equals("down")) {
                image = R.drawable.starting_pistol_down
                imageView.x = (gameController.gameEngine.player.getXPosition() - (width / 2)) * gameController.screenXRatio
                imageView.y = (gameController.gameEngine.player.getYPosition()) * gameController.screenYRatio
            } else if (gameController.gameEngine.player.lastDirection.equals("left")) {
                image = R.drawable.starting_pistol_left
                imageView.x = (gameController.gameEngine.player.getXPosition() - width) * gameController.screenXRatio
                imageView.y = (gameController.gameEngine.player.getYPosition()) * gameController.screenYRatio
            } else if (gameController.gameEngine.player.lastDirection.equals("right")) {
                image = R.drawable.starting_pistol_right
                imageView.x = gameController.gameEngine.player.getXPosition() * gameController.screenXRatio
                imageView.y = gameController.gameEngine.player.getYPosition() * gameController.screenYRatio
            } else {
                image = R.drawable.starting_pistol_right
            }
        }

    }

    override fun shoot(angle: Float){
        if(isEquipped == true) {
            if (reloadTime <= 0) {
                if (gameController.gameEngine.player.bulletCounter >= gameController.gameEngine.player.bulletArray.size) {
                    bulletCounter = 0
                }
                val x = Random()
                var d = x.nextInt(getAccuracy()) + 1
                d = d - (getAccuracy() / 2)
                val result = d / 100.0
                gameController.gameEngine.player.bulletArray[gameController.gameEngine.player.bulletCounter] = Bullet(gameController, gameController.gameEngine.player, (angle + result).toFloat())
                gameController.gameEngine.player.bulletCounter++
                gameController.soundManager.soundPool.play(gameController.soundManager.pisto1, gameController.volume, gameController.volume, 5, 0, 1f)
                reloadTime = maxReload
            }
            reloadTime--
        }
    }


}