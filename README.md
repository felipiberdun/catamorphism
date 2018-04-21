An attempt to implement in Java the challenge proposed by Tony Morris on his blog (http://blog.tmorris.net/posts/debut-with-a-catamorphism/index.html).

Given a base structure and the catamorphism concept, you are asked to implement the following MyOption contract.

```
trait MyOption[+A] {
  // single abstract method
  def cata[X](some: A => X, none: => X): X

  def map[B](f: A => B): MyOption[B] = error("todo")

  def flatMap[B](f: A => MyOption[B]): MyOption[B] = error("todo")

  def getOrElse[AA >: A](e: => AA): AA = error("todo")

  def filter(p: A => Boolean): MyOption[A] = error("todo")

  def foreach(f: A => Unit): Unit = error("todo")

  def isDefined: Boolean = error("todo")

  def isEmpty: Boolean = error("todo")

  // WARNING: not defined for None
  def get: A = error("todo")

  def orElse[AA >: A](o: MyOption[AA]): MyOption[AA] = error("todo")

  def toLeft[X](right: => X): Either[A, X] = error("todo")

  def toRight[X](left: => X): Either[X, A] = error("todo")

  def toList: List[A] = error("todo")

  def iterator: Iterator[A] = error("todo")
}
```