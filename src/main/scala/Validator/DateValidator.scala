package Validator

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.util.{Failure, Success, Try}

object DateValidator extends BaseValidator[String] {
  override def validate(datetime: String): Boolean = {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")
    Try(LocalDate.parse(datetime, formatter)) match {
      case Success(_) => true
      case Failure(_) => throw new IllegalArgumentException("O padrão de tempo é yyyy-MM-dd hh:mm:ss")
    }
  }
}
