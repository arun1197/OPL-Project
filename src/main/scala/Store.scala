class Store {

  val ITEM: List[String] = List("Sword", "Armour", "Potions")

  val PRICE: List[Int] = List(100, 50, 10)

  def printStore(player: Player): Unit = {
    println("\fWelcome to the store.\n")
    println("You have " + player.getPouch.getCoins + " coins.\n")

    for(i <- 0 to ITEM.length) {
      println(i + 1 + ". " + ITEM(i) + ", Price: " + PRICE(i))
    }

    print("\nWhat would you like to buy? ")
    getInput(player)
  }

  import java.util.InputMismatchException

  def getInput(player: Player): Unit = {
    val SCANNER = scala.io.Stdin
//    val scanner = new Nothing(System.in)
    var itemIndex = 0
    try {
      itemIndex = SCANNER.nextInt() - 1
      if (itemIndex < 0 || itemIndex >= ITEM.length) return
    } catch {
      case exception: InputMismatchException =>
        println("\nExiting store...")
        TheDungeon.delay()
        return
    }
    buyItem(player, itemIndex)
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
