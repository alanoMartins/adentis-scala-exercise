package Simulator

import java.time.LocalDate

object DateGanerator {

  def date: LocalDate = {
    import java.time.LocalDate
    import java.util.concurrent.ThreadLocalRandom
    val minDay = LocalDate.of(2000, 1, 1).toEpochDay
    val maxDay = LocalDate.of(2020, 12, 31).toEpochDay
    val randomDay = ThreadLocalRandom.current.nextLong(minDay, maxDay)
    val randomDate = LocalDate.ofEpochDay(randomDay)
    return randomDate
  }

  def sample(freq: Int): Seq[LocalDate] = {
    for (_ <- 0 until freq)  yield date
  }
}