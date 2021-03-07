package Simulator

object ContactGenerator extends BatchGenerator[String] {

  override val data: Seq[String] = NameGenerator.data.map(_ + "@gmail.com")
}