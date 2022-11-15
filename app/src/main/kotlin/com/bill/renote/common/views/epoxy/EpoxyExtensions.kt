package com.bill.renote.common.views.epoxy

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel

fun EpoxyModel<*>.addWithId(id: String, controller: EpoxyController) {
    id(id)
    addTo(controller)
}

fun EpoxyModel<*>.addWithId(id: Long, controller: EpoxyController) {
    id(id)
    addTo(controller)
}