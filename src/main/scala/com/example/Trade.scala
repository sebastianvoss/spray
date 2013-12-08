package com.example

case class Trade(uid: String, product: String, price: Double, quantity: Long, legs: List[Leg] = Nil)

case class Leg(product: String, price: Double, quantity: Long)
