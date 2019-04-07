package com.example.jacobgraves.myapplication.model.guns

import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import com.example.jacobgraves.myapplication.model.Bullet

class StartingPistol(gameController: GameController): Gun(gameController) {
    init {
        setMaxAmmo(50)
        setCurrentAmmo(50)
        maxReload = 15
        setAccuracy(.2f)

        assignImages()
    }

    override fun assignImages(){
        image = R.drawable.testgun
        shootAnimationSet = IntArray(3)
        //gameController.gameEngine.player.moveRightAnimationSet[0] = R.drawable.testgun
       // gameController.gameEngine.player.moveRightAnimationSet[1] = R.drawable.testgunfire
       // gameController.gameEngine.player.moveRightAnimationSet[2] = R.drawable.testgun
    }

    override fun shoot(angle: Float){
        if(reloadTime <= 0){
            if (gameController.gameEngine.player.bulletCounter >= gameController.gameEngine.player.bulletArray.size){
                bulletCounter = 0
            }
            gameController.gameEngine.player.bulletArray[gameController.gameEngine.player.bulletCounter] = Bullet(gameController, gameController.gameEngine.player, (angle + ((-getAccuracy() * Math.random()) * -.5 * Math.random())).toFloat())
            gameController.gameEngine.player.bulletCounter++
            gameController.soundManager.soundPool.play(gameController.soundManager.pisto1,1f,1f,5,0,1f)
            reloadTime = maxReload
        }
        reloadTime--
    }


}