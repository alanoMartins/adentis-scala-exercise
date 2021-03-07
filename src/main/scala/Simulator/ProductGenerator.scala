package Simulator

object ProductGenerator extends BatchGenerator[String] {
  override val data: Seq[String] = Seq("butter", "flour", "sugar", "water", "rice", "black beans", "shampoo", "toothpaste",
    "soap", "deodorant")
}