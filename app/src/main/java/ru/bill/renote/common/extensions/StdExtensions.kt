package ru.bill.renote.common.extensions

fun <T> MutableCollection<T>.addIfNotExists(element: T) {
  if (element !in this){
    this.add(element)
  }
}





