package com.example.jacobgraves.myapplication.model

import android.widget.ImageView
import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.R
import com.example.jacobgraves.myapplication.R.id.imageView
import kotlinx.android.synthetic.main.game_view.*

abstract class Gun (val gameController: GameController) {
    var name = "Gun"
    private var damageValue: Int = 1
    private var accuracy: Float = 1f
    private var xPosition: Float = 1f
    private var yPosition: Float = 1f
    private var width: Int = 35
    private var height: Int = 40
    private var currentAmmo: Int = 1
    private var maxAmmo: Int = 1
    var isPickedUp: Boolean = false
    var image = R.drawable.testgun
    var bulletArray: Array<Bullet?>
    lateinit var shootAnimationSet: IntArray
    var animationCounter: Int = 0
    val imageView: ImageView
    var maxReload = 20
    var reloadTime = 0
    var bulletCounter = 0

    init {
        setMaxAmmo(50)
        setCurrentAmmo(50)
        setDamageValue(1)
        setAccuracy(1.0f)
        setXPosition(750f)
        setYPosition(750f)
        setWidth(40)
        setHeight(35)
        bulletArray = Array(500) { null }

        assignImages()

        imageView = ImageView(gameController)

        setupImageView()
    }

    //Setters----------------------------

    fun setDamageValue(x: Int) {
        if (x < 0) {
            damageValue = 0
        } else {
            damageValue = x
        }
    }

    fun setMaxAmmo(x: Int) {
        if (x < 1) {
            maxAmmo = 1
        } else {
            maxAmmo = x
        }
    }

    fun setCurrentAmmo(x: Int) {
        if (x < 0) {
            currentAmmo = 0
        } else {
            currentAmmo = x
        }
    }

    fun setAccuracy(x: Float) {
        if (x <= 0) {
            accuracy = 1f
        } else {
            accuracy = x
        }
    }

    fun setXPosition(x: Float) {
        if (x < 0) {
            xPosition = 0.0f
        } else {
            xPosition = x
        }
    }

    fun setYPosition(x: Float) {
        if (x < 0) {
            yPosition = 0.0f
        } else {
            yPosition = x
        }
    }

    fun setWidth(x: Int) {
        if (x < 0) {
            width = 100
        } else {
            width = x
        }
    }

    fun setHeight(x: Int) {
        if (x < 0) {
            height = 200
        } else {
            height = x
        }
    }

    //Getters----------------------------

    fun getDamageValue(): Int {
        return damageValue
    }

    fun getMaxAmmo(): Int {
        return maxAmmo
    }

    fun getCurrentAmmo(): Int {
        return currentAmmo
    }

    fun getAccuracy(): Float {
        return accuracy
    }

    fun getXPosition(): Float {
        return xPosition
    }

    fun getYPosition(): Float {
        return yPosition
    }

    fun getWidth(): Int {
        return width
    }

    fun getHeight(): Int {
        return height
    }

    //reload, shoot, setup images, update images

    fun assignImages(){
        image = R.drawable.testgun
        shootAnimationSet = IntArray(3)
        shootAnimationSet[0] = R.drawable.testgun
        shootAnimationSet[1] = R.drawable.testgunfire
        shootAnimationSet[2] = R.drawable.testgun
    }


    fun setupImageView() {
        gameController.runOnUiThread {
            run {
                gameController.constraintLayout.addView(imageView)

                imageView.layoutParams.width = (width * gameController.screenXRatio).toInt()
                imageView.layoutParams.height = (height * gameController.screenYRatio).toInt()
                imageView.x = (getXPosition() - getWidth() / 2f) * gameController.screenXRatio
                imageView.y = (getYPosition() - getHeight() / 2f) * gameController.screenYRatio

                imageView.setImageResource(image)


            }
        }
    }

    fun updateImageView() {
        gameController.runOnUiThread {
            run {
                gameController.constraintLayout.addView(imageView)

                imageView.layoutParams.width = (width * gameController.screenXRatio).toInt()
                imageView.layoutParams.height = (height * gameController.screenYRatio).toInt()
                imageView.x = (getXPosition() - getWidth() / 2f) * gameController.screenXRatio
                imageView.y = (getYPosition() - getHeight() / 2f) * gameController.screenYRatio

                imageView.setImageResource(image)

                //reload, shoot, setup images, update images.
            }
        }
    }

    fun shoot(angle: Float){
        if(reloadTime <= 0){
            //if(mediaPlayer.isPlaying){
            //   mediaPlayer.pause()
            //}
            if (bulletCounter >= bulletArray.size){
                bulletCounter = 0
            }
            bulletArray[bulletCounter] = Bullet(gameController,gameController.gameEngine.player,angle)
            bulletCounter++
            //mediaPlayer.start()
            gameController.soundManager.soundPool.play(gameController.soundManager.pisto1,1f,1f,5,0,1f)
            reloadTime = maxReload
        }
        reloadTime--
    }
}