import java.io._
import java.io.FileNotFoundException
import java.io.PrintWriter
import java.io.IOException
import java.io.BufferedReader
import java.io.FileReader



object State {

  val SCANNER = scala.io.StdIn

  def saveState(player: Player): Unit = {
    /* Check if the user does not have a name, if so get the name */
    if (player.getName == "") {
      print("Enter your name: ")
      var name = SCANNER.readLine()
      /* Remove whitespace from the name. */
      name = name.replaceAll("\\s+", "")
      player.setName(name)
    }
    try {
      val userData: File = new File("users/" + player.getName + ".txt")
      userData.getParentFile().mkdirs
      val writer: PrintWriter = new PrintWriter(userData)
      val encrypted: String = Cipher.encrypt(player.getData)
      writer.println(encrypted)
      writer.close()
      println("Your data was saved. You may now safely close the console.")
    } catch {
      case exception: FileNotFoundException => println("File could not be written. Data was not saved.")
    }
  }

  @throws[FileNotFoundException]
  @throws[IOException]
  def loadState(player: Player): Unit = {

    /* Order of the data is [name, hasSword, hasArmour, enemiesKilled, health, numberOfPotions, coins] */
    val reader: BufferedReader = new BufferedReader(new FileReader("users/" + player.getName + ".txt"))

    /* Load saved decrypted player data */
    val data: Array[String] = Cipher.decrypt(reader.readLine).split(" ")
    reader.close()

    val name = data(0)
    val hasSword: Boolean = data(1).toBoolean
    val hasArmour: Boolean = data(2).toBoolean
    val enemiesKilled = data(3).toInt
    val health = data(4).toInt
    val numberOfPotions = data(5).toInt
    val coins = data(6).toInt

    /* Set the name of this player. */
    player.setName(name)

    /* Add a wood sword if the player had a sword. */
    if (hasSword){
      player.addSword("wood")
    }
    /* Add leather armour if the player had armour. */
    if (hasArmour){
      player.addArmour("leather")
    }
    /* Set the score of this player in terms of enemies killed. */
    player.setEnemiesKilled(enemiesKilled)

    /* Set the health points of this player based on the data. */
    player.setHealth(health)

    /* Set the number of potions of this player. */
    player.setNumberOfPotions(numberOfPotions)

    /* Set the coins of this player. */
    player.getPouch.setCoins(coins)
  }
}