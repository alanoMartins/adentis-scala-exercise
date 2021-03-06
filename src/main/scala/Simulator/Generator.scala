package Simulator

import java.time.LocalDate
import scala.util.Random
import Entities.{Item, Order, Product}

import java.time.LocalDate
import java.util.concurrent.ThreadLocalRandom

object Generator {

  def item(amount: Int): Seq[Item] = {
    val costs = DoubleGanerator.sample(0, 10000, amount)
    val fees = DoubleGanerator.sample(0, 10, amount)
    val taxes = DoubleGanerator.sample(0, 10, amount)
    val products = product(amount)

    for (i <- 0 until amount ) yield Item(costs(i), fees(i), taxes(i), products(i))
  }


  def product(amount: Int): Seq[Product] = {
    val names = ProductGenerator.sample(amount)

    val category = CategoryGenerator.sample(amount)
    val weight = DoubleGanerator.sample(0, 5, amount)
    val prices = DoubleGanerator.sample(0, 1000, amount)
    val creations = DateGanerator.sample(amount)

    for (i <- 0 until amount ) yield Product(names(i), category(i), prices(i), weight(i), creations(i))
  }

  def orders(amount: Int): Seq[Order] = {
    val names = NameGenerator.sample(amount)
    val address = AddressGenerator.sample(amount)
    val contacts = ContactGenerator.sample(amount)
    val dataPlaced = DateGanerator.sample(amount)
    val listItems:Seq[Seq[Item]] = for (_ <- 0 until amount) yield item(amount)
    val grandsTotal: Seq[Double] = listItems.map(items => items.foldLeft(0.0)(_ + _.cost))

    for (i <- 0 until amount) yield Order(names(i), contacts(i), address(i), grandsTotal(i), dataPlaced(i), listItems(i).toSet)
  }

}
