package fun.nya.recommend.model.config

class ALSModelConfig extends ModelConfig {
  var RANKS : Int = 20;
  var ITERATIONS : Int = 10;
  def getRanks() : Int = {
    RANKS
  }
  def setRanks(ranks : Int) = {
    RANKS = ranks
  }
  def getIterations() : Int = {
    ITERATIONS
  }
  def setIterations(iterations : Int) = {
    this.ITERATIONS = iterations
  }
}
