package Simulator

object CategoryGenerator extends GeneratorBase[String] {
  override def data: Seq[String] = Seq("food", "higene", "electronics")
}