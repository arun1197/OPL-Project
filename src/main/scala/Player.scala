import scala.util.Random

class Player {

  /** Default number of potions of this player. */
  val DEFAULT_NUMBER_OF_POTIONS = 3

  /** The delay used for display messages to allow for readability. */
  val DELAY: Long = 1000

  /** Random number generator for this player. */
  val RANDOM: Random = new Random()

  /** The amount of HP one potion heals. */
  val POTION_HEALING: Int = 30

  /** Maximum health of this player. */
  val FULL_HEALTH: Int = 100

  /** The maximum attack damage of this player. */
  val MAXIMUM_ATTACK_DAMAGE: Int = 25

  /** The default name given to a player. */
  val NO_NAME: String = ""

  /* instance fields */
  private var armour: Armour = new Armour("clothes")
  private var enemiesKilled: Int = 0
  private var hasSword: Boolean = false
  private var hasArmour: Boolean = false
  private var health: Int = FULL_HEALTH
  private var pouch: Pouch = new Pouch()
  private var name: String = NO_NAME
  private var potionsRemaining: Int = DEFAULT_NUMBER_OF_POTIONS
  private var sword: Sword = new Sword("balloon")

  def getName(): String = {
    name
  }

  def getHealth(): Int = {
    health
  }

  def getEnemiesKilled(): Int = {
    enemiesKilled
  }

  def getPotions(): Int = {
    potionsRemaining
  }

  def getSword(): Sword = {
    sword
  }

  def getArmour(): Armour = {
    armour
  }

  def getPouch(): Pouch = {
    pouch
  }

  def getHasSword(): Boolean = {
    hasSword
  }

  def getHasArmour(): Boolean = {
    hasArmour
  }

  def setEnemiesKilled(enemiesKilled: Int) = {
    this.enemiesKilled = enemiesKilled
  }

  def setHealth(healthPoints: Int) = {
    if (healthPoints > 0 && healthPoints <= FULL_HEALTH) {
      health = healthPoints
      // end of if (healthPoints > 0)
    }
  }

  def setName(name: String)= {
    this.name = name
  }

  def setNumberOfPotions(potions: Int) = {
    if (potions >= 0) {
      potionsRemaining = potions
      // end of if (potions >= 0)
    }
  }

  def attack(): Int = {
    if (hasSword) {
      sword.useSword()
      if (sword.getHitpoints() <= 0){
        println("\nYour " + sword.getName() + " broke.")
        try {
          Thread.sleep(DELAY)
        }
        catch {
          case exception: InterruptedException =>
            println("The game experienced an interrupted exception.")
            println("The game data could not be saved.")
            println("Please restart the game.")
            System.exit(0)
        }
        hasSword = false
      }
      return RANDOM.nextInt(MAXIMUM_ATTACK_DAMAGE) + sword.getDamageIncrease()
    }
    return RANDOM.nextInt(MAXIMUM_ATTACK_DAMAGE)
  }

  def takeDamage(damage: Int) = {
    if (hasArmour){
      armour.useArmour()
      health = health - Math.max(damage - armour.getDamageBlocked(), 0) //max because when enemies attack is lesser than the armour damage blocked
      if (armour.getHitpoints() <= 0) {
        println("\nYour " + armour.getName() + " broke.")
        try {
          Thread.sleep(DELAY)
        }
        catch {
          case exception: InterruptedException =>
            println("The game experienced an interrupted exception.")
            println("The game data could not be saved.")
            println("Please restart the game.")
            System.exit(0)
        }
        hasArmour = false
      }
    }
    else {
      health = health - damage
    }
  }

  def usePotion(): Unit = {
    if (potionsRemaining <= 0) return
    health = health + POTION_HEALING
    potionsRemaining -= 1
  }

  def addPotions(potions: Int) = {
    potionsRemaining = potionsRemaining + potions
  }

  def increaseEnemiesKilled() = {
    enemiesKilled += 1
  }

  def addSword(str: String): Unit = {
    if (str == null) return
    sword = new Sword(str)
    hasSword = true
  }

  def addArmour(str: String): Unit = {
    if (str == null) return
    armour = new Armour(str)
    hasArmour = true
  }

  def getData(): String = name + " " + hasSword + " " + hasArmour + " " + enemiesKilled + " " + health + " " + potionsRemaining + " " + pouch.getCoins()

  def reset()= {
    health = FULL_HEALTH
    potionsRemaining = DEFAULT_NUMBER_OF_POTIONS
    enemiesKilled = 0
    hasSword = false
    hasArmour = false
  } // end of method reset()
}