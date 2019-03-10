package com.example.jacobgraves.myapplication.model

import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R

class SpeedUp(gameController: GameController, x:Float, y:Float):Consumable(gameController,x,y) {
    override var image = R.drawable.arrow

    init{
        width = 18
        height = 31
    }

    override fun giveTo(player: Player) {
        player.setMovementSpeed(player.getMovementSpeed()*1.2f)
    }
}