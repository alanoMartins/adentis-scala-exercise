package Simulator

object ProductGenerator extends GeneratorBase[String] {
  override def data: Seq[String] = Seq("P1", "P2")
}