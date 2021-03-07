package Simulator

object ProductGenerator extends GeneratorBase[String] {
  override def data: Seq[String] = Seq("butter", "flour", "sugar", "water", "rice", "black beans", "shampoo", "toothpaste",
    "soap", "deodorant")
}