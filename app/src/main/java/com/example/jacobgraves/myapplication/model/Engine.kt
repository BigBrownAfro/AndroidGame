package com.example.jacobgraves.myapplication.model

import com.example.jacobgraves.myapplication.GameController


class Engine(val gameController: GameController, name:String) {
    var player: Player
    var frameCount: Int
    /*
    Make an array for dead enemies so you don't remove enemy while iterating over enemies
     */
    var enemies = ArrayList<Enemy>()
    var deadEnemies:Array<Enemy?>
    var deadEnemyCounter:Int
    var orphanBullets = ArrayList<Bullet>()
    var deadOrphans:Array<Bullet?>
    var deadOrphanCounter:Int

    init{
        player = Player(gameController,name)
        frameCount = 0
        enemies.add(Freezo(gameController))
        /*enemies.add(Freezo())
        enemies[1].setXPosition(1720f)
        enemies[1].setYPosition(980f)
        enemies[1].followRadius += 100*/
        deadOrphans = Array<Bullet?>(10){null}
        deadOrphanCounter = 0
        deadEnemies = Array<Enemy?>(10){null}
        deadEnemyCounter = 0
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
        updateGUI()
    }

    fun updateGUI(){
        player.updateGUI()
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
    }

    fun checkForCollision(){
        //for each item check collision with player
        //for each consumable check collision with player

        for(enemy in enemies){
            //check enemy player collision
            if(enemy.hitBox.intersect(player.hitBox)){

            }

            //check player bullet collisions
            for (bullet in player.bulletArray) {
                if (bullet != null) {
                    if(enemy.hitBox.intersect(bullet.hitBox)){
                        bullet.xPosition = -222f
                        enemy.setHealthValue(enemy.getHealthValue() - bullet.attackValue)
                        gameController.soundManager.soundPool.play(gameController.soundManager.arrow,1f,1f,5,0,1f)
                    }
                }
            }

            //check enemy bullet collisions
            for (bullet in enemy.bulletArray) {
                if (bullet != null) {
                    if(player.hitBox.intersect(bullet.hitBox)){
                        bullet.xPosition = -1000f
                        player.setHealthValue(player.getHealthValue()-bullet.attackValue)
                    }
                }
            }
        }

        for(bullet in orphanBullets){
            if(player.hitBox.intersect(bullet.hitBox)){
                bullet.xPosition = -1000f
                player.setHealthValue(player.getHealthValue()-bullet.attackValue)
            }
        }
    }

    fun updateAnimations(){
        player.updateAnimations()
        player.updateBulletAnimations()

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
        player.updateImageView()

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
    }

    fun checkDeaths(){
        if(player.getHealthValue() <= 0){
            //Do Something
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
            }
        }
        for(enemy in deadEnemies){
            if(enemy != null){
                enemies.remove(enemy)
            }
        }
    }
}