package fun.nya.recommend.test

import java.io.File

object FileOperationTest {
  def main(args : Array[String]) = {
    val file = new File("./modelSaved")
    deleteModelFile(file)
    println(file.exists() + file.getAbsolutePath)
  }
  def deleteModelFile(file : File) : Unit = {
    if(file.listFiles() != null) {
      for (subFile <- file.listFiles()) {
        val result = deleteModelFile(subFile)
      }
    }
    file.delete()
  }
}
