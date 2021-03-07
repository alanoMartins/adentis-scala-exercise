package Simulator

import scala.concurrent.Future

trait GeneratorBase[T] {
  def sample(freq: Int): Future[Seq[T]]
}

