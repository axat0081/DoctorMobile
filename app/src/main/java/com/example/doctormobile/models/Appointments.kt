package com.example.doctormobile.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class PendingAppointment(
    val caseId: String? = null,
    val name: String? = null,
    val enrollNo: String? = null,
    val cause: String? = null,
    val time: Long? = null
):Parcelable

@Parcelize
data class AssignedAppointment(
    val caseId: String? = null,
    val name: String? = null,
    val enrollNo: String? = null,
    val cause: String? = null,
    val docName: String? = null,
    val time: Long = 0
):Parcelable

@Parcelize
data class CompletedAppointment(
    val caseId: String? = null,
    val name: String? = null,
    val enrollNo: String? = null,
    val cause: String? = null,
    val prescription: String? = null,
    val docName: String? = null
): Parcelable

@Parcelize
@Entity(tableName = "completed_appointment")
data class CachedCompletedAppointment(
    @PrimaryKey
    val caseId: String,
    val name: String,
    val enrollNo: String,
    val cause: String,
    val prescription: String,
    val docName: String? = null
):Parcelable