package com.bill.renote.data.persist.di

import androidx.room.withTransaction
import com.bill.renote.data.persist.AppDatabase

class RoomTransactionRunner(
    private val appDatabase: AppDatabase
){
    suspend fun runAsTransaction(block: suspend () -> Unit) {
       appDatabase.withTransaction(block::invoke)
    }
}