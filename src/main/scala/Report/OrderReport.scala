package Report

import Entities.{IntervalReport, Order, ReportArgs}

import java.time.LocalDate
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object OrderReport {
  def calcMonths(localDate: LocalDate): Int = {
    (LocalDate.now().getYear - localDate.getYear) * 12 + localDate.getMonthValue
  }

  def inInterval(date: LocalDate, intervalReport: Set[IntervalReport]): Set[IntervalReport] = {
    intervalReport.filter{ interval =>
      interval.initial.getOrElse(0) <= calcMonths(date) && interval.end.getOrElse(Int.MaxValue) >= calcMonths(date)
    }
  }


  def reportOrders(orders: Seq[Order], reportArgs: ReportArgs) = Future {
    def oldestProductInOrder = (order: Order) => {
      order.items.foldLeft(LocalDate.now())((older,r) => {
        if(r.product.creation.isBefore(older)) r.product.creation else older
      })}

    orders.filter { o =>
      o.datePlaced.isAfter(reportArgs.initial) && o.datePlaced.isBefore(reportArgs.end)
    } .groupBy { order =>
      inInterval(oldestProductInOrder(order), reportArgs.intervals) }
  }

  def reportProduct(orders: Seq[Order], reportArgs: ReportArgs) = Future {
    val filterOrders = orders.filter(o => o.datePlaced.isAfter(reportArgs.initial) && o.datePlaced.isBefore(reportArgs.end))
    filterOrders.flatMap(x => x.items).groupBy(ps => inInterval(ps.product.creation, reportArgs.intervals))

  }

  def printResult[T1, T](intervals: Set[T1], orders: Seq[T]) = {
    val intervalString = intervals.size match {
      case 0 => "Uncategorized"
      case _ => intervals.map(_.toString).reduce(_ + " and " +  _)
    }
    val total = orders.length

    println(s"$intervalString : $total")
  }
}
