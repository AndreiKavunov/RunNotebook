package ru.kavunov.runnotebook.bd

import androidx.room.*
import androidx.room.ForeignKey.CASCADE


@Entity(tableName = "NotebookTable")
data class NotebookTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ntbId")
    val ntbId: Long? = null,
    val title: String,
    val description: String? = null,
    val dataTime: Long,

    )

@Entity(tableName = "PhotoNoteBTable")
data class PhotoNoteBTable(
    @PrimaryKey
    @ColumnInfo(name = "photoNbId")
    val photoNbId: Long,
    var photo: String?,
    val title: String? = null,
    val dataTime: Long?,

    )


@Entity(tableName = "PhotoTables",
    foreignKeys = [ForeignKey(
        entity = PhotoNoteBTable::class,
        parentColumns = arrayOf("photoNbId"),
        childColumns = arrayOf("Photoid"),
        onDelete = CASCADE
    )]
)
data class PhotoTables(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long? = null,
    val photo: String,
    val Photoid: Long

)


@Entity(tableName = "TrainingTable")
data class TrainingTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "trainId")
    val trainId: Long,
    val dayOfWeek: String,
    val description: String? = null,

    )