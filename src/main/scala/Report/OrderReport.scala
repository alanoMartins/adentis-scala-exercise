package Report

import Entities.{IntervalReport, Order, ReportArgs}

import java.time.LocalDate

object OrderReport {
  def calcMonths(localDate: LocalDate): Int = {
    (LocalDate.now().getYear - localDate.getYear) * 12 + localDate.getMonthValue
  }

  def inInterval(months: Int, intervalReport: Set[IntervalReport]): Set[IntervalReport] = {
    intervalReport.filter(i => i.initial.getOrElse(0) <= months && i.end.getOrElse(Int.MaxValue) >= months)
  }

  def reportOrders(orders: Seq[Order], reportArgs: ReportArgs) = {
    val filterOrders = orders.filter(o => o.datePlaced.isAfter(reportArgs.initial) && o.datePlaced.isBefore(reportArgs.end))
    filterOrders.groupBy(order =>
      inInterval(order.items.foldLeft(0)((max,r) => Math.max(max, calcMonths(r.product.creation))), reportArgs.intervals))
  }

  def reportProduct(orders: Seq[Order], reportArgs: ReportArgs) = {
    val filterOrders = orders.filter(o => o.datePlaced.isAfter(reportArgs.initial) && o.datePlaced.isBefore(reportArgs.end))
    filterOrders.flatMap(x => x.items).groupBy(ps => inInterval(calcMonths(ps.product.creation), reportArgs.intervals))
  }

  def printOrders(orders: Seq[Order], reportArgs: ReportArgs) ={
    val orderByMonth = reportOrders(orders, reportArgs)
    println("------------- Orders ------------------")
    orderByMonth.foreach(mapOrder => printResult(mapOrder._1, mapOrder._2))
  }

  def printProduct(orders: Seq[Order], reportArgs: ReportArgs) ={
    val productByMonth = reportProduct(orders, reportArgs)
    println("------------- Products ------------------")
    productByMonth.foreach(mapOrder => printResult(mapOrder._1, mapOrder._2))
  }

  def printResult(intervals: Set[IntervalReport], orders: Seq[Product]) = {
    val intervalString = intervals.size match {
      case 0 => "Uncategorized"
      case _ => intervals.map(_.toString).reduce(_ + " and " +  _)
    }
    val total = orders.length

    println(s"$intervalString : $total")
  }
}
