import scala.util.Random

class Enemy {

  val ENEMY_NAMES: List[String] = List("Zombie", "Demon", "Witch", "Wizard", "Werewolf", "Orc")

  val MAXIMUM_ATTACK_DAMAGE: Int = 20

  val MAXIMUM_HEALTH: Int = 75

  val MINIMUM_HEALTH: Int = 1

  val RANDOM = new Random()

  private var health: Int = 0
  private var name: String = ""

  {
      name = ENEMY_NAMES(RANDOM.nextInt(ENEMY_NAMES.length))
      health = RANDOM.nextInt(MAXIMUM_HEALTH)
  }

  def attack(): Int = RANDOM.nextInt(MAXIMUM_ATTACK_DAMAGE)

  def takeDamage(damage: Int) = health = health - damage

  def getName(): String = name

  def getHealth(): Int = health

}
