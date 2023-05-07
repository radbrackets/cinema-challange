package com.velocit

import scala.util.{Failure, Success, Try}

package object cinema {

  implicit class OptionToTry[A](option: Option[A]) {
    def requireDefined(ex: => Throwable): Try[A] = option match {
      case Some(value) => Success(value)
      case None => Failure(ex)
    }
  }

  implicit class TryOps[A](t: Try[A]) {
    def mapException(newEx: Throwable): Try[A] = {
      t.transform(
        value => Success(value),
        _ => Failure(newEx)
      )
    }
  }
}
