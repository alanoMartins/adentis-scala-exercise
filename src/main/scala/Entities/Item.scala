package Entities

import spray.json.DefaultJsonProtocol
import ProductProtocol._

case class Item(cost: Double, fee: Double, tax: Double, product: Product)

object ItemProtocol extends DefaultJsonProtocol {
  implicit val itemFormat = jsonFormat4(Item)
}
