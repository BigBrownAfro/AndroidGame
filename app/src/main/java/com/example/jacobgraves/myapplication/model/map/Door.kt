package com.example.jacobgraves.myapplication.model.map

import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import kotlinx.android.synthetic.main.game_view.*

class Door(gameController: GameController, var direction: String, row: Int, col: Int): RoomTile(gameController, row, col) {


    init{
        isDoor = true
        assignAnimationPictures()
    }

    override fun assignAnimationPictures() {
        image = R.drawable.temp_door
        when(direction){
            "top" -> image = R.drawable.door
            "bottom" -> image = R.drawable.door_bottom
            "left" -> image = R.drawable.door_left
            "right" -> image = R.drawable.door_right
        }
        animationSet = IntArray(1)
        animationSet[0] = R.drawable.door
    }
}