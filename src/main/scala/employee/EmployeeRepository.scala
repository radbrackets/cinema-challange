package com.velocit.cinema
package employee

object EmployeeRepository {
  def getAllEmployees: List[Employee] = List(
    Employee(EmployeeId("1"), "Patrycja"),
    Employee(EmployeeId("2"), "Ewelina")
  )
}
