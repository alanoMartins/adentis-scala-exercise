import Entities.{Order, ReportArgs}
import spray.json._
import Entities.OrderProtocol._
import Report.OrderReport
import Report.OrderReport.printResult
import Simulator.Generator

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration



object Main {

  val usage = """
    Usage: initial date [-i num] [-e num]
  """

  def main(args: Array[String]): Unit = {
    val t0 = System.nanoTime()
    val reportArgs: ReportArgs = ReportArgs.fromArgs(args.toList)
    println("---------- Generate orders ------------")
    val reportsFuture = Generator.orders(10000, 10).flatMap{
      orders => {
        Future.sequence(Seq(OrderReport.reportOrders(orders, reportArgs), OrderReport.reportProduct(orders, reportArgs)))
      }
    }

    // Explain on presentation
    val reports = Await.result(reportsFuture, Duration("200 secs"))

    println("---------- Report orders ------------")
    reports(0).foreach { mapOrder =>printResult(mapOrder._1, mapOrder._2)   }
    println("---------- Report product ------------")
    reports(1).foreach { mapOrder =>printResult(mapOrder._1, mapOrder._2)   }
    val t1 = System.nanoTime()
    println("Elapsed time: " + (t1 - t0) / Math.pow(10, 9) + "ns")
  }

  def ordersFromJson(filename: String): Set[Order] = {
    require(!filename.isBlank, "Needs a json file!")
    val source = scala.io.Source.fromFile(filename)
    val lines = try source.mkString finally source.close()
    val json = lines.parseJson
    json.convertTo[Set[Order]]
  }
}
