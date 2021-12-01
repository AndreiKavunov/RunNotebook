package ru.kavunov.runnotebook.bd

import androidx.room.*

@Dao
interface PhotoNotBDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photoNoteBTable: PhotoNoteBTable)

    @Update
    fun update(photoNoteBTable: PhotoNoteBTable)

    @Query("SELECT * FROM PhotoNoteBTable WHERE photoNbId == :id")
    fun getByName(id: Long): PhotoNoteBTable

    @Query("SELECT * FROM PhotoNoteBTable")
    fun get(): List<PhotoNoteBTable>

    @Query("DELETE FROM PhotoNoteBTable WHERE photoNbId = :Id")
    fun deleteById(Id: Long)
}