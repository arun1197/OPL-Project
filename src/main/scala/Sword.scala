object Sword {
  val WOOD_HITPOINTS: Int = 10

  val WOOD_DAMAGE_INCREASE: Int = 5

  val METAL_HITPOINTS: Int = 15

  val METAL_DAMAGE_INCREASE: Int = 10

  val GOLD_HITPOINTS: Int = 20

  val GOLD_DAMAGE_INCREASE: Int = 15


  private var name: String = ""
  private var hitpoints: Int = 0
  private var damageIncreased: Int = 0


  class Sword(str: String) {

    if(str == null) ""

    name = str + " sword"

    str match {
      case "wood" =>
        hitpoints = WOOD_HITPOINTS
        damageIncreased = WOOD_DAMAGE_INCREASE

      case "metal" =>
        hitpoints = METAL_HITPOINTS
        damageIncreased = METAL_DAMAGE_INCREASE

      case "gold" =>
        hitpoints = GOLD_HITPOINTS
        damageIncreased = GOLD_DAMAGE_INCREASE

      case _ =>
        name = "wood sword"
        hitpoints = WOOD_HITPOINTS
        damageIncreased = WOOD_DAMAGE_INCREASE
    }
  }

  def getName(): String = name

  def getHitpoints(): Int = hitpoints

  def damageIncrease(): Int = damageIncrease

  def setName(name: String) = this.name = name

  def useSword() = hitpoints -= 1

  def repairSword(hitpointsToRepair: Int) = hitpoints = hitpoints + hitpointsToRepair

}
