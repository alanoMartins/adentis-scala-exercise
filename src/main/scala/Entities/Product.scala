package Entities

import Entities.OrderProtocol.DateJsonFormat
import spray.json.DefaultJsonProtocol

import java.time.LocalDate

case class Product(name: String, category: String, weight: Double, price: Double, creation: LocalDate)


object ProductProtocol extends DefaultJsonProtocol {
  implicit val productFormat = jsonFormat5(Product)
}
