package com.example.jacobgraves.myapplication.model

import android.graphics.Color
import android.widget.ImageView
import com.example.jacobgraves.myapplication.GameController
import com.example.jacobgraves.myapplication.model.map.Room
import com.example.jacobgraves.myapplication.model.consumables.Coin
import com.example.jacobgraves.myapplication.model.consumables.Consumable
import com.example.jacobgraves.myapplication.model.consumables.HP
import com.example.jacobgraves.myapplication.model.consumables.SpeedUp
import com.example.jacobgraves.myapplication.model.guns.Gun
import com.example.jacobgraves.myapplication.model.guns.StartingPistol
import com.example.jacobgraves.myapplication.model.map.Wall
import kotlinx.android.synthetic.main.game_view.*


class Engine(var gameController: GameController, name:String) {
    var player: Player
    var frameCount: Int
    var startGun: Gun
    var hud:HUD
    var room: Room

    // Temporary
    val tempMapArea:ImageView
    val tempBackground:ImageView

    var enemies = ArrayList<Enemy>()
    var deadEnemies:Array<Enemy?>
    var deadEnemyCounter:Int
    var orphanBullets = ArrayList<Bullet>()
    var deadOrphans:Array<Bullet?>
    var deadOrphanCounter:Int
    var consumables:Array<Consumable?>
    var consumableCounter:Int
    var roomSchematic:Array<Array<Int>>

    init{
        // Temporary
        tempMapArea = ImageView(gameController)
        tempBackground = ImageView(gameController)

        gameController.runOnUiThread {
            run {
                gameController.constraintLayout.addView(tempBackground)
                tempBackground.layoutParams.width = (1920f * gameController.screenXRatio).toInt()
                tempBackground.layoutParams.height = (1080f * gameController.screenYRatio).toInt()
                tempBackground.x = 0 * gameController.screenXRatio
                tempBackground.y = 0 * gameController.screenYRatio
                tempBackground.setBackgroundColor(Color.DKGRAY)

                gameController.constraintLayout.addView(tempMapArea)
                tempMapArea.layoutParams.width = (Room.mapWidth * gameController.screenXRatio).toInt()
                tempMapArea.layoutParams.height = (Room.mapHeight * gameController.screenYRatio).toInt()
                tempMapArea.x = Room.mapX * gameController.screenXRatio
                tempMapArea.y = Room.mapY * gameController.screenYRatio
                tempMapArea.setBackgroundColor(Color.rgb(140, 119, 84))
            }
        }
        roomSchematic = Array(12){Array(20){0}}
        buildSchematic()

        room = Room(gameController, roomSchematic)

        player = Player(gameController,name)
        startGun = StartingPistol(gameController)
        frameCount = 0
        hud = HUD(gameController)
        enemies.add(Freezo(gameController))
        deadOrphans = Array<Bullet?>(10){null}
        deadOrphanCounter = 0
        deadEnemies = Array<Enemy?>(10){null}
        deadEnemyCounter = 0
        consumables = Array<Consumable?>(50){null}
        consumableCounter = 0
    }

    fun buildSchematic(){
        for(j in 0..roomSchematic[0].size-1){
            roomSchematic[0][j] = 1
        }
        for(j in 0..roomSchematic[11].size-1){
            roomSchematic[11][j] = 2
        }
        for (i in 0..roomSchematic.size-1){
            roomSchematic[i][0] = 3
            roomSchematic[i][19] = 4
        }
        roomSchematic[0][10] = 5
        roomSchematic[11][10] = 6
        roomSchematic[6][0] = 7
        roomSchematic[6][19] = 8
    }

    fun update(){
        if (frameCount == 60){
            //println("It's been 60 frames")
            frameCount = 0
        }
        if (frameCount % 10 == 0){
            updateAnimations()
        }
        frameCount += 1


        moveBullets()
        moveEnemies()
        updateImageViews()
        decelerate()
        updateHitboxes()
        checkForCollision()
        attackPlayer()
        checkDeaths()
        hud.updateViews()
    }

    fun moveEnemies(){
        for(enemy in enemies){
            if(enemy is Freezo){
                enemy.pursuePlayer(player)
            }
        }
    }

