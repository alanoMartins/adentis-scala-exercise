package Simulator

import java.util.concurrent.ThreadLocalRandom

object DoubleGenerator extends  OnlineGenerator[Double] {

  override def data(min: Option[Double], max: Option[Double]): Double = {
    (min, max) match {
      case (None, None) => ThreadLocalRandom.current().nextDouble(0, Double.MaxValue)
      case (Some(min), Some(max)) => ThreadLocalRandom.current().nextDouble(min, max)
      case _ => throw new IllegalArgumentException("Should pass a valid max and min limits")
    }
  }
}