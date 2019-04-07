package com.example.jacobgraves.myapplication.model.map

import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import kotlinx.android.synthetic.main.game_view.*

class Ground(gameController: GameController, row: Int, col: Int): RoomTile(gameController, row, col) {


    init{
        isGround = true
        assignAnimationPictures()
    }

    override fun assignAnimationPictures() {
        image = R.drawable.floor
        animationSet = IntArray(1)
        animationSet[0] = R.drawable.floor
    }
}