package Simulator

object AddressGenerator extends GeneratorBase[String] {
  override def data: Seq[String] = Seq("Lisbon", "Porto", "Aveiro", "Beja", "Braga", "Bragança",
    "Castelo Branco", "Coimbra", "Évora", "Faro", "Funchal", "Guarda", "Leiria", "Ponta Delgada", "Portalegre",
    "Santarém", "Setúbal", "Viana do Castelo", "Vila Real", "Viseu")
}
