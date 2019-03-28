package com.example.jacobgraves.myapplication.model

import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import kotlinx.android.synthetic.main.game_view.*

class Door(gameController: GameController, direction: String):RoomTile(gameController) {


    init{
        isDoor = true
        assignAnimationPictures()
    }

    override fun assignAnimationPictures() {
        TODO("not implemented") //Assign the pictures for ground animations. Even if there's only one picture
        image = R.drawable.temp_door
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