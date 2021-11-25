package ru.kavunov.runnotebook.bd

import androidx.room.*

@Dao
interface PhotoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photoTable: PhotoTable)

    @Update
    fun update(photoTable: PhotoTable)

    @Query("SELECT * FROM PhotoTable WHERE photoId == :id")
    fun getByName(id: Long): PhotoTable

    @Query("SELECT * FROM PhotoTable")
    fun get(): List<PhotoTable>

    @Query("DELETE FROM PhotoTable WHERE photoId = :Id")
    fun deleteById(Id: Long)
}