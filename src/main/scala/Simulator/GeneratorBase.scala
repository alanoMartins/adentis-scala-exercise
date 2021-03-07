package Simulator

import java.time.LocalDate
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Random

trait GeneratorBase[T] {

  def data: Seq[T]

  def sample(freq: Int): Future[Seq[T]] = Future {
    require(!data.isEmpty, "List is empty")
    for { _ <- 0 until freq } yield data(Random.nextInt(data.length))
  }
}

