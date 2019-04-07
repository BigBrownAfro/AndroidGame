package com.example.jacobgraves.myapplication.model.map

import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import kotlinx.android.synthetic.main.game_view.*

class Wall(gameController: GameController, var direction: String, row: Int, col: Int): RoomTile(gameController, row, col) {


    init{
        isWall = true
        assignAnimationPictures()
    }

    override fun assignAnimationPictures() {
        image = R.drawable.wall
        when(direction){
            "top" -> image = R.drawable.wall_bottom_cobble
            "bottom" -> image = R.drawable.wall_bottom_cobble
            "left" -> image = R.drawable.wall_left_cobble
            "right" -> image = R.drawable.wall_left_cobble
        }
        animationSet = IntArray(1)
        animationSet[0] = image
    }
}