package com.example.jacobgraves.myapplication.model

import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import kotlinx.android.synthetic.main.game_view.*

class Wall(gameController: GameController, direction: String):RoomTile(gameController) {


    init{
        isWall = true
        assignAnimationPictures()
    }

    override fun assignAnimationPictures() {
        TODO("not implemented") //Assign the pictures for ground animations. Even if there's only one picture
        image = R.drawable.temp_wall
    }

    override fun setupImageView(){
        gameController.runOnUiThread{
            run{
                gameController.constraintLayout.addView(imageView)
                imageView.setImageResource(image)
            }
        }
    }

    override fun updateImageView(){
        gameController.runOnUiThread{
            run{
                imageView.setImageResource(image)
            }
        }
    }
}