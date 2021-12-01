package ru.kavunov.runnotebook.MVVM.Model

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kavunov.runnotebook.bd.AppDatabase
import ru.kavunov.runnotebook.bd.PhotoTables

class PhotoDetailModel {
    companion object {

        var db: AppDatabase? = null

        var photoTables: PhotoTables? = null
        var PhotoTablesList: List<PhotoTables>? = null

        fun initializeDB(context: Context) : AppDatabase {
            return AppDatabase.getDataseClient(context)
        }

        suspend fun getID(context: Context, id: Long) : PhotoTables? = withContext(
            Dispatchers.IO){
            db = initializeDB(context)
            var list: MutableList<PhotoTables> = ArrayList()
            db?.photoDAO()?.let { list.addAll(it.getPhotoId(id)) }
            list.sortBy { it.id }
            photoTables = list.getOrNull(0)
            return@withContext photoTables
        }

        suspend fun getAllId(context: Context, id: Long) : List<PhotoTables>? = withContext(Dispatchers.IO) {
            db = initializeDB(context)
            PhotoTablesList = db?.photoDAO()?.getPhotoId(id)
            return@withContext PhotoTablesList
        }

        suspend fun get(context: Context, id: Long) : PhotoTables? = withContext(Dispatchers.IO) {
            db = initializeDB(context)
            photoTables = db?.photoDAO()?.getId(id)
            return@withContext photoTables
        }

    }
}