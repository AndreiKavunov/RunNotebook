package ru.kavunov.runnotebook.bd

import androidx.room.*


@Dao
interface NotesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(notebookTable: NotebookTable)

    @Update
    fun update(notebookTable: NotebookTable)

    @Query("SELECT * FROM NotebookTable WHERE ntbId == :id")
    fun getByName(id: Long): NotebookTable

    @Query("SELECT * FROM NotebookTable")
    fun get(): List<NotebookTable>

    @Query("DELETE FROM NotebookTable WHERE ntbId = :Id")
    fun deleteById(Id: Long)

}