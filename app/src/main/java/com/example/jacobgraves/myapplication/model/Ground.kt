package com.example.jacobgraves.myapplication.model

import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import kotlinx.android.synthetic.main.game_view.*

class Ground(gameController: GameController):RoomTile(gameController) {


    init{
        isGround = true
        assignAnimationPictures()
    }

    override fun assignAnimationPictures() {
        image = R.drawable.basic_floor
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