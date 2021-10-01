package com.example.doctormobile.di

import android.app.Application
import androidx.room.Room
import com.example.doctormobile.room.CompletedAppointmentsDao
import com.example.doctormobile.room.CompletedAppointmentsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun providesCompletedAppointmentsDatabase(
        app: Application
    ): CompletedAppointmentsDatabase = Room.databaseBuilder(
        app,
        CompletedAppointmentsDatabase::class.java,
        "completed_appointments_database"
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun providesCompletedAppointmentsDao(db: CompletedAppointmentsDatabase): CompletedAppointmentsDao =
        db.getCompletedAppointmentDatabaseDao()
}