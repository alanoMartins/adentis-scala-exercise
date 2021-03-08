package Entities

import Validator.{ArgumentsValidator, DateValidator, IntervalValidator}

import java.time.LocalDate
import java.time.format.DateTimeFormatter

case class IntervalReport(initial: Option[Int], end: Option[Int]) {
  override def toString: String = s"Months ${initial.getOrElse("Begin")} - ${end.getOrElse("end")}"
}
case class GeneratorArgs(sample:Int, from: LocalDate, to:LocalDate)
case class ReportArgs(initial: LocalDate, end: LocalDate, intervals: Set[IntervalReport], generator:GeneratorArgs, reportType: String)

object ReportArgs {

  type ArgOptions = Map[String, String]

  def nextOption(map : ArgOptions, list: List[String]) : ArgOptions = {
    list match {
      case Nil => map
      case "--type" :: value :: tail => nextOption(map ++ Map("type" -> value), tail)
      case "--sample" :: value :: tail => nextOption(map ++ Map("sample" -> value), tail)
      case "--interval" :: value :: tail => nextOption(map ++ Map("interval" -> value), tail)
      case "--from" :: value :: tail => nextOption(map ++ Map("from" -> value), tail)
      case "--to" :: value :: tail => nextOption(map ++ Map("to" -> value), tail)
      case option :: _ => throw  new IllegalArgumentException(s"Unknown argument $option")
    }
  }

  def fromArgs(args: List[String]): ReportArgs = {
    require(ArgumentsValidator validate args, "Read the docs, please! =) ")
    val initialParam = dateParse(args(0))
    val endParam = dateParse(args(1))
    val namedArgs = nextOption(Map(),args.drop(2))
    val reportType = namedArgs.getOrElse("type", "order")
    val intervals = namedArgs.getOrElse("interval", "1-3,4-6,7-9,10-12, <1,>12").trim.split(',').map(intervalParser).toSet
    val sample = namedArgs.getOrElse("sample", "100").toInt
    val fromGenerator = dateParse(namedArgs.getOrElse("from", "2018-01-01 00:00:00"))
    val toGenerator = dateParse(namedArgs.getOrElse("to", "2019-01-01 00:00:00"))

    ReportArgs(initialParam, endParam, intervals, GeneratorArgs(sample, fromGenerator, toGenerator), reportType)

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
          case Array(_, init) => IntervalReport(Some(Integer.parseInt(init)), None)
        }
      case i: String if i.contains("<") =>
        interval.split("<") match {
          case Array(_, end) => IntervalReport(None, Some(Integer.parseInt(end)))
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



