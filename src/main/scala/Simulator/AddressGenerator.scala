package Simulator

object AddressGenerator extends BatchSampleable[String] {
  override val data: Seq[String] = Seq("Lisbon", "Porto", "Aveiro", "Beja", "Braga", "Bragança",
    "Castelo Branco", "Coimbra", "Évora", "Faro", "Funchal", "Guarda", "Leiria", "Ponta Delgada", "Portalegre",
    "Santarém", "Setúbal", "Viana do Castelo", "Vila Real", "Viseu")
}
