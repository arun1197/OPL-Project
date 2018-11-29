import java.io.{FileNotFoundException, IOException}

import scala.util.Random

object Game {

  val ATTACK: Int = 1 // Option for attacking
  val CONFIRMATION: String = "yesyeayupoksure" // String containing all confirmation answers
  val REFUTATION: String = "nopenah"
  val DELAY: Long = 2000 //delay for displaying messages
  val EXIT: Int = 5 //exit game option
  val MAXIMUM_GOLD_DROP: Int = 30 //Maximum gold you can receive
  val PENALTY_FOR_RUNNING: Int = 5 // penalty for running away
  val PENALTY_FOR_WRONG_INPUT: Int = 5 // penalty for wrong input
  val RANDOM: Random = new Random()
  val RUN: Int = 3 //Running away option
  val VISIT_STORE: Int = 4 //Visit store option
  val SCANNER = scala.io.StdIn
  val USE_POTION = 2 //Using potion option


  val SWORD_LIST: List[String] = List("wood","metal","gold")
  val ARMOUR_LIST: List[String] = List("leather","iron","gold")


  def main(args: Array[String]): Unit = {
    run()
  }

  def run(): Unit = {
    var player: Player = new Player()
    var pouch: Pouch = player.getPouch()

    // Game variables
    var armourDropChance: Int = 10
    var healthPotionDropChance: Int = 50
    var swordDropChance: Int = 10
    var running: Boolean = false
    var ranAway: Boolean = false

    // Game introduction
    println("\fWelcome to the dungeon.");
    print("Would you like to load your previous game? ")

    while(!running) {
      val loadGameState: String = SCANNER.readLine()
      if (CONFIRMATION.contains(loadGameState.toLowerCase())) {
        println("\nWhat is your name? ")
        var name: String = SCANNER.readLine()
        name = name.replaceAll("\\s+","")
        player.setName(name)
        try{
          State.loadState(player)
          running = true
        }
        catch {
          case exception: FileNotFoundException =>
            println("\nYour saved game was not found. Starting a new unsaved game.")
            running = true
            delay
          case exception: IOException =>
            println("Input from the keyboard could not be read. Please restart the game.")
        }
      }
      else if (REFUTATION.contains(loadGameState.toLowerCase)){
        running = true
      }else{
        println("Invalid command!")
        print("Would you like to load your previous game? ")
      }
    }


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
//            println("Invalid input type!")
        }
        choice match {
          case ATTACK => {
            ranAway = false
            var playerAttack: Int = player.attack()
            var enemyAttack: Int = villain.attack()
            println("\nYou attacked with " + playerAttack + " damage.")
            println("You took " + enemyAttack + " damage.")
            villain.takeDamage(playerAttack)
            player.takeDamage(enemyAttack)
            delay()
          }
          case USE_POTION => {
            if(player.getHealth() > player.FULL_HEALTH - player.POTION_HEALING) {
              println("\nYou are healthy, and do not need a potion.")
              delay()
            }
            else if(player.getPotions() > 0){
              player.usePotion()
              println("\nYou drank the potion. Health restored by: " + player.POTION_HEALING + " HP")
              println("Current HP: " + player.getHealth())
              delay()
            }else{
              println("You don't have any potions, buy some!")
              delay()
            }

          }
          case RUN => {
            /* pPENALTY FOR RUNNING AWAY BRUHHHH */
            if (player.getPouch.getCoins > PENALTY_FOR_RUNNING) {
              println("\n" + PENALTY_FOR_RUNNING + " coins were stolen by the " + villain.getName())
              pouch.removeCoins(PENALTY_FOR_RUNNING)
            }
            else {
              println("\nThe enemy did " + PENALTY_FOR_RUNNING + " damage before you managed to escape")
              player.takeDamage(PENALTY_FOR_RUNNING)
            }

            println("\nYou successfully ran away!")
            delay()

            villain.takeDamage(villain.getHealth())
            ranAway = true
          }
          case VISIT_STORE => {
            Store.printStore(player)
          }
          case EXIT => {
            println("\fExiting game...")
            print("Would you like to save your progress? ")
            if (CONFIRMATION.contains(SCANNER.readLine())) {
              State.saveState(player)
            }
            running = false
            return
          }
          case _ => {
            if (player.getPouch.getCoins > PENALTY_FOR_WRONG_INPUT) {
              println("Oops! wrong input, too bad we took " + PENALTY_FOR_WRONG_INPUT + " coins!")
              pouch.removeCoins(PENALTY_FOR_WRONG_INPUT)
            }
            else {
              println("Oops! wrong input, too bad we took " + PENALTY_FOR_WRONG_INPUT + " HP!")
              player.takeDamage(PENALTY_FOR_WRONG_INPUT)
            }
            delay()
          }
        }
        if (player.getHealth() <= 0){
          println("\nUh oh! You have died, game over. You're a n00b!")
          print("Would you like to restart the game? ")
          var continueGame: String = SCANNER.readLine()
          if (CONFIRMATION.contains(continueGame)){
            running = true
            player.reset()
          }
          else {
            println("\nProgram terminated.")
            villain.takeDamage(villain.getHealth())
            running = false
            return
          }
        }
      }

      if(!ranAway){
        player.increaseEnemiesKilled()
        pouch.addCoins(RANDOM.nextInt(MAXIMUM_GOLD_DROP))
        if (RANDOM.nextInt(100) < swordDropChance){
          if (player.getHasSword()){
            println("\nThe " + villain.getName() + " dropped a sword, but you already have one.")
          }
          else{
            player.addSword(SWORD_LIST(RANDOM.nextInt(3)))
            println("\nThe " + villain.getName() + " dropped a " + player.getSword().getName() + ".\nYour attack damage has now increased by " + player.getSword().getDamageIncrease() + ".")
          }
          delay()
        }
        else if (RANDOM.nextInt(100) < armourDropChance) {
          if (player.getHasArmour()) {
            println("\nThe " + villain.getName() + " dropped some armour, but you already have some.")
          }
          else {
            player.addArmour(ARMOUR_LIST(RANDOM.nextInt(3)))
            println("\nThe " + villain.getName() + " dropped " + player.getArmour().getName() + ".\nYour damage taken has now decreased by " + player.getArmour().getDamageBlocked() + ".")
          }
          delay()
        }
        else if (RANDOM.nextInt(100) < healthPotionDropChance) {
          player.addPotions(1)
          println("\nThe " + villain.getName() + " dropped a health potion.")
          delay()
        }
      }
    }
  }

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
    println("====================================================================")
    println("\f# A " + villain.getName() + " appeared #")
    println("\n# You have " + player.getHealth() + " HP #")
    println("# Enemy has " + villain.getHealth() + " HP #")
    println("# Potions left: " + player.getPotions() + " #")
    println("# Pouch has " + player.getPouch().getCoins() + " coins #")
    println("# Enemies killed: " + player.getEnemiesKilled() + " #")
    // Sword
    if (player.getHasSword()) {
      println("\n# Sword type: " + player.getSword().getName() + " | Sword hitpoints: " + player.getSword().getHitpoints() + "  #");
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
        println("\fThe game experienced an interrupted exception.")
        println("The game state was not saved.")
        println("Please restart the game.")
        System.exit(0)
    }
  }
}