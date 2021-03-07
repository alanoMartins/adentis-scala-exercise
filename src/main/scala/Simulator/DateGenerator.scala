package Simulator

import java.time.LocalDate
import java.util.concurrent.ThreadLocalRandom

object DateGenerator extends OnlineGenerator [LocalDate]{

  override def data(min: Option[LocalDate], max: Option[LocalDate]): LocalDate = {
    (min, max) match {
      case (None, None) => {
        val minDay = LocalDate.of(2000, 1, 1).toEpochDay
        val maxDay = LocalDate.of(2020, 12, 31).toEpochDay
        val randomDay = ThreadLocalRandom.current.nextLong(minDay, maxDay)
        LocalDate.ofEpochDay(randomDay)
      }
      case (Some(min), Some(max)) => {
        val randomDay = ThreadLocalRandom.current().nextLong(min.toEpochDay, max.toEpochDay)
        LocalDate.ofEpochDay(randomDay)
      }
      case _ => throw new IllegalArgumentException("Should pass a valid max and min limits")
    }

  }


}