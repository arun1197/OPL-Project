import scala.util.Random

object Cipher {

  private val KEY: Int = 123

  def encrypt(data: String): String = {
    var encrypted: String = ""
    for(i <- 0 to data.length - 1) {
      encrypted += (data.charAt(i).asInstanceOf[Int] + KEY).asInstanceOf[Char]
    }
    encrypted
  }

  def decrypt(message: String): String = {
    var decrypted = ""
    for(i <- 0 to message.length - 1) {
      decrypted += (message.charAt(i).asInstanceOf[Int] - KEY).asInstanceOf[Char]
    }
    decrypted
  }
}
