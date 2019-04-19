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
        isEquipped = true

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
        if(gameController.gameEngine.player.accelerationX > 0){
            image = R.drawable.starting_pistol_right
            gameController.gameEngine.player.lastDirection ="right"
        }else
            if(gameController.gameEngine.player.accelerationX < 0){
                image = R.drawable.starting_pistol_left
                gameController.gameEngine.player.lastDirection ="left"
            }else
                if(gameController.gameEngine.player.accelerationY < 0){
                    image = R.drawable.starting_pistol_right
                    gameController.gameEngine.player.lastDirection ="up"
                }else
                    if(gameController.gameEngine.player.accelerationY > 0){
                        image = R.drawable.starting_pistol_down
                        gameController.gameEngine.player.lastDirection ="down"
                    }else
                        if(gameController.gameEngine.player.accelerationX == 0f){
                            if(gameController.gameEngine.player.lastDirection.equals("up")){
                                image = R.drawable.starting_pistol_right
                                imageView.x = 5000f
                            }else if(gameController.gameEngine.player.lastDirection.equals("down")){
                                image = R.drawable.starting_pistol_down
                            }else if(gameController.gameEngine.player.lastDirection.equals("left")){
                                image = R.drawable.starting_pistol_left
                            }else if(gameController.gameEngine.player.lastDirection.equals("right")){
                                image = R.drawable.starting_pistol_right
                            }else{
                                image = R.drawable.starting_pistol_right
                            }
                        }
    }

    override fun shoot(angle: Float){
        if(reloadTime <= 0){
            if (gameController.gameEngine.player.bulletCounter >= gameController.gameEngine.player.bulletArray.size){
                bulletCounter = 0
            }
            gameController.gameEngine.player.bulletArray[gameController.gameEngine.player.bulletCounter] = Bullet(gameController, gameController.gameEngine.player, (angle + ((-getAccuracy() * Math.random()) * -.5 * Math.random())).toFloat())
            gameController.gameEngine.player.bulletCounter++
            gameController.soundManager.soundPool.play(gameController.soundManager.pisto1,gameController.volume,gameController.volume,5,0,1f)
            reloadTime = maxReload
        }
        reloadTime--
    }


}