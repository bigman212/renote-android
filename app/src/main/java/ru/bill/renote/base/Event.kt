package ru.bill.renote.base

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import ru.bill.renote.common.extensions.observe
import java.util.*

interface Event

class EventsQueue : MutableLiveData<Queue<Event>>() {
  fun append(event: Event) {
    val queue = (value ?: LinkedList()).apply { add(event) }
    value = queue
  }
}

fun Fragment.observeEvents(eventsQueue: EventsQueue, eventHandler: (Event) -> Unit) {
  observe(eventsQueue) { queue: Queue<Event> ->
    while (queue.isNotEmpty()) {
      eventHandler(queue.remove())
    }
  }
}

fun Activity.observeEvents(eventsQueue: EventsQueue, eventHandler: (Event) -> Unit) {
  eventsQueue.observe(this as LifecycleOwner, { queue: Queue<Event> ->
    while (queue.isNotEmpty()) {
      eventHandler(queue.remove())
    }
  })
}
