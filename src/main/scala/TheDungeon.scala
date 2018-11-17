import scala.util.Random

object TheDungeon {

  val ATTACK: Int = 1 // Option for attacking
  val CONFIRMATION: String = "yesyyupoksureof course" // String containing all acceptable answers to yes and no question
  val DELAY: Long = 2000 //delay for displaying messages
  val EXIT: Int = 5 //exit game option
  val MAXIMUM_GOLD_DROP: Int = 30 //Maximum gold you can receive
  val PENALTY_FOR_RUNNING: Int = 5 // coin penalty for running away
  val RANDOM: Random = new Random()
  val RUN: Int = 3 //Running away option
  val VISIT_STORE: Int = 4 //Visit store option
  val SCANNER = scala.io.StdIn
  val USE_POTION = 2 //Using potion option

  def main(args: Array[String]): Unit = {
    var player: Player = new Player()
    var pouch: Pouch = player.getPouch()

    // Game variables
    /* The following three chance variables are percentages */
    var armourDropChance: Int = 10
    var healthPotionDropChance: Int = 50
    var swordDropChance: Int = 10
    var running: Boolean = true
    var ranAway: Boolean = false

    // Game introduction
    println("\fWelcome to the dungeon.");
    System.out.print("Would you like to load your previous game? ")
    val loadGameState: String = SCANNER.readLine()

//    if (CONFIRMATION.contains(loadGameState)) {
//      println("\nWhat is your name? ")
//      var name: String = SCANNER.readLine()
//      name = name.replaceAll("\\s+","")
//      player.setName(name)
//      try
//        State.loadState(player)
//      catch {
//        case exception: FileNotFoundException =>
//          System.out.println("\nYour saved game was not found. Starting a new unsaved game.")
//          delay
//        case exception: IOException =>
//          System.out.println("Input from the keyboard could not be read. Please restart the game.")
//      }
//    }

    while (running) {
      var villain: Enemy = new Enemy()
      while(villain.getHealth() > 0) {
        printStatistics(player,villain)
        startBattle()
        var choice: Int = 0
        try {
          choice = Integer.parseInt(SCANNER.readLine())
        }
        catch {
          case exception: NumberFormatException =>
            choice = RUN
        }
        choice match {
          case ATTACK => {
            ranAway = false
            var playerAttack: Int = player.attack()
            var enemyAttack: Int = villain.attack()
            println("\nYou dealt " + playerAttack + " damage.")
            println("You took " + enemyAttack + " damage.")
            villain.takeDamage(playerAttack)
            player.takeDamage(enemyAttack)
            delay()
          }
          case USE_POTION => {
            if(player.getHealth() > player.FULL_HEALTH - player.POTION_HEALING) {
              println("\nYou are healthy, and do not need a potion.")
              TheDungeon.delay()
            }
            player.usePotion()
            println("\nYou drank the potion. Health restored by: " + player.POTION_HEALING + " HP")
            println("Current HP: " + player.getHealth())
            delay()
          }
          case RUN => {
            /* Penalize the player by removing their coins or health */
            if (player.getPouch.getCoins > PENALTY_FOR_RUNNING) {
              println("\n" + PENALTY_FOR_RUNNING + " coins were stolen by the " + villain.getName())
              pouch.removeCoins(PENALTY_FOR_RUNNING)
            }
            /* Player does not have enough coins. Take away health instead of coins. */
            else {
              println("\nThe enemy did " + PENALTY_FOR_RUNNING + " damage before you managed to escape")
              player.takeDamage(PENALTY_FOR_RUNNING)
            } // end of if (player.getPouch().getCoins() > PENALTY_FOR_RUNNING)

            println("\nYou successfully ran away!")
            delay()

            /* Kill the enemy by dealing damage equivalent to its health. */
            villain.takeDamage(villain.getHealth())
            ranAway = true
          }
//          case VISIT_STORE => {
//            /* Print the store options. */
//            Store.printStore(player)
//          }
          case EXIT => {
            println("\fExiting game...")
            print("Would you like to save your progress? ")
//            if (CONFIRMATION.contains(SCANNER.readLine())) {
//              State.saveState(player)
//            } // end of if (CONFIRMATION.contains(SCANNER.nextLine()))
            running = false
            return
          }
        }// end of choice match
        if (player.getHealth() <= 0){
          println("\nUh oh! You have died, game over.")
          print("Would you like to respawn? ")
          var continueGame: String = SCANNER.readLine()
          if (CONFIRMATION.contains(continueGame)){
            running = true
            player.reset()
          }
          else {
            println("\nProgram terminated.")
            /* Kill the enemy by dealing damage equivalent to its health. */
            villain.takeDamage(villain.getHealth())
            running = false
            return
          }// end of else
        }// end of if (player.health() <= 0)
      } //end of while loop (villain.gethealth))

      if(!ranAway){
        /* The enemy has died and the player did not run away. This means the player killed the enemy. Reward the player. */
        player.increaseEnemiesKilled()
        /* Give the player some gold for killing the enemy. */
        pouch.addCoins(RANDOM.nextInt(MAXIMUM_GOLD_DROP))
        if (RANDOM.nextInt(100) < swordDropChance){
          if (player.getHasSword()){
            println("\nThe " + villain.getName() + " dropped a sword, but you already have one.")
          }
          else{
            player.addSword("")
            println("\nThe " + villain.getName() + " dropped a " + player.getSword().getName() + ".\nYour attack damage has now increased by " + player.getSword().damageIncrease() + ".")
          }
          delay()
        } // end of if (RANDOM.nextInt(100) < swordDropChance)
        else if (RANDOM.nextInt(100) < armourDropChance) {
          if (player.getHasArmour()) {
            println("\nThe " + villain.getName() + " dropped some armour, but you already have some.")
          }
          else {
            player.addArmour("leather")
            println("\nThe " + villain.getName() + " dropped " + player.getArmour().getName() + ".\nYour damage taken has now decreased by " + player.getArmour().getDamageBlocked() + ".")
          }
          delay()
        }
        else if (RANDOM.nextInt(100) < healthPotionDropChance) {
          player.addPotions(1)
          println("\nThe " + villain.getName() + " dropped a health potion.")
          delay()
        }//end of else if
      }//end of (!runAway)
    } //end of while(running)
  }//end of main

