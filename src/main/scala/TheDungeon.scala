import scala.util.Random
import java.io.FileNotFoundException
import java.io.IOException


object TheDungeon {

  val ATTACK: Int = 1 // Option for attacking
  val CONFIRMATION: String = "yesyyupoksureof course" // String containing all acceptable answers to yes and no question
  val DELAY: Long = 2000 //delay for displaying messages
  val EXIT: Int = 5 //exit game option
  val MAXIMUM_GOLD_DROP: Int = 30 //Maximum gold you can receive
  val PENALTY_FOR_RUNNING: Int = 5 // coin penalty for running away
  val RANDOM: Random = new Random()
  val RUN = 3 //Running away option
  val VISIT_STORE: Int = 4 //Visit store option
  val SCANNER = scala.io.StdIn
  val USE_POTION = 2 //Using potion option

  def main(args: Array[String]): Unit = {
    Pouch pouch = Player.getPouch()

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

    if (CONFIRMATION.contains(loadGameState)) {
      println("\nWhat is your name? ")
      var name: String = SCANNER.readLine()
      name = name.replaceAll("\\s+","")
      Player.setName(name)
      try
        State.loadState(player)
      catch {
        case exception: FileNotFoundException =>
          System.out.println("\nYour saved game was not found. Starting a new unsaved game.")
          delay
        case exception: IOException =>
          System.out.println("Input from the keyboard could not be read. Please restart the game.")
      }
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
