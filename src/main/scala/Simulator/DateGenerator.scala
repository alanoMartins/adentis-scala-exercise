package Simulator

import java.time.LocalDate
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import java.util.concurrent.ThreadLocalRandom

object DateGenerator {

  def date: LocalDate = {
    val minDay = LocalDate.of(2000, 1, 1).toEpochDay
    val maxDay = LocalDate.of(2020, 12, 31).toEpochDay
    val randomDay = ThreadLocalRandom.current.nextLong(minDay, maxDay)
    LocalDate.ofEpochDay(randomDay)

  }

  def sample(freq: Int): Future[Seq[LocalDate]] = Future {
    for (_ <- 0 until freq)  yield date
  }

}