  def startBattle() = {
    println("\n1. Attack.")
    println("2. Use potion.")
    println("3. Run!")
    println("4. Visit Store.")
    println("5. Exit Game.")
    print("\nChoice? ")
  }

  def printStatistics(player: Player, villain: Enemy) = {
    //Statistics
    println("\f# A " + villain.getName() + " appeared #")
    println("\n# You have " + player.getHealth() + " HP #")
    println("# Enemy has " + villain.getHealth() + " HP #")
    println("# Potions left: " + player.getPotions() + " #")
    println("# Pouch has " + player.getPouch().getCoins() + " coins #")
    println("# Enemies killed: " + player.getEnemiesKilled() + " #")
    // Sword
    if (player.getHasSword()) {
      println("\n# Sword type: " + player.getSword().getName() + " | hitpoints: " + player.getSword().getHitpoints() + "  #");
    }
    // Armour
    if (player.getHasArmour()) {
      println("\n# Armour type: " + player.getArmour().getName() + " | Armour hitpoints: " + player.getArmour().getHitpoints() + "  #");
    }
  }

  def delay(): Unit = {
    try {
      Thread.sleep(DELAY)
      }
    catch {
      case exception: InterruptedException =>
        System.out.println("\fThe game experienced an interrupted exception.")
        System.out.println("The game state was not saved.")
        System.out.println("Please restart the game.")
        System.exit(0)
    } // end of catch (InterruptedException)
  }// end of method delay()}
}
