package com.example.jacobgraves.myapplication.model

import com.example.jacobgraves.myapplication.GameController

class Ground(gameController: GameController):RoomTile(gameController) {

    init{
        isGround = true
    }

    override fun assignAnimationPictures() {
        TODO("not implemented") //Assign the pictures for ground animations. Even if there's only one picture
    }
}