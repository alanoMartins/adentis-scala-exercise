package Simulator

object CategoryGenerator extends BatchGenerator[String] {
  override val data: Seq[String] = Seq("food", "higene", "electronics")
}