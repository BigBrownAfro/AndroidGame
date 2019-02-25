package com.example.jacobgraves.myapplication.model

import android.graphics.RectF


class Engine {
    var player: Player
    var frameCount: Int
    var freezos = ArrayList<Freezo>()

    constructor(name:String){
        player = Player(name)
        frameCount = 0
        freezos.add(Freezo())
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
        for(freezo in freezos){
            freezo.decelerate()
        }
        moveBullets()
        moveEnemies()
        updateHitboxes()
        checkForCollision()
        attackPlayer()
    }

    fun moveEnemies(){
        for(freezo in freezos){
            freezo.pursuePlayer(player)
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
}