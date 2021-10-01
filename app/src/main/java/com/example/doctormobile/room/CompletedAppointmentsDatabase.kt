package com.example.doctormobile.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.doctormobile.models.CachedCompletedAppointment
import com.example.doctormobile.models.CompletedAppointment

@Database(entities = [CachedCompletedAppointment::class], version = 2)
abstract class CompletedAppointmentsDatabase : RoomDatabase() {
    abstract fun getCompletedAppointmentDatabaseDao(): CompletedAppointmentsDao
}