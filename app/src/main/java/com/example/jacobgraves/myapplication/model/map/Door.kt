package com.example.jacobgraves.myapplication.model.map

import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import kotlinx.android.synthetic.main.game_view.*

class Door(gameController: GameController, direction: String, row: Int, col: Int): RoomTile(gameController, row, col) {


    init{
        isDoor = true
        assignAnimationPictures()
    }

    override fun assignAnimationPictures() {
        image = R.drawable.temp_door
        animationSet = IntArray(1)
        animationSet[0] = R.drawable.door
    }
}