package com.example.jacobgraves.myapplication.model.enemies

import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.model.Player
import com.example.jacobgraves.myapplication.model.map.Room
import kotlin.math.acos
import kotlin.math.hypot

class SwarmMario(gameController: GameController): Enemy(gameController) {

    var cooldown = 0

    init {
        setHealthValue(1)
        setAttackValue(1)
        setMovementSpeed(.6f)
        sensorRadius = 400

        setXPosition(Room.mapX + 100)
        setYPosition(Room.mapY + Room.mapHeight/2)

        setWidth(40)
        setHeight(80)
    }

    fun pursuePlayer(player: Player){
        var xDifference = player.getXPosition() - getXPosition()
        var yDifference = getYPosition() - player.getYPosition()
        var hypotenuse = hypot(xDifference, yDifference)
        var angle = acos(xDifference/hypotenuse)
        if(yDifference < 0){
            angle *= -1f
        }

        move(angle)
    }

    fun attack(player: Player){
        cooldown -= 1
        if (player.hitBox.intersect(hitBox)){
            if (cooldown < 0){
                player.setHealthValue(player.getHealthValue() - getAttackValue())
                cooldown = 120
            }
        }
    }
}