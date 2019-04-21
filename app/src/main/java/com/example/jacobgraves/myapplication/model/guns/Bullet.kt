package com.example.jacobgraves.myapplication.model.guns

import android.graphics.RectF
import android.widget.ImageView
import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import com.example.jacobgraves.myapplication.model.Player
import com.example.jacobgraves.myapplication.model.enemies.Enemy
import com.example.jacobgraves.myapplication.model.map.Room
import kotlinx.android.synthetic.main.game_view.*
import kotlin.math.*

class Bullet{
    var attackValue:Int
    var isAlive:Boolean
    var friendly:Boolean
    var image = R.drawable.line
    lateinit var animationSet:IntArray
    var size:Float
    var type:String
    var xPosition:Float
    var yPosition:Float
    private var width:Int = 0
    private var height:Int = 0
    var xMovementSpeed:Float
    var yMovementSpeed:Float
    var animationCounter = 0
    var hitBox :RectF
    var angle:Float

    var gameController:GameController

    val imageView:ImageView

    constructor(gameController: GameController, player: Player, angle:Float){
        attackValue = player.getAttackValue()
        isAlive = true
        friendly = true
        setHeight(2)
        setWidth(7)
        size = 1f
        type = "regular"
        if(player.accelerationX < 0f){
            xMovementSpeed = 10f
        }else{
            xMovementSpeed = player.accelerationX+10f
        }
        if (player.accelerationY < 0f){
            yMovementSpeed = 10f
        }else{
            yMovementSpeed = player.accelerationY+10f
        }
        xPosition = player.getXPosition()
        yPosition = player.getYPosition()
        hitBox = RectF(xPosition-getWidth()/2,yPosition-getHeight()/2,xPosition+getWidth()/2,yPosition+getHeight()/2)
        this.angle = angle

        assignPictures()
        this.gameController = gameController
        imageView = ImageView(gameController)
        setupImageView()
    }

    constructor(gameController: GameController, bulletType:String, player: Player, angle:Float){
        attackValue = player.getAttackValue()
        isAlive = true
        friendly = true
        setHeight(2)
        setWidth(7)
        size = 1f
        type = bulletType
        xMovementSpeed = player.accelerationX+10f
        yMovementSpeed = player.accelerationY+10f
        xPosition = player.getXPosition()
        yPosition = player.getYPosition()
        hitBox = RectF(xPosition-getWidth()/2,yPosition-getHeight()/2,xPosition+getWidth()/2,yPosition+getHeight()/2)
        this.angle = angle

        assignPictures()
        this.gameController = gameController
        imageView = ImageView(gameController)
        setupImageView()
    }

    constructor(gameController: GameController, enemy: Enemy, angle:Float){
        attackValue = enemy.getAttackValue()
        isAlive = true
        friendly = true
        setHeight(2)
        setWidth(7)
        size = 1f
        type = "regular"
        if(enemy.accelerationX < 0f){
            xMovementSpeed = 10f
        }else{
            xMovementSpeed = enemy.accelerationX+10f
        }
        if (enemy.accelerationY < 0f){
            yMovementSpeed = 10f
        }else{
            yMovementSpeed = enemy.accelerationY+10f
        }
        xPosition = enemy.getXPosition()
        yPosition = enemy.getYPosition()
        hitBox = RectF(xPosition-getWidth()/2,yPosition-getHeight()/2,xPosition+getWidth()/2,yPosition+getHeight()/2)
        this.angle = angle

        assignPictures()
        this.gameController = gameController
        imageView = ImageView(gameController)
        setupImageView()
    }

    constructor(gameController: GameController, bulletType:String, enemy: Enemy, angle:Float){
        attackValue = enemy.getAttackValue()
        isAlive = true
        friendly = false
        setHeight(2)
        setWidth(7)
        size = 1f
        type = bulletType
        xMovementSpeed = enemy.accelerationX+10f
        yMovementSpeed = enemy.accelerationY+10f
        xPosition = enemy.getXPosition()
        yPosition = enemy.getYPosition()
        hitBox = RectF(xPosition-getWidth()/2,yPosition-getHeight()/2,xPosition+getWidth()/2,yPosition+getHeight()/2)
        this.angle = angle

        assignPictures()
        this.gameController = gameController
        imageView = ImageView(gameController)
        setupImageView()
    }

    //Setters----------------------------
    fun setWidth(x: Int){
        if (x < 0){
            width = 50
        }else{
            width = x
        }
    }

