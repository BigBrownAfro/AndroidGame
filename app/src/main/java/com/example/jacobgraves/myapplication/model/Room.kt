package com.example.jacobgraves.myapplication.model

import android.widget.ImageView
import com.example.jacobgraves.myapplication.GameController
import kotlinx.android.synthetic.main.game_view.*

class Room(var gameController: GameController, var schematic: Array<Array<Int>>) {
    var tiles : Array<Array<Ground>> = Array(60, { Array(35, { Ground(gameController) }) })
    val imageView: ImageView

    init{
        tiles[0][0] = Ground(gameController)
        tiles[0][5] = Ground(gameController)
        buildRoom()
        imageView = ImageView(gameController)
        setupImageView()
    }

    fun buildRoom(){
        //Parses through the schematic to generate each tile and build the room

        var i = 0
        var j = 0
        for(intArray in schematic){
            for(int in intArray){
                when(int){
                    0 -> tiles[i][j] = Ground(gameController)
                  //  1 -> tiles[i][j] = Wall(gameController,"top")
                  //  2 -> tiles[i][j] = Wall(gameController,"bottom")
                  //  3 -> tiles[i][j] = Wall(gameController,"left")
                  //  4 -> tiles[i][j] = Wall(gameController,"right")
                   // 5 -> tiles[i][j] = Door(gameController, "top")
                  //  6 -> tiles[i][j] = Door(gameController, "bottom")
                  //  7 -> tiles[i][j] = Door(gameController, "left")
                  //  8 -> tiles[i][j] = Door(gameController, "right")
                }
                j++
            }
            i++
        }

    }
    fun setupImageView(){
        gameController.runOnUiThread{
            run{
                gameController.constraintLayout.addView(imageView)
                var i = 0
                var j = 0
                for(intArray in schematic){
                    for(int in intArray){
                        tiles[i][j].setupImageView()

                        j++
                    }
                    i++
                }
            }
        }
    }
}