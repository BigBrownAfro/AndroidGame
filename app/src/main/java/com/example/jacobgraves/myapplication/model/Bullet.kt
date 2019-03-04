package com.example.jacobgraves.myapplication.model

import android.graphics.RectF
import android.widget.ImageView
import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import kotlinx.android.synthetic.main.game_view.*

class Bullet{
    var attackValue:Int
    var isAlive:Boolean
    var friendly:Boolean
    var image = R.drawable.bullet
    lateinit var animationSet:IntArray
    var size:Float
    var type:String
    var xPosition:Float
    var yPosition:Float
    private var width:Int = 0
    private var height:Int = 0
    var xMovementSpeed:Float
    var yMovementSpeed:Float
    var direction:String
    var animationCounter = 0
    var hitBox :RectF

    val gameController:GameController

    val imageView:ImageView

    constructor(gameController: GameController, player:Player, bulletDirection:String){
        attackValue = 1
        isAlive = true
        friendly = true
        setHeight(7)
        setWidth(20)
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
        direction = bulletDirection
        hitBox = RectF(xPosition-getWidth()/2,yPosition-getHeight()/2,xPosition+getWidth()/2,yPosition+getHeight()/2)

        assignPictures()
        this.gameController = gameController
        imageView = ImageView(gameController)
        setupImageView()
    }

    constructor(gameController: GameController, bulletType:String, player:Player, bulletDirection:String){
        attackValue = 1
        isAlive = true
        friendly = true
        setHeight(7)
        setWidth(20)
        size = 1f
        type = bulletType
        xMovementSpeed = player.accelerationX+10f
        yMovementSpeed = player.accelerationY+10f
        xPosition = player.getXPosition()
        yPosition = player.getYPosition()
        direction = bulletDirection
        hitBox = RectF(xPosition-getWidth()/2,yPosition-getHeight()/2,xPosition+getWidth()/2,yPosition+getHeight()/2)

        assignPictures()
        this.gameController = gameController
        imageView = ImageView(gameController)
        setupImageView()
    }

    constructor(gameController: GameController, enemy:Enemy, bulletDirection:String){
        attackValue = 1
        isAlive = true
        friendly = true
        setHeight(7)
        setWidth(20)
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
        direction = bulletDirection
        hitBox = RectF(xPosition-getWidth()/2,yPosition-getHeight()/2,xPosition+getWidth()/2,yPosition+getHeight()/2)


        assignPictures()
        this.gameController = gameController
        imageView = ImageView(gameController)
        setupImageView()
    }

    constructor(gameController: GameController, bulletType:String, enemy:Enemy, bulletDirection:String){
        attackValue = 1
        isAlive = true
        friendly = false
        setHeight(7)
        setWidth(20)
        size = 1f
        type = bulletType
        xMovementSpeed = enemy.accelerationX+10f
        yMovementSpeed = enemy.accelerationY+10f
        xPosition = enemy.getXPosition()
        yPosition = enemy.getYPosition()
        direction = bulletDirection
        hitBox = RectF(xPosition-getWidth()/2,yPosition-getHeight()/2,xPosition+getWidth()/2,yPosition+getHeight()/2)


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
        }
    }

    fun move(){
        if(direction == "up"){
            yPosition -= yMovementSpeed
        }else if(direction == "down"){
            yPosition += yMovementSpeed
        }else if(direction == "left"){
            xPosition -= xMovementSpeed
        }else if(direction == "right"){
            xPosition += xMovementSpeed
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
        hitBox = RectF(xPosition-getWidth()/2,yPosition-getHeight()/2,xPosition+getWidth()/2,yPosition+getHeight()/2)
    }

    fun setupImageView(){
        gameController.runOnUiThread{
            run{
                gameController.constraintLayout.addView(imageView)
                imageView.layoutParams.width = width
                imageView.layoutParams.height = height

                imageView.x = xPosition - getWidth()/2f
                imageView.y = yPosition - getHeight()/2f
                var tempImageResource = image
                if (tempImageResource < 0){
                    tempImageResource *= -1
                    imageView.setImageResource(tempImageResource)
                    imageView.rotationY = 180f
                }else{
                    imageView.rotationY = 0f
                    imageView.setImageResource(image)
                }
            }
        }
    }

    fun updateImageView(){
        gameController.runOnUiThread{
            run{
                imageView.layoutParams.width = width
                imageView.layoutParams.height = height
                imageView.x = xPosition - getWidth()/2f
                imageView.y = yPosition - getHeight()/2f
                var tempImageResource = image
                if (tempImageResource < 0){
                    tempImageResource *= -1
                    imageView.setImageResource(tempImageResource)
                    imageView.rotationY = 180f
                }else{
                    imageView.rotationY = 0f
                    imageView.setImageResource(image)
                }
            }
        }
    }
}