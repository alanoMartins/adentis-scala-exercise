package Simulator

import java.util.concurrent.ThreadLocalRandom

object DoubleGanerator {
  def sample(min:Int, max:Int, freq: Int): Seq[Double] = {
    for (_ <- 0 until freq)  yield ThreadLocalRandom.current().nextDouble(min, max)
  }
}