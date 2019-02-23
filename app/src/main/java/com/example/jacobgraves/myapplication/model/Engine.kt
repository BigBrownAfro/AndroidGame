package com.example.jacobgraves.myapplication.model

import android.graphics.Rect
import android.graphics.RectF
import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import kotlinx.android.synthetic.main.game_view.*
import android.widget.ImageView


class Engine {
    var player: Player
    var frameCount: Int
    var enemy:Freezo
    var playerRect:RectF
    var enemyRect:RectF
    var bulletRectArray:Array<RectF?>


    constructor(name:String){
        player = Player(name)
        enemy = Freezo()
        frameCount = 0
        playerRect = RectF(player.getXPosition(),player.getYPosition(),player.getXPosition()+player.getWidth(),player.getYPosition()+player.getHeight())
        enemyRect = RectF(enemy.getXPosition(),enemy.getYPosition(),enemy.getXPosition()+enemy.getWidth(),enemy.getYPosition()+enemy.getHeight())
        bulletRectArray = Array(100){null}
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
        moveBullets()
        checkHitboxes()
        moveEnemies()
        updateHitboxes()
    }

    fun moveEnemies(){
        //enemy.pursuePlayer(player)

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
            println("Collide")
        }
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
        for (bullet in player.bulletArray){
            if (bullet != null){
                if (bullet.animationCounter >= bullet.animationSet.size){
                    bullet.animationCounter = 0
                }
                bullet.image = bullet.animationSet[bullet.animationCounter]
                bullet.animationCounter += 1
            }
        }
    }

    fun moveBullets(){
        for (bullet in player.bulletArray){
            if (bullet != null){
                bullet.move()
            }
        }
    }
}