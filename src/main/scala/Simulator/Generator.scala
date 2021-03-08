package Simulator

import Entities.{GeneratorArgs, Item, Order, Product}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Generator {

  def item(args: GeneratorArgs): Future[Seq[Item]] = {
    for {
      resCost <- DoubleGenerator.sample(0, 10000, args.sample)
      resFees <- DoubleGenerator.sample(0, 10, args.sample)
      resTaxes <- DoubleGenerator.sample(0, 10, args.sample)
      resProducts <- product(args)
    } yield {
      for (i <- 0 until args.sample) yield Item(resCost(i), resFees(i), resTaxes(i), resProducts(i))
    }
  }

  def product(args: GeneratorArgs): Future[Seq[Product]] = {
    for {
      resName <- ProductGenerator.sample(args.sample)
      resCategory <- CategoryGenerator.sample(args.sample)
      resWeight <- DoubleGenerator.sample(0, 5, args.sample)
      resPrices <- DoubleGenerator.sample(0, 1000, args.sample)
      resCreations <- DateGenerator.sample(args.from, args.to, args.sample)
    } yield {
      for (i <- 0 until args.sample) yield Product(resName(i), resCategory(i), resWeight(i), resPrices(i), resCreations(i))
    }
  }

  def orders(args: GeneratorArgs): Future[Seq[Order]] = {
    for {
      resName <- NameGenerator.sample(args.sample)
      resAddress <- AddressGenerator.sample(args.sample)
      resContact <- ContactGenerator.sample(args.sample)
      resDatePlaced <- DateGenerator.sample(args.from, args.to, args.sample)
      resItems <- Future.sequence(for (_ <- 0 until args.sample) yield item(args))
    } yield {
      val grantsTotal = resItems.map { items => items.foldLeft(0.0)(_ + _.cost) }
      for (i <- 0 until args.sample) yield Order(resName(i), resContact(i), resAddress(i), grantsTotal(i), resDatePlaced(i), resItems(i).toSet)
    }
  }

}