    fun decelerate(){
        player.decelerate()
        for(freezo in enemies){
            freezo.decelerate()
        }

        if(consumableCounter > 0){
            for(consumable in consumables){
                if (consumable != null){
                    consumable.decelerate()
                }
            }
        }
    }

    fun updateHitboxes() {
        player.updateHitbox()
        player.updateBulletHitboxes()

        for(enemy in enemies){
            enemy.updateHitbox()
            enemy.updateBulletHitboxes()
        }

        for(bullet in orphanBullets){
            bullet.updateHitbox()
        }

        if(consumableCounter > 0){
            for(consumable in consumables){
                if (consumable != null){
                    consumable.updateHitbox()
                }
            }
        }
    }

    fun checkForCollision(){
        //for each item check collision with player

        for(enemy in enemies){
            //check enemy player collision
            if(enemy.hitBox.intersect(player.hitBox)){

            }

            //check player bullet collisions
            for (bullet in player.bulletArray) {
                if (bullet != null) {
                    if(enemy.hitBox.intersect(bullet.hitBox)){
                        bullet.isAlive = false
                        enemy.setHealthValue(enemy.getHealthValue() - bullet.attackValue)
                        gameController.soundManager.soundPool.play(gameController.soundManager.arrow,1f,1f,5,0,1f)
                    }
                }
            }

            //check enemy bullet collisions
            for (bullet in enemy.bulletArray) {
                if (bullet != null) {
                    if(player.hitBox.intersect(bullet.hitBox)){
                        bullet.isAlive = false
                        player.setHealthValue(player.getHealthValue()-bullet.attackValue)
                    }
                }
            }
        }

        for(bullet in orphanBullets){
            if(player.hitBox.intersect(bullet.hitBox)){
                bullet.isAlive = false
                player.setHealthValue(player.getHealthValue()-bullet.attackValue)
            }
        }

        //For each consumable check for player collision
        if(consumableCounter > 0){
            for (i in 0..consumables.size-1){
                if(consumables[i] != null){
                    if(consumables[i]!!.hitBox.intersect(player.hitBox)){
                        consumables[i]!!.giveTo(player)
                        consumables[i]!!.kill()
                        consumables[i] = null
                        consumableCounter -= 1
                    }
                }
            }
        }
    }

    fun updateAnimations(){
        player.updateAnimations()
        player.updateBulletAnimations()
        startGun.updateBulletAnimations()

        for(enemy in enemies){
            enemy.updateAnimations()
            enemy.updateBulletAnimations()
        }

        for(bullet in orphanBullets){
            bullet.updateAnimations()
        }
    }

    fun moveBullets(){
        player.moveBullets()
        startGun.moveBullets()

        for(enemy in enemies){
            enemy.moveBullets()
        }

        for(bullet in orphanBullets){
            if(!bullet.isAlive){
                bullet.kill()
                deadOrphans[deadOrphanCounter] = bullet
                deadOrphanCounter++
                //orphanBullets.remove(bullet)
            }else{
                bullet.move()
                if(bullet.xPosition > 2200 || bullet.xPosition < -100 || bullet.yPosition > 1200 || bullet.yPosition < -100){
                    bullet.isAlive = false
                }
            }
        }

        if(deadOrphanCounter > 0){
            for(bullet in deadOrphans){
                if(bullet != null){
                    orphanBullets.remove(bullet)
                    deadOrphanCounter--
                }
            }
        }
    }

    fun attackPlayer(){
        for(enemy in enemies) {
            if(enemy is Freezo){
                enemy.attack(player)
            }
        }
    }

    fun updateImageViews(){
        //var start = System.currentTimeMillis()
        //room.updateImages()
        //var end = System.currentTimeMillis()
        //println(end - start)
        player.updateImageView()
        startGun.updateImageView()

        for(bullet in player.bulletArray){
            if(bullet != null){
                bullet.updateImageView()
            }
        }

        for(bullet in orphanBullets){
            bullet.updateImageView()
        }

        for (enemy in enemies){
            enemy.updateImageView()
            for(bullet in enemy.bulletArray){
                if(bullet != null){
                    bullet.updateImageView()
                }
            }
        }

        if(consumableCounter > 0){
            for (consumable in consumables){
                if (consumable != null){
                    consumable.updateImageView()
                }
            }
        }
    }

