package com.example.jacobgraves.myapplication.model

import com.example.jacobgraves.myapplication.GameController
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.hypot

class Freezo(gameController: GameController): Enemy(gameController) {

    var maxReload = 30
    var reloadTime = 0;
    var followRadius = 500

    init {
        setHealthValue(5)
        setAttackValue(1)
        setMovementSpeed(.75f)
        sensorRadius = 800
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
            reloadTime = maxReload;

            var xDifference = player.getXPosition() - getXPosition()
            var yDifference = getYPosition() - player.getYPosition()
            var hypotenuse = hypot(xDifference, yDifference)
            var angle = acos(xDifference/hypotenuse)

            if(getYPosition() < player.getYPosition()){
                angle *= -1f
            }

            shoot(angle)
        }
        reloadTime--
    }



}