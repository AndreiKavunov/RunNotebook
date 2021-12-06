package ru.kavunov.runnotebook.bd

import androidx.room.*

@Dao
interface PhotoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(photoTable: PhotoTables)

    @Update
    fun update(photoTable: PhotoTables)

    @Query("SELECT * FROM PhotoTables WHERE Photoid == :id")
    fun getPhotoId(id: Long): List<PhotoTables>

    @Query("SELECT * FROM PhotoTables WHERE id == :id")
    fun getId(id: Long): PhotoTables

    @Query("SELECT * FROM PhotoTables")
    fun getAll(): List<PhotoTables>

    @Query("DELETE FROM PhotoTables WHERE Photoid = :Id")
    fun deleteById(Id: Long)
}