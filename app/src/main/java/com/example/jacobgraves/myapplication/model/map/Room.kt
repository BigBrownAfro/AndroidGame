package com.example.jacobgraves.myapplication.model.map

import android.widget.ImageView
import com.example.jacobgraves.myapplication.GameController
import kotlinx.android.synthetic.main.game_view.*

class Room(var gameController: GameController, var schematic: Array<Array<Int>>) {
    var tiles : Array<Array<RoomTile?>>

    init{
        //              rows                       cols
        tiles = Array(12){Array<RoomTile?>(20){null}}
        println("Schematic rows: " + schematic.size + ", Schematic cols: " + schematic[0].size)
        println("tiles rows: " + tiles.size + ", tiles cols: " + tiles[0].size)
        buildRoom()
        setupImageView()
    }

    fun buildRoom(){
        //Parses through the schematic to generate each tile and build the room
        for(i in 0..tiles.size-1) {
            for (j in 0..tiles[0].size - 1) {
                tiles[i][j] = Ground(gameController, i, j)
            }
        }

        for(j in 0..tiles[0].size-1){
            tiles[0][j] = Wall(gameController, "top", 0, j)
        }
        for(j in 0..tiles[11].size-1){
            tiles[11][j] = Wall(gameController, "bottom", 11, j)
        }

        /*
        var i = 0
        var j = 0
        for(intArray in schematic){
            for(int in intArray){
                /*when(int){
                    0 -> tiles[i][j] = Ground(gameController)
                    1 -> tiles[i][j] = Wall(gameController,"top")
                    2 -> tiles[i][j] = Wall(gameController,"bottom")
                    3 -> tiles[i][j] = Wall(gameController,"left")
                    4 -> tiles[i][j] = Wall(gameController,"right")
                    5 -> tiles[i][j] = Door(gameController, "top")
                    6 -> tiles[i][j] = Door(gameController, "bottom")
                    7 -> tiles[i][j] = Door(gameController, "left")
                    8 -> tiles[i][j] = Door(gameController, "right")
                }*/
                tiles[i][j] = Wall(gameController,"top",i,j)
                j++
            }
            i++
        }*/
    }

    fun setupImageView(){
        for(i in 0..tiles.size-1){
            for (j in 0..tiles[0].size-1){
                var tile = tiles[i][j]
                if (tile != null){
                    tile.setupImageView()
                }
            }
        }
        println("tile[0][0] x,y: " + tiles[0][0]!!.imageView.x / gameController.screenXRatio + "," + tiles[0][0]!!.imageView.y / gameController.screenYRatio)
    }

    fun updateImages(){ //Takes to long so this doesn't run
        for(i in 0..tiles.size-1){
            for (j in 0..tiles[0].size-1){
                var tile = tiles[i][j]
                if (tile != null){
                    tile.updateAnimations()
                    tile.updateImageView()
                }
            }
        }
    }
}