    fun checkDeaths(){
        if(player.getHealthValue() <= 0){
            //Do Something when player dies
        }

        for(enemy in enemies){
            if(enemy.getHealthValue() <= 0){
                for(bullet in enemy.bulletArray){
                    if(bullet != null) {
                        orphanBullets.add(bullet)
                    }
                }
                enemy.kill()
                deadEnemies[deadEnemyCounter] = enemy
                deadEnemyCounter += 1
                createConsumables(2,enemy)
            }
        }

        for(enemy in deadEnemies){
            if(enemy != null){
                enemies.remove(enemy)
            }
        }
        deadEnemyCounter = 0
    }

    fun createConsumable(enemy: Enemy): Consumable {
        var c: Consumable? = null
        val x:Int = Math.floor(Math.random()*10).toInt() + 1

        //c = HP(gameController, enemy.getXPosition(), enemy.getYPosition())

        if (x <= 4){
            c = HP(gameController, enemy.getXPosition(), enemy.getYPosition())
        }
        if(x >= 5 && x <= 8){
            c = Coin(gameController, enemy.getXPosition(), enemy.getYPosition())
        }
        if(x >= 9 && x <= 10){
            c = SpeedUp(gameController, enemy.getXPosition(), enemy.getYPosition())
        }

        consumables[consumableCounter] = c!!
        consumableCounter++
        return c
    }

    fun createConsumables(amount:Int, enemy:Enemy){
        for (i in 0..amount-1){
            createConsumable(enemy)
        }
    }
}

/*
    fun destroyImages(){
        var imagesToDestroy = ArrayList<ImageView>()

        //Temporary
        imagesToDestroy.add(tempBackground)
        imagesToDestroy.add(tempMapArea)

        imagesToDestroy.add(player.imageView)

        for (bullet in player.bulletArray){
            if (bullet != null){
                imagesToDestroy.add(bullet.imageView)
            }
        }

        for (enemy in enemies){
            if(enemy != null){
                imagesToDestroy.add(enemy.imageView)
                for(bullet in enemy.bulletArray){
                    if (bullet != null){
                        imagesToDestroy.add(bullet.imageView)
                    }
                }
            }
        }

        for (enemy in deadEnemies){
            if(enemy != null){
                imagesToDestroy.add(enemy.imageView)
            }
        }

        for (bullet in orphanBullets){
            if(bullet != null){
                imagesToDestroy.add(bullet.imageView)
            }
        }

        for(consumable in consumables){
            if (consumable != null){
                imagesToDestroy.add(consumable.imageView)
            }
        }

        while(!imagesToDestroy.isEmpty()){
            gameController.constraintLayout.removeView(imagesToDestroy.get(0))
            imagesToDestroy.removeAt(0)
        }
    }

    fun refreshGameController(gc: GameController){
        var imagesToRefresh = ArrayList<ImageView>()

        //Temporary
        imagesToRefresh.add(tempBackground)
        imagesToRefresh.add(tempMapArea)

        gameController = gc

        player.gameController = gc
        imagesToRefresh.add(player.imageView)

        for (bullet in player.bulletArray){
            if (bullet != null){
                bullet.gameController = gc
                imagesToRefresh.add(bullet.imageView)
            }
        }

        for (enemy in enemies){
            if(enemy != null){
                enemy.gameController = gc
                imagesToRefresh.add(enemy.imageView)
                for(bullet in enemy.bulletArray){
                    if (bullet != null){
                        bullet.gameController = gc
                        imagesToRefresh.add(bullet.imageView)
                    }
                }
            }
        }

        for (enemy in deadEnemies){
            if(enemy != null){
                enemy.gameController = gc
                imagesToRefresh.add(enemy.imageView)
            }
        }

        for (bullet in orphanBullets){
            if(bullet != null){
                bullet.gameController = gc
                imagesToRefresh.add(bullet.imageView)
            }
        }

        for(consumable in consumables){
            if (consumable != null){
                consumable.gameController = gc
                imagesToRefresh.add(consumable.imageView)
            }
        }

        while(!imagesToRefresh.isEmpty()){
            gameController.constraintLayout.addView(imagesToRefresh.get(0))
            imagesToRefresh.removeAt(0)
        }

        hud = HUD(gameController)
    }*/
