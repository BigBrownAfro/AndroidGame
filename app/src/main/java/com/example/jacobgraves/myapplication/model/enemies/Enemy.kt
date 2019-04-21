package com.example.jacobgraves.myapplication.model.enemies

import android.graphics.RectF
import android.widget.ImageView
import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import com.example.jacobgraves.myapplication.model.guns.Bullet
import com.example.jacobgraves.myapplication.model.Player
import kotlinx.android.synthetic.main.game_view.*
import kotlin.math.*

abstract class Enemy(var gameController: GameController) {
    var name = "Enemy"
    private var healthValue :Int = 0
    private var attackValue :Int = 0
    private var movementSpeed :Float = 0.0f
    var accelerationX:Float = 0.0f
    var accelerationY:Float = 0.0f
    private var xPosition :Float = 0f
    private var yPosition :Float = 0f
    private var width:Int = 0
    private var height:Int = 0
    var image = R.drawable.isaac
    lateinit var moveLeftAnimationSet:IntArray
    lateinit var moveRightAnimationSet:IntArray
    lateinit var moveUpAnimationSet:IntArray
    lateinit var moveDownAnimationSet:IntArray
    var animationCounter:Int = 0
    var lastDirection = "down"
    var bulletArray:Array<Bullet?>
    var bulletCounter = 0
    var hitBox: RectF
    var sensorRadius = 300
    var moved = false

    val imageView:ImageView

    init{
        setHealthValue(5)
        setAttackValue(1)
        setMovementSpeed(1.0f)
        setXPosition(1000f)
        setYPosition(500f)
        setWidth(80)
        setHeight(160)
        bulletArray = Array(100){null}
        hitBox = RectF(getXPosition()-getWidth()/2,getYPosition()-getHeight()/2,getXPosition()+getWidth()/2,getYPosition()+getHeight()/2)

        assignImages()

        imageView = ImageView(gameController)
        setupImageView()
    }


    //Setters----------------------------
    fun setHealthValue(x: Int){
        if (x < 0){
            healthValue = 0
        }else {
            healthValue = x
        }
    }

    fun setAttackValue(x: Int){
        if (x < 0){
            attackValue = 0
        }else{
            attackValue = x
        }
    }

    fun setMovementSpeed(x: Float){
        if (x < 0){
            movementSpeed = 0.0f
        }else{
            movementSpeed = x
        }
    }

    fun setXPosition(x: Float){
        if (x < 0){
            xPosition = 0.0f
        }else{
            xPosition = x
        }
    }

    fun setYPosition (x: Float){
        if (x < 0){
            yPosition = 0.0f
        }else{
            yPosition = x
        }
    }

    fun setWidth (x: Int){
        if (x < 0){
            width = 100
        }else{
            width = x
        }
    }

    fun setHeight (x: Int){
        if (x < 0){
            height = 200
        }else{
            height = x
        }
    }



    //Getters----------------------------
    fun getHealthValue(): Int{
        return healthValue
    }

    fun getAttackValue(): Int{
        return attackValue
    }

    fun getMovementSpeed(): Float{
        return movementSpeed
    }

    fun getXPosition(): Float{
        return xPosition
    }

    fun getYPosition (): Float{
        return yPosition
    }

    fun getWidth (): Int{
        return width
    }

    fun getHeight (): Int{
        return height
    }


//Other Stuff------------------------

    fun kill(){
        gameController.runOnUiThread{
            run{
                gameController.constraintLayout.removeView(imageView)
            }
        }
    }

    fun inRadius(player: Player): Boolean{
        var inRadius = false
        var xDifference = player.getXPosition() - xPosition
        xDifference = xDifference.absoluteValue
        var yDifference = yPosition - player.getYPosition()
        yDifference = yDifference.absoluteValue
        var hypotenuse = hypot(xDifference, yDifference)
        if(hypotenuse <= sensorRadius){
            inRadius = true
        }
        return inRadius
    }

    fun assignImages(){
        image = R.drawable.mario_peace
        moveLeftAnimationSet = IntArray(3)
        moveLeftAnimationSet[0] = R.drawable.mario_stand
        moveLeftAnimationSet[1] = R.drawable.mario_run_1
        moveLeftAnimationSet[2] = R.drawable.mario_run_2
        moveRightAnimationSet = IntArray(3)
        moveRightAnimationSet[0] = (-1) * R.drawable.mario_stand
        moveRightAnimationSet[1] = (-1) * R.drawable.mario_run_1
        moveRightAnimationSet[2] = (-1) * R.drawable.mario_run_2
        moveUpAnimationSet = IntArray(4)
        moveUpAnimationSet[0] = R.drawable.mario_run_up_1
        moveUpAnimationSet[1] = R.drawable.mario_run_up_2
        moveUpAnimationSet[2] = R.drawable.mario_run_up_1
        moveUpAnimationSet[3] = R.drawable.mario_run_up_3
        moveDownAnimationSet = IntArray(4)
        moveDownAnimationSet[0] = R.drawable.mario_face_forward
        moveDownAnimationSet[1] = R.drawable.mario_run_down_1
        moveDownAnimationSet[2] = R.drawable.mario_face_forward
        moveDownAnimationSet[3] = R.drawable.mario_run_down_2
    }

