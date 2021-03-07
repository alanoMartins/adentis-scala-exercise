package Simulator

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait OnlineGenerator[T] extends GeneratorBase[T] {
  def data(min: Option[T] = None, max: Option[T] = None): T


  override def sample(freq: Int): Future[Seq[T]] = Future {
    for { _ <- 0 until freq } yield data()
  }

  def sample(min: T, max:T, freq: Int): Future[Seq[T]] = Future {
    for (_ <- 0 until freq)  yield data(Some(min), Some(max))
  }
}
