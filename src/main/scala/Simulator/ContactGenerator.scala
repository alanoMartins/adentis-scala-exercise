package Simulator

object ContactGenerator extends GeneratorBase[String] {

  override def data: Seq[String] = NameGenerator.data.map(_ + "@gmail.com")
}