package magic.slide4

import scala.annotation.implicitNotFound

object Scopes {

  @implicitNotFound("No default config in scope")
  case class Config(scope: String)

  object Config {
    implicit val default = Config(scope = "companion")
  }

  trait LowPriority {
    implicit val lowPriority = Config(scope = "low priority imported")
  }

  object DefinitionsToImport extends LowPriority {
    implicit val highPriority = Config(scope = "high priority imported")
  }

  import DefinitionsToImport._

  def main(args: Array[String]): Unit = {
//    implicit val local = Config(scope = "local")
    println(s"Config with scope = ${implicitly[Config].scope}")
  }
}