    fun move(angle: Float){
        accelerationX += movementSpeed * cos(angle)
        accelerationY -= movementSpeed * sin(angle)

        if(hypot(accelerationX,accelerationY) < 8f * movementSpeed){
            xPosition += accelerationX
            yPosition += accelerationY
            moved = true
        }else{
            accelerationX -= movementSpeed * cos(angle)
            accelerationY += movementSpeed * sin(angle)
        }
    }

    fun moveUp(){
        accelerationY -= 2f * movementSpeed;
        if (accelerationY < (-1f) * 7f * movementSpeed){
            accelerationY = (-1f) * 7f * movementSpeed
        }
        yPosition += accelerationY;
    }

    fun moveDown(){
        accelerationY += 2f * movementSpeed;
        if (accelerationY > 7f * movementSpeed){
            accelerationY = 7f * movementSpeed
        }
        yPosition += accelerationY;
    }

    fun moveLeft(){
        accelerationX -= 2f * movementSpeed;
        if (accelerationX < (-1f) * 7f * movementSpeed){
            accelerationX = (-1f) * 7f * movementSpeed
        }
        xPosition += accelerationX;
    }

    fun moveRight(){
        accelerationX += 2f * movementSpeed;
        if (accelerationX > 7f * movementSpeed){
            accelerationX = 7f * movementSpeed
        }
        xPosition += accelerationX;
    }

    fun decelerate(){
        accelerationX *= .95f
        accelerationY *= .95f
        if(accelerationX < .1f && accelerationX > -.1f){
            accelerationX = 0f
        }
        if(accelerationY < .1f && accelerationY > -.1f){
            accelerationY = 0f
        }
        if (!moved){
            xPosition += accelerationX
            yPosition += accelerationY
        }
        moved = false
    }

    fun updateAnimations(){
        if(accelerationX > 0f){
            if (animationCounter >= moveRightAnimationSet.size){
                animationCounter = 0
            }
            image = moveRightAnimationSet[animationCounter]
            lastDirection ="right"
            animationCounter += 1
        }else
        if(accelerationX < 0f){
            if (animationCounter >= moveLeftAnimationSet.size){
                animationCounter = 0
            }
            image = moveLeftAnimationSet[animationCounter]
            lastDirection ="left"
            animationCounter += 1
        }else
        if(accelerationY < 0f){
            if (animationCounter >= moveUpAnimationSet.size){
                animationCounter = 0
            }
            image = moveUpAnimationSet[animationCounter]
            lastDirection ="up"
            animationCounter += 1
        }else
        if(accelerationY > 0f){
            if (animationCounter >= moveDownAnimationSet.size){
                animationCounter = 0
            }
            image = moveDownAnimationSet[animationCounter]
            lastDirection ="down"
            animationCounter += 1
        }else
        if(accelerationX == 0f){
            if(lastDirection.equals("up")){
                image = R.drawable.mario_run_up_1
            }else if(lastDirection.equals("down")){
                image = R.drawable.mario_face_forward
            }else if(lastDirection.equals("left")){
                image = R.drawable.mario_stand
            }else if(lastDirection.equals("right")){
                image = R.drawable.mario_stand * (-1)
            }else{
                image = R.drawable.mario_peace
            }
        }
    }

    fun shoot(angle:Float){
        if (bulletCounter > 99){
            bulletCounter = 0
        }
        bulletArray[bulletCounter] = Bullet(gameController, this, angle)
        bulletCounter++
    }

    fun moveBullets(){
        for (bullet in bulletArray){
            if (bullet != null){
                if(!bullet.isAlive){
                    bullet.kill()
                    bulletArray[bulletArray.indexOf(bullet)] = null
                }else{
                    bullet.move()
                    if(bullet.xPosition > 2200 || bullet.xPosition < -100 || bullet.yPosition > 1200 || bullet.yPosition < -100){
                        bullet.isAlive = false
                    }
                }
            }
        }
    }

    fun updateBulletAnimations(){
        for (bullet in bulletArray){
            if (bullet != null){
                bullet.updateAnimations()
            }
        }
    }

    fun updateHitbox(){
        hitBox.set(getXPosition()-getWidth()/2,getYPosition()-getHeight()/2,getXPosition()+getWidth()/2,getYPosition()+getHeight()/2)
    }

    fun updateBulletHitboxes(){
        var count = 0
        for (bullet in bulletArray) {
            if (bullet != null) {
                bullet.updateHitbox()
            }
            count++
        }
    }

    fun setupImageView(){
        gameController.runOnUiThread{
            run{
                gameController.constraintLayout.addView(imageView)

                imageView.layoutParams.width = (width * gameController.screenXRatio).toInt()
                imageView.layoutParams.height = (height * gameController.screenYRatio).toInt()
                imageView.x = (getXPosition() - getWidth()/2f) * gameController.screenXRatio
                imageView.y = (getYPosition() - getHeight()/2f) * gameController.screenYRatio
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
                imageView.layoutParams.width = (width * gameController.screenXRatio).toInt()
                imageView.layoutParams.height = (height * gameController.screenYRatio).toInt()
                imageView.x = (getXPosition() - getWidth()/2f) * gameController.screenXRatio
                imageView.y = (getYPosition() - getHeight()/2f) * gameController.screenYRatio
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