package com.bill.renote.common.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun <R> unsafeLazy(initializer: () -> R): Lazy<R> = lazy(LazyThreadSafetyMode.NONE, initializer)

inline fun <reified R> List<*>.findInstance(): R? {
    return find { it is R }?.let { it as R }
}

inline fun <E> List<E>.ifSizeNot(expectedSize: Int, defaultValue: (originalList: List<E>) -> List<E>): List<E> {
    return if (this.size != expectedSize) defaultValue.invoke(this) else this
}

inline fun <E> List<E>.ifNotEmpty(action: (originalList: List<E>) -> Unit): List<E> {
    if (isNotEmpty()) action.invoke(this)
    return this
}

fun <T> MutableCollection<T>.addIfNotExists(element: T) {
    if (element !in this) {
        this.add(element)
    }
}

fun Int.isEven(): Boolean {
    return this % 2 == 0
}

fun <T> List<T>.without(itemToRemove: T): List<T> = this - itemToRemove

inline fun <reified T : Enum<T>, V> ((T) -> V).find(value: V): T? {
    return enumValues<T>().firstOrNull { this(it) == value }
}

fun Result<*>.invokeAndForget() {
    getOrNull()
}

fun CoroutineScope.launchCatching(
    tryBlock: suspend (CoroutineScope) -> Unit,
    catchBlock: (Throwable) -> Unit,
    finallyBlock: () -> Unit = {}
) {
    launch {
        try {
            tryBlock(this)
        } catch (e: Throwable) {
            catchBlock(e)
        } finally {
            finallyBlock()
        }
    }
}





