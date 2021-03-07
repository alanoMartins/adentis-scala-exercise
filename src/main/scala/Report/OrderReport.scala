package Report

import Entities.{IntervalReport, Item, Order, ReportArgs}

import java.time.LocalDate
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object OrderReport {

  private def oldestProductInOrder = (order: Order) => {
    order.items.foldLeft(LocalDate.now())((older,r) => {
      if(r.product.creation.isBefore(older)) r.product.creation else older
    })}

  private def ageInMonths(created: LocalDate, shippedDate: LocalDate): Int = {
    (shippedDate.getYear - created.getYear) * 12 + created.getMonthValue
  }

  private def inInterval(ageInMonths: Int, intervalReport: Set[IntervalReport]): Set[IntervalReport] = {
    intervalReport.filter{ interval =>
      interval.initial.getOrElse(0) <= ageInMonths && interval.end.getOrElse(Int.MaxValue) >= ageInMonths
    }
  }

  private def reportOrderCurried(orders: Seq[Order], reportArgs: ReportArgs)(operation: Order => Set[IntervalReport]) = {
    orders.filter { o =>
      o.datePlaced.isAfter(reportArgs.initial) && o.datePlaced.isBefore(reportArgs.end)
    } .groupBy { operation }
  }

  def reportsOrder(orders: Seq[Order], reportArgs: ReportArgs, agg: String) = Future {
    val reportGen = reportOrderCurried(orders, reportArgs) _
    agg match {
      case "order" => reportGen(order => inInterval(ageInMonths(oldestProductInOrder(order), order.datePlaced), reportArgs.intervals))
      case "product" => reportGen(order => order.items.map(ps =>inInterval(ageInMonths(ps.product.creation, order.datePlaced), reportArgs.intervals)).flatten)
    }
  }

  def printResult(intervals: Set[IntervalReport], orders: Seq[Order]) = {
    val intervalString = intervals.size match {
      case 0 => "Uncategorized"
      case _ => intervals.map(_.toString).reduce(_ + " and " +  _)
    }
    val total = orders.length

    println(s"$intervalString : $total")
  }
}
