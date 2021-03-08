package Simulator

object CategoryGenerator extends BatchSampleable[String] {
  override val data: Seq[String] = Seq("food", "higene", "electronics")
}