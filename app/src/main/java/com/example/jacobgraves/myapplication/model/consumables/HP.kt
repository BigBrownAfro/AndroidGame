package com.example.jacobgraves.myapplication.model.consumables

import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import com.example.jacobgraves.myapplication.model.Player

class HP(gameController: GameController,x:Float, y:Float): Consumable(gameController,x,y) {
    override var image = R.drawable.heart

    init{
        image = R.drawable.health_potion;
        width = 33
        height = 31
    }

    override fun giveTo(player: Player) {
        gameController.soundManager.soundPool.play(gameController.soundManager.health,gameController.volume,gameController.volume,5,0,1f)
        player.setHealthValue(player.getHealthValue() + 1)
    }
}