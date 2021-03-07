package Simulator

import Entities.{Item, Order, Product}

import java.time.LocalDate
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Generator {

  def item(amount: Int): Future[Seq[Item]] = {
    for {
      resCost <- DoubleGenerator.sample(0, 10000, amount)
      resFees <- DoubleGenerator.sample(0, 10, amount)
      resTaxes <- DoubleGenerator.sample(0, 10, amount)
      resProducts <- product(amount)
    } yield {
      for (i <- 0 until amount) yield Item(resCost(i), resFees(i), resTaxes(i), resProducts(i))
    }
  }

  def product(amount: Int): Future[Seq[Product]] = {
    for {
      resName <- ProductGenerator.sample(amount)
      resCategory <- CategoryGenerator.sample(amount)
      resWeight <- DoubleGenerator.sample(0, 5, amount)
      resPrices <- DoubleGenerator.sample(0, 1000, amount)
      resCreations <- DateGenerator.sample(
        LocalDate.of(2018, 1, 1),
        LocalDate.of(2020, 1, 1), amount)
    } yield {
      for (i <- 0 until amount) yield Product(resName(i), resCategory(i), resWeight(i), resPrices(i), resCreations(i))
    }
  }

  def orders(amount: Int, itemsPerOrder: Int): Future[Seq[Order]] = {
    for {
      resName <- NameGenerator.sample(amount)
      resAddress <- AddressGenerator.sample(amount)
      resContact <- ContactGenerator.sample(amount)
      resDatePlaced <- DateGenerator.sample(
        LocalDate.of(2018, 1, 1),
        LocalDate.of(2020, 1, 1), amount)
      resItems <- Future.sequence(for (_ <- 0 until amount) yield item(itemsPerOrder))
    } yield {
      val grantsTotal = resItems.map { items => items.foldLeft(0.0)(_ + _.cost) }
      for (i <- 0 until amount) yield Order(resName(i), resContact(i), resAddress(i), grantsTotal(i), resDatePlaced(i), resItems(i).toSet)
    }
  }

}
