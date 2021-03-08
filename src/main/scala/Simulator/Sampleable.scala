package Simulator

import scala.concurrent.Future

trait Sampleable[T] {
  def sample(freq: Int): Future[Seq[T]]
}

