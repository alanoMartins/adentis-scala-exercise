import Entities.{Order, ReportArgs}
import spray.json._
import Entities.OrderProtocol._
import Report.OrderReport
import Simulator.Generator



object Main {

  val usage = """
    Usage: initial date [-i num] [-e num]
  """

  def main(args: Array[String]): Unit = {
    val reportArgs: ReportArgs = ReportArgs.fromArgs(args.toList)
    val orders = Generator.orders(1000)

    OrderReport.printOrders(orders, reportArgs)
    OrderReport.printProduct(orders, reportArgs)
  }

  def ordersFromJson(filename: String): Set[Order] = {
    require(!filename.isBlank, "Needs a json file!")
    val source = scala.io.Source.fromFile(filename)
    val lines = try source.mkString finally source.close()
    val json = lines.parseJson
    json.convertTo[Set[Order]]
  }
}
