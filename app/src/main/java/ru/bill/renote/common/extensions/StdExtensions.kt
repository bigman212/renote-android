package ru.bill.renote.common.extensions

fun <T> MutableCollection<T>.addIfNotExists(element: T) {
  if (element !in this) {
    this.add(element)
  }
}

fun Int.isEven(): Boolean {
  return this % 2 == 0
}

fun <T> List<T>.without(itemToRemove: T): List<T> = this - itemToRemove






