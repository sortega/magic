//package magic.slide6
//
//import simulacrum._
//
//object TypeclassesLessBoilerplate extends App {
//
//  @typeclass trait Monoid[A] {
//    def mzero: A
//    @op("|+|") def mappend(l: A, r: A): A
//  }
//
//  implicit object IntMonoid extends Monoid[Int] {
//    override def mzero: Int = 0
//    override def mappend(l: Int, r: Int): Int = l + r
//  }
//
//  implicit def MapMonoid[K, V](implicit M: Monoid[V]) = new Monoid[Map[K, V]] {
//    override def mzero: Map[K, V] = Map.empty
//    override def mappend(l: Map[K, V], r: Map[K, V]): Map[K, V] =
//      (l.toList ++ r.toList).groupBy(_._1).mapValues(_.map(_._2).foldLeft(M.mzero)(M.mappend))
//  }
//
//  import Monoid.ops._
//  println(Monoid[Map[String, Int]].mzero)
//  println(Map("a" -> 1, "b" -> 2) |+| Map("b" -> 2, "c" -> 5))
//}
