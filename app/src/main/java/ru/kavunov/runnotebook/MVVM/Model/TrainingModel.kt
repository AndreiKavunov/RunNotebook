package ru.kavunov.runnotebook.MVVM.Model

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kavunov.runnotebook.bd.AppDatabase
import ru.kavunov.runnotebook.bd.TrainingTable

class TrainingModel {

    companion object {

        var db: AppDatabase? = null

        var trainingTable: TrainingTable? = null
        var trainingTableAll: List<TrainingTable>? = null

        fun initializeDB(context: Context) : AppDatabase {
            return AppDatabase.getDataseClient(context)
        }

        suspend fun insertData(context: Context, id: Long, dayOfWeek: String, description: String)= withContext(
            Dispatchers.IO) {
            db = initializeDB(context)
            val train = TrainingTable(trainId = id, dayOfWeek = dayOfWeek, description = description)
            db?.trainDAO()?.insert(train)

        }

        suspend fun updateData(context: Context, id: Long, dayOfWeek: String, description: String)= withContext(
            Dispatchers.IO) {
            db = initializeDB(context)
            val train = TrainingTable(trainId= id, dayOfWeek = dayOfWeek, description = description)
            db?.trainDAO()?.update(train)

        }

        suspend fun getMovieID(context: Context, id: Long) : TrainingTable? = withContext(
            Dispatchers.IO){
            db = initializeDB(context)
            trainingTable = db?.trainDAO()?.getByName(id)
            return@withContext trainingTable
        }

        suspend fun getAll(context: Context) : List<TrainingTable>? = withContext(Dispatchers.IO) {
            db = initializeDB(context)
            trainingTableAll = db?.trainDAO()?.get()
            return@withContext trainingTableAll
        }


    }
}