package magic.slide3

import scala.util.Random

object CompilerProvided extends App {

  def imp[T](implicit value: T): T = value

  // implicitly is your friend to debug implicit resolution

  println(imp[Manifest[String]])

  println(imp[Ordering[Int]].lt(1, 0))

  implicitly[Vector[Int] <:< Seq[Int]]
//  implicitly[Seq[Int] <:< Vector[Int]]
  type Foo = List[Int]
  implicitly[Foo =:= List[Int]]

  def genericSum[N](nums: Traversable[N])(implicit N: Numeric[N]): N =
    nums.foldLeft(N.zero)(N.plus)

  println(genericSum(List(1, 2, 3)))
  println(genericSum(List(1f, 2f, 3f)))
  println(genericSum(List[BigInt](1, 2, BigInt.probablePrime(1024, Random))))
}
