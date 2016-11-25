package magic.slide6

object Typeclasses extends App {

  trait Monoid[A] {
    def mzero: A
    def mappend(l: A, r: A): A
    def |+|(l: A, r: A): A = mappend(l, r)
  }

  object monoid {
    implicit class MonoidSyntax[A](val a: A)(implicit M: Monoid[A]) {
      def |+|(other: A): A = M.mappend(a, other)
    }
  }

  implicit object IntMonoid extends Monoid[Int] {
    override def mzero: Int = 0
    override def mappend(l: Int, r: Int): Int = l + r
  }

  implicit def MapMonoid[K, V](implicit M: Monoid[V]) = new Monoid[Map[K, V]] {
    override def mzero: Map[K, V] = Map.empty
    override def mappend(l: Map[K, V], r: Map[K, V]): Map[K, V] =
      (l.toList ++ r.toList).groupBy(_._1).mapValues(_.map(_._2).foldLeft(M.mzero)(M.mappend))
  }

  import monoid._
  println(Map("a" -> 1, "b" -> 2) |+| Map("b" -> 2, "c" -> 5))
}
