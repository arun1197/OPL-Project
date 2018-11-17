class Pouch {

  private var coins: Int = 0

  {
    coins = 0
  }

  def getCoins(): Int = coins

  def addCoins(coins: Int) = this.coins += coins

  def removeCoins(coins: Int) = this.coins -= coins

  def setCoins(coins: Int) = this.coins = coins

}