    fun setHeight(x: Int){
        if (x < 0){
            height = 50
        }else{
            height = x
        }
    }


    //Getters----------------------------
    fun getHeight(): Int{
        return height
    }

    fun getWidth (): Int{
        return width
    }


    //Other Stuff-------------------------

    fun kill(){
        gameController.runOnUiThread{
            run{
                gameController.constraintLayout.removeView(imageView)
            }
        }
    }

    fun assignPictures(){
        animationSet = IntArray(1)
        animationSet[0] = R.drawable.line
        /*
        if (type.equals("regular")){
            animationSet = IntArray(3)
            animationSet[0] = R.drawable.bullet_animation_1
            animationSet[1] = R.drawable.bullet_animation_2
            animationSet[2] = R.drawable.bullet_animation_3
        }else{
            animationSet = IntArray(3)
            animationSet[0] = R.drawable.bullet_animation_1
            animationSet[1] = R.drawable.bullet_animation_2
            animationSet[2] = R.drawable.bullet_animation_3
        }*/
    }

    /*fun move(){
        if(direction == "up"){
            yPosition -= yMovementSpeed
        }else if(direction == "down"){
            yPosition += yMovementSpeed
        }else if(direction == "left"){
            xPosition -= xMovementSpeed
        }else if(direction == "right"){
            xPosition += xMovementSpeed
        }
    }*/

    fun move(){
        xPosition += cos(angle)*xMovementSpeed
        yPosition -= sin(angle)*yMovementSpeed

        if (false){
            /*if(xPosition < 300f + width/2){
                xPosition = 300f + width/2
                angle = PI.toFloat() - angle
            }
            if(xPosition > 1620f - width/2){
                xPosition = 1620f - width/2
                angle = PI.toFloat() - angle
            }
            if (yPosition < 150f - height/2 +10){
                yPosition = 150f - height/2 +10
                angle = 2.0f * PI.toFloat() - angle
            }
            if (yPosition > 930f - height/2){
                yPosition = 930f - height/2
                angle = 2.0f * PI.toFloat() - angle
            }*/
        }else{
            checkRoomBounds()
        }
    }

    fun checkRoomBounds(){
        if(xPosition < Room.mapX + 64 + width/2){
            xPosition = Room.mapX + 64 + width/2
            isAlive = false
        }
        if(xPosition > (Room.mapX + Room.mapWidth - 64) - width/2){
            xPosition = (Room.mapX + Room.mapWidth - 64) - width/2
            isAlive = false
        }
        if (yPosition < Room.mapY + 64 - height/2){
            yPosition = Room.mapY + 64 - height/2
            isAlive = false
        }
        if (yPosition > (Room.mapY + Room.mapHeight) - 64 - height/2){
            yPosition = (Room.mapY + Room.mapHeight) - 64 - height/2
            isAlive = false
        }
    }

    fun updateAnimations(){
        if (animationCounter >= animationSet.size){
            animationCounter = 0
        }
        image = animationSet[animationCounter]
        animationCounter += 1
    }

    fun updateHitbox(){
        hitBox.set(xPosition-getWidth()/2,yPosition-getHeight()/2,xPosition+getWidth()/2,yPosition+getHeight()/2)
    }

    fun setupImageView(){
        gameController.runOnUiThread{
            run{
                gameController.constraintLayout.addView(imageView)
                imageView.layoutParams.width = (width * gameController.screenXRatio).toInt()
                imageView.layoutParams.height = (height * gameController.screenYRatio).toInt()
                imageView.x = (xPosition - getWidth()/2f) * gameController.screenXRatio
                imageView.y = (yPosition - getHeight()/2f) * gameController.screenYRatio

                imageView.rotation = angle * 180 / PI.toFloat()
                imageView.setImageResource(image)
            }
        }
    }

    fun updateImageView(){
        imageView.x = (xPosition - getWidth()/2f) * gameController.screenXRatio
        imageView.y = (yPosition - getHeight()/2f) * gameController.screenYRatio
        /*gameController.runOnUiThread{
            run{
                imageView.x = (xPosition - getWidth()/2f) * gameController.screenXRatio
                imageView.y = (yPosition - getHeight()/2f) * gameController.screenYRatio

                //imageView.rotation = angle * 180 / PI.toFloat()
                imageView.setImageResource(image)
            }
        }*/
    }
}