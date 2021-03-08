package Simulator

object ContactGenerator extends BatchSampleable[String] {

  override val data: Seq[String] = NameGenerator.data.map(_ + "@gmail.com")
}