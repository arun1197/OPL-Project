import java.io._
object State {

  val scanner = scala.io.Stdin

  import java.io.FileNotFoundException
  import java.io.PrintWriter

  def saveState(player: Player): Unit = {
    /* Check if the user does not have a name, if so get the name */ if (player.getName == "") {
      System.out.print("Enter your name: ")
      var name = scanner.nextLine()
      /* Remove whitespace from the name. */ name = name.replaceAll("\\s+", "")
      player.setName(name)
    }
    try {
      val userData = new Nothing("users/" + player.getName + ".txt")
      userData.getParentFile().mkdirs
      val writer = new PrintWriter(new File(userData))
      val encrypted = Cipher.encrypt(player.getData)
      writer.println(encrypted)
      writer.close()
      println("Your data was saved. You may now safely close the console.")
    } catch {
      case exception: FileNotFoundException => println("File could not be written. Data was not saved.")
    }

  }

}
