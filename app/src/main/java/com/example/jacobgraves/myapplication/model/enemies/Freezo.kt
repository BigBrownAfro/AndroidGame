package com.example.jacobgraves.myapplication.model.enemies

import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.model.Player
import kotlin.math.acos
import kotlin.math.hypot

class Freezo(gameController: GameController): Enemy(gameController) {

    var maxReload = 30
    var reloadTime = 0;
    var stopRadius = 300

    init {
        setHealthValue(5)
        setAttackValue(1)
        setMovementSpeed(.75f)
        sensorRadius = 400
    }

    fun pursuePlayer(player: Player){
        var xDifference = player.getXPosition() - getXPosition()
        var yDifference = getYPosition() - player.getYPosition()
        var hypotenuse = hypot(xDifference, yDifference)
        var angle = acos(xDifference/hypotenuse)
        if(yDifference < 0){
            angle *= -1f
        }
        if(hypotenuse > stopRadius){
            move(angle)
        }

        /*
        if(player.getXPosition() < this.getXPosition() && this.getXPosition() - player.getXPosition() > stopRadius){
            moveLeft()
        }
        if(player.getXPosition() > this.getXPosition() && player.getXPosition() - this.getXPosition() > stopRadius){
            moveRight()
        }
        if(player.getYPosition() < this.getYPosition() && this.getYPosition() - player.getYPosition() > stopRadius){
            moveUp()
        }
        if(player.getYPosition() > this.getYPosition() && player.getYPosition() - this.getYPosition() > stopRadius){
            moveDown()
        }*/
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