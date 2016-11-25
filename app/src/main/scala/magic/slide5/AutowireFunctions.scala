package magic.slide5

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal
import scala.util.{Failure, Success, Try}
import java.util
import scala.language.implicitConversions

object AutowireFunctions {

  case class Id(id: Long)
  implicit def conversion(id: Long): Id = Id(id)

  case class Person(id: Id, code: Long)
  Person(id = 3L, code = 12)

//  Person(id = 3, code = 12L)
}

object PimpMyLibraryPattern {

  val f = Future.successful(Future.successful("hi"))
  f.attempt
  // Future lacks a flatten! (Fixed in scala 2.12)
  f.flatten

  Seq.empty.flatten

  implicit class FutureExtensions[A](val f: Future[A]) extends AnyVal {

    def attempt: Future[Try[A]] =
      f.map(Success.apply).recover {
        case NonFatal(ex) => Failure(ex)
      }

    def flatten[B](implicit cast: A <:< Future[B], ec: ExecutionContext): Future[B] =
      f.flatMap(cast)
  }

  object ImplicitStyle {
    import scala.collection.JavaConversions._
    val list: Seq[Int] = util.Arrays.asList(1, 2, 3)
  }

  object ExplicitStyle {
    import scala.collection.JavaConverters._
    val list: Seq[Int] = util.Arrays.asList(1, 2, 3).asScala
  }
}
