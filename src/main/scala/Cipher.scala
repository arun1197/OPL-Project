import scala.util.Random

object Cipher {

  private val KEY: Int = 947

  def encrypt(data: String): String = {
    var encrypted: String = ""
    for(i <- 0 to data.length) {
      encrypted += (data.charAt(i).toInt + KEY).asInstanceOf[Char]
    }
    encrypted
  }

  def decrypt(message: String): String = {
    var decrypted = ""
    for(i <- 0 to message.length) {
      decrypted += (message.charAt(i).toInt - KEY).asInstanceOf[Char]
    }
    decrypted
  }
}
