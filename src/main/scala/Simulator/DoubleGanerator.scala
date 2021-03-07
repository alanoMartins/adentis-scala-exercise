package Simulator

import java.util.concurrent.ThreadLocalRandom
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object DoubleGanerator {
  def sample(min:Int, max:Int, freq: Int): Future[Seq[Double]] = Future {
    for (_ <- 0 until freq)  yield ThreadLocalRandom.current().nextDouble(min, max)
  }
}