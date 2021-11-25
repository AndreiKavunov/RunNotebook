package ru.kavunov.runnotebook.bd

import androidx.room.*

@Dao
interface TrainDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(trainingTable: TrainingTable)

    @Update
    fun update(trainingTable: TrainingTable)

    @Query("SELECT * FROM TrainingTable WHERE trainId == :id")
    fun getByName(id: Long): TrainingTable

    @Query("SELECT * FROM TrainingTable")
    fun get(): List<TrainingTable>
}