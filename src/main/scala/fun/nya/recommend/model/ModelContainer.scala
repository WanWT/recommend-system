package fun.nya.recommend.model

class ModelContainer(initModel : RecommendModel) {
  var model = initModel

  def getModel() = {
    model;
  }
  def setModel(newModel : RecommendModel) : Unit = {
    model.synchronized {
      this.model = newModel
    }
  }
}
