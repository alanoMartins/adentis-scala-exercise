package Simulator

import java.time.LocalDate
import scala.util.Random

trait GeneratorBase[T] {

  def data: Seq[T]

  def sample(freq: Int): Seq[T] = {
    require(!data.isEmpty, "List is empty")
    for (i <- 0 until freq)  yield data(Random.nextInt(data.length))
  }
}

