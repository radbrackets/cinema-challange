package cinema.domain.validator

import cats.Monoid
import cats.data.NonEmptyList
import cats.data.Validated
import cats.data.Validated.Invalid
import cats.data.Validated.Valid
import cats.implicits.catsSyntaxValidatedId

case class Violation(reason: String)

trait Validator[T] extends Monoid[Validator[T]] {
  def apply(t: T): Validated[NonEmptyList[Violation], T]

  override def empty: Validator[T] = Validator.apply[T]

  override def combine(val1: Validator[T], val2: Validator[T]): Validator[T] = {
    val1 match {
      case _: AlwaysValidValidator[_] => val2
      case _                          => CombinedValidator(val1, val2)
    }
  }

}

case class CombinedValidator[T](val1: Validator[T], val2: Validator[T]) extends Validator[T] {

  def apply(t: T): Validated[NonEmptyList[Violation], T] = {
    (val1(t), val2(t)) match {
      case (Valid(_), Valid(_))       => t.valid
      case (v @ Invalid(_), Valid(_)) => v
      case (Valid(_), v @ Invalid(_)) => v
      case (Invalid(v1), Invalid(v2)) => Invalid(v1 ++ v2.toList)
    }
  }

}

class DefaultValidator[T](test: T => Boolean, violation: Violation) extends Validator[T] {

  override def apply(t: T): Validated[NonEmptyList[Violation], T] =
    if (test(t)) Invalid(NonEmptyList.of(violation)) else Valid(t)

}

class AlwaysValidValidator[T] extends Validator[T] {
  override def apply(t: T): Validated[NonEmptyList[Violation], T] = Valid(t)
}

object Validator {
  type Violations = NonEmptyList[Violation]

  def apply[T]: Validator[T] = new AlwaysValidValidator[T]

  implicit class ValidatorOps[T](val wrapped: Validator[T]) {

    def validate(test: T => Boolean)(violation: Violation): Validator[T] = {
      val validator = new DefaultValidator[T](test, violation)
      Validator[T].combine(wrapped, validator)
    }

  }

}
