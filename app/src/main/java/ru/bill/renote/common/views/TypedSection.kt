package ru.bill.renote.common.views

import com.xwray.groupie.Section
import com.xwray.groupie.viewbinding.BindableItem

class TypedSection<T : BindableItem<*>> : Section() {

  fun update(groups: List<T>) = super.update(groups)

  @Suppress("UNCHECKED_CAST")
  fun getTypedGroups(): List<T> = super.getGroups() as List<T>
}
