package magic.slide2

import scala.collection.generic.CanBuildFrom
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.Random

object AutowireArguments extends App {

  implicit val default = "John"

  def greet(implicit name: String) = println(s"hello $name!")

  greet("Mike")
  greet
}

object Transactions {

  trait Transaction

  trait Repository {
    def lookup(id: Int)(implicit tx: Transaction): Option[String]
    def save(id: Int, name: String)(implicit tx: Transaction): Unit
  }

  def withTransaction[A](block: Transaction => A): A = ???

  class Action(repository: Repository) {
    def run(id: Int): String = withTransaction { implicit tx =>
      val name = repository.lookup(id).getOrElse("unknown")
      repository.save(id * 2, name + "_derived")
      name
    }
  }

}

object ExecutionContexts extends App {
  implicit val ec = ExecutionContext.global

  def estimatePi(samples: Int): Future[Double] =
    Future {
      val r = new Random()
      var hits = 0
      (1 to samples).foreach { _ =>
        val x = r.nextDouble()
        val y = r.nextDouble()
        if (Math.sqrt(x * x + y * y) <= 1d) hits += 1
      }
      hits * 4d / samples
    }

  def await[T](f: Future[T]): T = Await.result(f, Duration.Inf)

  println("PI is about " + await(estimatePi(1000)))

  def parallelEstimatePi(samples: Int, parallelism: Int): Future[Double] =
    Future
      .traverse(List.fill(parallelism)(samples / parallelism))(estimatePi)
      .map(_.sum / parallelism)

  println("PI is about " + await(parallelEstimatePi(4000, 4)))
}

object FutureComprehensions {
  val ec = ExecutionContext.global

  def service1(id: Int): Future[Int] = ???
  def service2(id: Int): Future[String] = ???

  val expression = service1(42).flatMap { case ids => (service2(ids)).map { case name => name }(ec)
  }(ec)
}
