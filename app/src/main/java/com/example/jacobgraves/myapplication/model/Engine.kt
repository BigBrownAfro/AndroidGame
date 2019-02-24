package com.example.jacobgraves.myapplication.model

import android.graphics.RectF


class Engine {
    var player: Player
    var frameCount: Int
    var enemy:Freezo
    var playerRect:RectF
    var enemyRect:RectF
    var bulletRectArray:Array<RectF?>
    var reloadTime:Int


    constructor(name:String){
        player = Player(name)
        enemy = Freezo()
        frameCount = 0
        playerRect = RectF(player.getXPosition(),player.getYPosition(),player.getXPosition()+player.getWidth(),player.getYPosition()+player.getHeight())
        enemyRect = RectF(enemy.getXPosition(),enemy.getYPosition(),enemy.getXPosition()+enemy.getWidth(),enemy.getYPosition()+enemy.getHeight())
        bulletRectArray = Array(100){null}
        reloadTime = 0
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
        enemy.decelerate()
        moveBullets()
        moveEnemies()
        updateHitboxes()
        checkHitboxes()
        attackPlayer()
    }

    fun moveEnemies(){
        enemy.pursuePlayer(player)

    }

    fun updateHitboxes() {
        playerRect.set(player.getXPosition(), player.getYPosition(), player.getXPosition() + player.getWidth(), player.getYPosition() + player.getHeight())
        enemyRect.set(enemy.getXPosition(), enemy.getYPosition(), enemy.getXPosition() + enemy.getWidth(), enemy.getYPosition() + enemy.getHeight())

        var count = 0
        for (bullet in player.bulletArray) {
            if (bullet != null) {
                bulletRectArray[count]= RectF(bullet.xPosition,bullet.yPosition,bullet.xPosition+bullet.getWidth(),bullet.yPosition+bullet.getHeight())
                if(enemyRect.intersect(bulletRectArray[count])){
                    println("Bullet Collide")
                }
            }
            count++
        }
    }

    fun checkHitboxes(){
        if(enemyRect.intersect(playerRect)){
            println("Enemy Collide")
        }
    }

    fun updateAnimations(){
        player.updateAnimations()
        player.updateBulletAnimations()
        enemy.updateAnimations()
        enemy.updateBulletAnimations()
    }

    fun moveBullets(){
        player.moveBullets()
        enemy.moveBullets()
    }

    fun attackPlayer(){
        enemy.attack(player)
    }
}