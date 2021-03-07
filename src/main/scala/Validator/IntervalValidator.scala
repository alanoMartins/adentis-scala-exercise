package Validator

import scala.util.Try

object IntervalValidator {

  def validate(interval: String): Boolean = {
    if(!validatorSize(interval)) throw new IllegalArgumentException(s"Parameter $interval with wrong size")
    if(!validatorType(interval)) throw new IllegalArgumentException(s"Parameter $interval with wrong type")
    if(!validatorOrder(interval)) throw new IllegalArgumentException(s"Parameter $interval with wrong order")
    true
  }

  def validatorSize(interval: String): Boolean = {
    interval match {
      case i: String if i.contains("-") => interval.split("-").length == 2
      case i: String if i.contains(">") => interval.split(">").length == 2
      case i: String if i.contains("<") => interval.split("<").length == 2
      case _ => throw new IllegalArgumentException("Parameter format is incorrect")
    }
  }

  def validatorType(interval: String): Boolean = {
    def validNumber(value: String):Boolean = {
      Try(value.toInt).isSuccess && value.toInt <= Int.MaxValue
    }
    interval match {
      case i: String if i.contains("-") => interval.split("-").forall(validNumber)
      case i: String if i.contains(">") => interval.split(">").filter(x => !x.isBlank).forall(validNumber)
      case i: String if i.contains("<") => interval.split("<").filter(x => !x.isBlank).forall(validNumber)
      case _ => throw new IllegalArgumentException("Parameter format is incorrect")
    }
  }

  def validatorOrder(interval: String): Boolean = {
    interval match {
      case i: String if i.contains("-") =>
        interval.split("-") match {
          case Array(init, end) => init.toInt < end.toInt
          case _ => throw new IllegalArgumentException("Parameter format is incorrect")
        }
      case i: String if i.contains(">") => i.startsWith(">")
      case i: String if i.contains("<") => i.startsWith("<")
      case _ => throw new IllegalArgumentException("Parameter format is incorrect")
    }
  }
}
