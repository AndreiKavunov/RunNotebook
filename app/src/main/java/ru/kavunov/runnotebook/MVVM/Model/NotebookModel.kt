package ru.kavunov.runnotebook.MVVM.Model

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kavunov.runnotebook.bd.AppDatabase
import ru.kavunov.runnotebook.bd.NotebookTable
import java.util.*

class NotebookModel {

    companion object {

        var db: AppDatabase? = null

        var notebookTable: NotebookTable? = null
        var notebookTableAll: List<NotebookTable>? = null

        fun initializeDB(context: Context) : AppDatabase {
            return AppDatabase.getDataseClient(context)
        }

        suspend fun insertData(context: Context, title: String, description: String, time: Long)= withContext(
            Dispatchers.IO) {
            db = initializeDB(context)
            val notB = NotebookTable(title = title, description = description, dataTime = time)
            db?.notebookDAO()?.insert(notB)

        }

        suspend fun updateData(context: Context, id: Long, title: String, description: String, time: Long)= withContext(
            Dispatchers.IO) {
            db = initializeDB(context)
            val notB = NotebookTable(ntbId= id, title = title, description = description, dataTime = time)
            db?.notebookDAO()?.update(notB)

        }

        suspend fun getMovieID(context: Context, id: Long) : NotebookTable? = withContext(Dispatchers.IO){
            db = initializeDB(context)
            notebookTable = db?.notebookDAO()?.getByName(id)
            return@withContext notebookTable
        }

        suspend fun getAll(context: Context) : List<NotebookTable>? = withContext(Dispatchers.IO) {
            db = initializeDB(context)
            notebookTableAll = db?.notebookDAO()?.get()
            return@withContext notebookTableAll
        }
        suspend fun delete(context: Context, id: Long)= withContext(Dispatchers.IO) {
            db = initializeDB(context)
            db?.notebookDAO()?.deleteById(id)
        }


    }
}