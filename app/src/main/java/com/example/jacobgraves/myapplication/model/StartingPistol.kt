package com.example.jacobgraves.myapplication.model

import com.example.jacobgraves.myapplication.GameController

class StartingPistol(gameController: GameController): Gun(gameController) {
    init {
        setMaxAmmo(50)
        setCurrentAmmo(50)
        maxReload = 15
    }


}