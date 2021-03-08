package Simulator

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Random

trait BatchSampleable[T]  extends Sampleable[T] {
  val data: Seq[T]


  override def sample(freq: Int): Future[Seq[T]] = Future {
    require(!data.isEmpty, "List is empty")
    for { _ <- 0 until freq } yield data(Random.nextInt(data.length))
  }
}
