package ru.kavunov.runnotebook.MVVM.Model

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kavunov.runnotebook.bd.AppDatabase
import ru.kavunov.runnotebook.bd.PhotoTable

class PhotoModel {
    companion object {

        var db: AppDatabase? = null

        var photoTable: PhotoTable? = null
        var photoTableAll: List<PhotoTable>? = null

        fun initializeDB(context: Context) : AppDatabase {
            return AppDatabase.getDataseClient(context)
        }

        suspend fun insertData(context: Context, title: String?, photo: String, time: Long)= withContext(
            Dispatchers.IO) {
            db = initializeDB(context)
            val photoT = PhotoTable(title = title, photo = photo, dataTime = time)
            db?.photoDAO()?.insert(photoT)

        }

        suspend fun updateData(context: Context, id: Long, title: String, photo: String, time: Long)= withContext(
            Dispatchers.IO) {
            db = initializeDB(context)
            val photoT = PhotoTable(photoId = id, title = title, photo = photo, dataTime = time)
            db?.photoDAO()?.update(photoT)

        }

        suspend fun getMovieID(context: Context, id: Long) : PhotoTable? = withContext(
            Dispatchers.IO){
            db = initializeDB(context)
            photoTable = db?.photoDAO()?.getByName(id)
            return@withContext photoTable
        }

        suspend fun getAll(context: Context) : List<PhotoTable>? = withContext(Dispatchers.IO) {
            db = initializeDB(context)
            photoTableAll = db?.photoDAO()?.get()
            return@withContext photoTableAll
        }
        suspend fun delete(context: Context, id: Long)= withContext(Dispatchers.IO) {
            db = initializeDB(context)
            db?.photoDAO()?.deleteById(id)
        }


    }
}