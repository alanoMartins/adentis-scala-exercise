package Simulator

import Entities.{Item, Order, Product}

import java.util.concurrent.ForkJoinPool
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

object Generator {

//  implicit val threadPool: ForkJoinPool = new ForkJoinPool(2)
//  implicit val ec: ExecutionContext = ExecutionContext.fromExecutor(threadPool)
  private val duration = Duration("2000 secs")

  def item(amount: Int): Future[Seq[Item]] = {
    for {
      resCost <- DoubleGanerator.sample(0, 10000, amount)
      resFees <- DoubleGanerator.sample(0, 10, amount)
      resTaxes <- DoubleGanerator.sample(0, 10, amount)
      resProducts <- product(amount)
    } yield {
      for (i <- 0 until amount) yield Item(resCost(i), resFees(i), resTaxes(i), resProducts(i))
    }
  }


  def product(amount: Int): Future[Seq[Product]] = {
    for {
      resName <- ProductGenerator.sample(amount)
      resCategory <- CategoryGenerator.sample(amount)
      resWeight <- DoubleGanerator.sample(0, 5, amount)
      resPrices <- DoubleGanerator.sample(0, 1000, amount)
      resCreations <- DateGenerator.sample(amount)
    } yield {
      for (i <- 0 until amount) yield Product(resName(i), resCategory(i), resWeight(i), resPrices(i), resCreations(i))
    }
  }


  def orders(amount: Int, itemsPerOrder: Int): Future[Seq[Order]] = {
    for {
      resName <- NameGenerator.sample(amount)
      resAddress <- AddressGenerator.sample(amount)
      resContact <- ContactGenerator.sample(amount)
      resDatePlaced <- DateGenerator.sample(amount)
      resItems <- Future.sequence(for (_ <- 0 until amount) yield item(itemsPerOrder))
    } yield {
      val grantsTotal = resItems.map { items => items.foldLeft(0.0)(_ + _.cost) }
      for (i <- 0 until amount) yield Order(resName(i), resContact(i), resAddress(i), grantsTotal(i), resDatePlaced(i), resItems(i).toSet)
    }
  }

}
