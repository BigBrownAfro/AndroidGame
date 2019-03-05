package com.example.jacobgraves.myapplication.model

import com.example.jacobgraves.myapplication.GameController

class Room(var gameController: GameController, var schematic: Array<Array<Int>>) {
    lateinit var tiles : Array<Array<RoomTile>>

    init{
        buildRoom()
    }

    fun buildRoom(){
        //Parses throught the schematic to generate each tile and build the room
        /*
        var i = 0
        var j = 0
        for(intArray in schematic){
            for(int in intArray){
                when(int){
                    0 -> tiles[i][j] = Ground(gameController)
                    1 -> tiles[i][j] = Wall(gameController,"top")
                    2 -> tiles[i][j] = Wall(gameController,"bottom")
                    3 -> tiles[i][j] = Wall(gameController,"left")
                    4 -> tiles[i][j] = Wall(gameController,"right")
                    5 -> tiles[i][j] = Door(gameController, "top")
                    6 -> tiles[i][j] = Door(gameController, "bottom")
                    7 -> tiles[i][j] = Door(gameController, "left")
                    8 -> tiles[i][j] = Door(gameController, "right")
                }
                j++
            }
            i++
        }
        */
    }
}