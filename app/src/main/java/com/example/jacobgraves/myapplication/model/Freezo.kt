package com.example.jacobgraves.myapplication.model

class Freezo(): Enemy() {


    init {
        setHealthValue(20)
        setAttackValue(1)
        setMovementSpeed(2.0f)
    }

    fun pursuePlayer(player:Player){
        if(player.getXPosition() < this.getXPosition() && this.getXPosition() - player.getXPosition() > 100){
            moveLeft()
        }
    }

}