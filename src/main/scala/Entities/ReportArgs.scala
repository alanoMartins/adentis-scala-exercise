package Entities

import Validator.{ArgumentsValidator, DateValidator, IntervalValidator}

import java.time.LocalDate
import java.time.format.DateTimeFormatter

case class IntervalReport(initial: Option[Int], end: Option[Int]) {
  override def toString: String = s"Months ${initial.getOrElse("Begin")} - ${end.getOrElse("end")}"
}

case class ReportArgs(initial: LocalDate, end: LocalDate, intervals: Set[IntervalReport])

object ReportArgs {
  def fromArgs(args: List[String]): ReportArgs = {
    require(ArgumentsValidator validate args)
    val initialParam = dateParse(args(0))
    val endParam = dateParse(args(1))
    val intervals = args.drop(2) match {
      case inter: Seq[String] if inter.isEmpty => Set(intervalParser("1-3"), intervalParser("4-6"), intervalParser("7-12"))
      case inter: Seq[String] => inter.map(intervalParser).toSet
    }

    ReportArgs(initialParam, endParam, intervals)

  }

  def intervalParser(interval: String): IntervalReport = {
    require(IntervalValidator validate interval)
    interval match {
      case i: String if i.contains("-") =>
        interval.split("-") match {
          case Array(init, end) => IntervalReport(Some(Integer.parseInt(init)), Some(Integer.parseInt(end)))
        }
      case i: String if i.contains(">") =>
        interval.split(">") match {
          case Array(_, end) => IntervalReport(None, Some(Integer.parseInt(end)))
        }
      case i: String if i.contains("<") =>
        interval.split("<") match {
          case Array(_, init) => IntervalReport(Some(Integer.parseInt(init)), None)
        }
      case _ => throw new IllegalArgumentException("Parse error")
    }
  }

  def dateParse(datetime: String): LocalDate = {
    require(DateValidator validate datetime)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")
    LocalDate.parse(datetime, formatter)
  }



}



