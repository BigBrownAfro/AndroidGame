package com.example.jacobgraves.myapplication.model

import android.graphics.RectF
import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R


class Engine {
    var player: Player
    var frameCount: Int
    var freezos = ArrayList<Freezo>()

    constructor(gameController: GameController, name:String){
        player = Player(gameController,name)
        frameCount = 0
        freezos.add(Freezo(gameController))
        /*freezos.add(Freezo())
        freezos[1].setXPosition(1720f)
        freezos[1].setYPosition(980f)
        freezos[1].followRadius += 100*/
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


        moveBullets()
        moveEnemies()
        updateImageViews()
        decelerate()
        updateHitboxes()
        checkForCollision()
        attackPlayer()
        updateGUI()
    }

    fun updateGUI(){
        player.updateGUI()
    }

    fun moveEnemies(){
        for(freezo in freezos){
            freezo.pursuePlayer(player)
        }
    }

    fun decelerate(){
        player.decelerate()
        for(freezo in freezos){
            freezo.decelerate()
        }
    }

    fun updateHitboxes() {
        player.updateHitbox()
        player.updateBulletHitboxes()

        for(freezo in freezos){
            freezo.updateHitbox()
            freezo.updateBulletHitboxes()
        }
    }

    fun checkForCollision(){
        //for each item check collision with player
        //for each consumable check collision with player

        for(freezo in freezos){
            //check enemy player collision
            if(freezo.hitBox.intersect(player.hitBox)){
                println("Enemy Collided With Player")
            }

            //check player bullet collisions
            var count = 0
            for (bullet in player.bulletArray) {
                if (bullet != null) {
                    if(freezo.hitBox.intersect(bullet.hitBox)){
                        println("Bullet Collided With Enemy")
                    }
                }
                count++
            }

            //check enemy bullet collisions
            count = 0
            for (bullet in freezo.bulletArray) {
                if (bullet != null) {
                    if(player.hitBox.intersect(bullet.hitBox)){
                        println("Bullet Collided With Player")
                        bullet.xPosition = -1000f


                        player.setHealthValue(player.getHealthValue()-1)
                        println(player.getHealthValue())
                    }
                }
                count++
            }
        }
    }

    fun updateAnimations(){
        player.updateAnimations()
        player.updateBulletAnimations()

        for(freezo in freezos){
            freezo.updateAnimations()
            freezo.updateBulletAnimations()
        }
    }

    fun moveBullets(){
        player.moveBullets()

        for(freezo in freezos){
            freezo.moveBullets()
        }
    }

    fun attackPlayer(){
        for(freezo in freezos) {
            freezo.attack(player)
        }
    }

    fun updateImageViews(){
        player.updateImageView()

        for(bullet in player.bulletArray){
            if(bullet != null){
                bullet.updateImageView()
            }
        }

        for (enemy in freezos){
            enemy.updateImageView()
            for(bullet in enemy.bulletArray){
                if(bullet != null){
                    bullet.updateImageView()
                }
            }
        }
    }
}