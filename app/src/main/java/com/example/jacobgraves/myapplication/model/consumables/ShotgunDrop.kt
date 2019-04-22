package com.example.jacobgraves.myapplication.model.consumables

import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import com.example.jacobgraves.myapplication.model.Player
import com.example.jacobgraves.myapplication.model.guns.Shotgun

class ShotgunDrop(gameController: GameController, x:Float, y:Float): Consumable(gameController,x,y) {
    override var image = R.drawable.shotgun_drop

    init {
        width = 90
        height = 35
    }

    override fun giveTo(player: Player) {
        if(player.SecondaryGun.isEquipped == false) {
            gameController.soundManager.soundPool.play(gameController.soundManager.swapGun, gameController.volume, gameController.volume, 5, 0, 1f)
            player.SecondaryGun = Shotgun(gameController)
            player.StartingGun.imageView.x = 5000f
            player.StartingGun.isEquipped = false
            player.SecondaryGun.isEquipped = true
            player.SecondaryGun.isPickedUp = true
        }
    }
}