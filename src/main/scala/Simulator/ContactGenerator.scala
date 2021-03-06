package Simulator

import java.util.concurrent.ThreadLocalRandom

object ContactGenerator extends GeneratorBase[String] {
  def contact: String = {
    Iterator.continually("447700900" + f"${ThreadLocalRandom.current.nextInt(999)}%03d").toString()
  }

  override def data: Seq[String] = for (_ <- 0 until 10) yield contact
}