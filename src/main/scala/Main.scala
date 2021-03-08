import Entities.{Order, ReportArgs}
import spray.json._
import Entities.OrderProtocol._
import Report.OrderReport
import Report.OrderReport.printResult
import Simulator.Generator

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration


object Main {

  val usage =
    """
      | Usage java -jar <exec.jar> [Initial Data] [End Data]
      | --type [Order|Product] => Report type
      | --sample 1000 => Amount of orders to generate
      | --interval "<1,1-3,4-6,7-9,10-12,>12" String separated by comman => Interval to classify orders
      |""".stripMargin

  def main(args: Array[String]): Unit = {
    val start = System.nanoTime()

    try {
      val reportArgs: ReportArgs = ReportArgs.fromArgs(args.toList)
      generateReport(reportArgs)
    }
    catch { case e: IllegalArgumentException =>
      println(e.getMessage)
      print(usage)

    }

    val end = System.nanoTime()
    println("Elapsed time: " + (end - start) / Math.pow(10, 9) + "s")
  }

  def ordersFromJson(filename: String): Set[Order] = {
    require(!filename.isBlank, "Needs a json file!")
    val source = scala.io.Source.fromFile(filename)
    val lines = try source.mkString finally source.close()
    val json = lines.parseJson
    json.convertTo[Set[Order]]
  }

  def generateReport(reportArgs: ReportArgs): Unit = {
    println("---------- Generate orders ------------")
    val reportsFuture = Generator.orders(reportArgs.generator).flatMap{
      orders => OrderReport.reportsOrder(orders, reportArgs)
    }

    // Explain on presentation
    val reports = Await.result(reportsFuture, Duration("300 secs"))

    println("---------- Generate Report ------------")
    reports.foreach { mapOrder =>printResult(mapOrder._1, mapOrder._2)   }
  }
}
