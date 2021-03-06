package Entities

import spray.json._
import ItemProtocol._

import java.time.LocalDate
import java.time.format.DateTimeFormatter

case class Order(customName: String, contact: String, shippingAddress: String, grandTotal: Double, datePlaced: LocalDate, items: Set[Item])

object OrderProtocol extends DefaultJsonProtocol {
  implicit object DateJsonFormat extends RootJsonFormat[LocalDate] {

    override def write(obj: LocalDate) = JsString(obj.toString)

    override def read(json: JsValue) : LocalDate = json match {
      case JsString(s) => LocalDate.parse(s, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
      case _ => throw new DeserializationException("Error info you want here ...")
    }
  }

  implicit val orderFormat = jsonFormat6(Order)

}






