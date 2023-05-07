package com.velocit.cinema
package employee

case class EmployeeId(value: String) extends AnyVal

object EmployeeId {
  def apply(value: String) = new EmployeeId(value)
}

case class Employee(id: EmployeeId, name: String)

class EmployeeNotFoundException(employeeId: EmployeeId)
  extends Exception(s"Could not found employee with $employeeId") {
}

object EmployeeNotFoundException {
  def apply(employeeId: EmployeeId) = new EmployeeNotFoundException(employeeId)
}