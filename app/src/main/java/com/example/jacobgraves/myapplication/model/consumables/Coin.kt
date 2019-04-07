package com.example.jacobgraves.myapplication.model.consumables

import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import com.example.jacobgraves.myapplication.model.Player

class Coin(gameController: GameController,x:Float,y:Float): Consumable(gameController,x,y) {
    override var image = R.drawable.coin

    init {
        width = 31
        height = 40
    }

    override fun giveTo(player: Player) {
        player.coins += 1
    }
}