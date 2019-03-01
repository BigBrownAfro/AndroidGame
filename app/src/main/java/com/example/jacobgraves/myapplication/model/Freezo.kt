package com.example.jacobgraves.myapplication.model

import android.content.res.Resources
import kotlin.math.asin
import kotlin.math.hypot

class Freezo(resources: Resources): Enemy(resources) {

    var maxReload = 30
    var reloadTime = 0;
    var followRadius = 200

    init {
        setHealthValue(20)
        setAttackValue(1)
        setMovementSpeed(.75f)
    }

    fun pursuePlayer(player:Player){
        if(player.getXPosition() < this.getXPosition() && this.getXPosition() - player.getXPosition() > followRadius){
            moveLeft()
        }
        if(player.getXPosition() > this.getXPosition() && player.getXPosition() - this.getXPosition() > followRadius){
            moveRight()
        }
        if(player.getYPosition() < this.getYPosition() && this.getYPosition() - player.getYPosition() > followRadius){
            moveUp()
        }
        if(player.getYPosition() > this.getYPosition() && player.getYPosition() - this.getYPosition() > followRadius){
            moveDown()
        }
    }

    fun attack(player: Player){
        if(inRadius(player) && reloadTime <= 0){
            shoot(lastDirection)
            reloadTime = maxReload;
            /*
            var xDifference = player.getXPosition() - getXPosition()
            var yDifference = getYPosition() - player.getYPosition()
            var hypotenuse = hypot(xDifference, yDifference)
            var angle = asin(yDifference/hypotenuse)
            */

        }
        reloadTime--
    }



}