package com.example.jacobgraves.myapplication.model

import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R

class HP(gameController: GameController,x:Float, y:Float):Consumable(gameController,x,y) {
    override var image = R.drawable.heart

    init{
        image = R.drawable.health_potion;
        width = 33
        height = 31
    }

    override fun giveTo(player: Player) {
        player.setHealthValue(player.getHealthValue() + 1)
    }
}