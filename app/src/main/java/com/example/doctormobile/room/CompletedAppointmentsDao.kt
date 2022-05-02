package com.example.doctormobile.room

import androidx.room.*
import com.example.doctormobile.models.CachedCompletedAppointment
import kotlinx.coroutines.flow.Flow

@Dao
interface CompletedAppointmentsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompletedAppointment(appointment: CachedCompletedAppointment)

    @Query("SELECT * FROM completed_appointment")
    fun getCompletedAppointments(): Flow<List<CachedCompletedAppointment>>

    @Delete
    suspend fun deleteCompletedAppointment(appointment: CachedCompletedAppointment)
}