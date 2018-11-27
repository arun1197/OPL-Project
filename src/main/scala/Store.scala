import java.util.InputMismatchException


object Store {

  val ITEM: List[String] = List("Sword", "Armour", "Potions")

  val PRICE: List[Int] = List(100, 50, 10)

  def printStore(player: Player): Unit = {
    println("====================================================================")
    println("\fWelcome to the store.\n")
    println("You have " + player.getPouch.getCoins + " coins.\n")

    for(i <- 0 to ITEM.length - 1) {
      println(i + 1 + ". " + ITEM(i) + ", Price: " + PRICE(i))
    }

    print("\nWhat would you like to buy? ")
    getInput(player)
  }

  def getInput(player: Player): Unit = {
    val SCANNER = scala.io.StdIn
    var itemIndex = 0
    try {
      itemIndex = SCANNER.readInt() - 1
      if (itemIndex >= 0 && itemIndex < ITEM.length) {
        buyItem(player, itemIndex)
      }
      else{
        println("\nExiting store...")
        TheDungeon.delay()
        return
      }
    } catch {
      case exception: NumberFormatException => //InputMismatchException
        println("\nExiting store...")
        TheDungeon.delay()
        return
    }
  }

  def buyItem(player: Player, itemIndex: Int): Unit = {
    if (player.getPouch.getCoins < PRICE(itemIndex)) {
      println("\nYou cannot afford " + ITEM(itemIndex) + ". Please purchase an afforable item.")
      TheDungeon.delay()
      printStore(player)
      return
    }
    itemIndex match {
      case 0 => player.addSword("wood")
      case 1 => player.addArmour("leather")
      case 2 => player.addPotions(1)
    }
    player.getPouch.removeCoins(PRICE(itemIndex))
    println("\nYou purchased: " + ITEM(itemIndex) + ", for " + PRICE(itemIndex) + " coins")
    TheDungeon.delay()
  }
}
