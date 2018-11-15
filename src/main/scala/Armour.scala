object Armour {
  val LEATHER_HITPOINTS: Int = 5
  val LEATHER_DAMGAGE_BLOCKED: Int = 2

  val IRON_HITPOINTS: Int = 10
  val IRON_DAMAGE_BLOCKED: Int = 5

  val GOLD_HITPOINTS: Int = 20
  val GOLD_DAMAGE_BLOCKED: Int = 10

  private var name: String = ""
  private var hitpoints: Int = 0
  private var damageBlocked: Int = 0


  class Armour(str: String) {

    if(str == null) ""

    name = str + " armour"

    str match {
      case "leather" =>
        hitpoints = LEATHER_HITPOINTS
        damageBlocked = LEATHER_DAMGAGE_BLOCKED
      case "iron" =>
        hitpoints = IRON_HITPOINTS
        damageBlocked = IRON_DAMAGE_BLOCKED

      case "gold" =>
        hitpoints = GOLD_HITPOINTS
        damageBlocked = GOLD_DAMAGE_BLOCKED

      case _ =>
        name = "clothes"
        hitpoints = 0
        damageBlocked = 0
    }
  }

  def getDamageBlocked(): Int = damageBlocked

  def getHitpoints(): Int = hitpoints

  def getName(): String = name

  def setName(name: String) = this.name = name

  def useArmour() = hitpoints = hitpoints - 1

  def repairArmour(hitpointsToRepair: Int) = hitpoints = hitpoints + hitpointsToRepair

}
