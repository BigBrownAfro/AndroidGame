package com.example.jacobgraves.myapplication.model.consumables

import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import com.example.jacobgraves.myapplication.model.Player

class SpeedUp(gameController: GameController, x:Float, y:Float): Consumable(gameController,x,y) {
    override var image = R.drawable.arrow

    init{
        width = 18
        height = 31
    }

    override fun giveTo(player: Player) {
        player.setMovementSpeed(player.getMovementSpeed()*1.1f)
    }
}