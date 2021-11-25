package ru.kavunov.runnotebook.bd

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "NotebookTable")
data class NotebookTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ntbId")
    val ntbId: Long? = null,
    val title: String,
    val description: String? = null,
    val dataTime: Long,

    )

@Entity(tableName = "PhotoTable")
data class PhotoTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "photoId")
    val photoId: Long? = null,
    val title: String? = null,
    val photo: String,
    val dataTime: Long?,

    )

@Entity(tableName = "TrainingTable")
data class TrainingTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "trainId")
    val trainId: Long,
    val dayOfWeek: String,
    val description: String? = null,

    )