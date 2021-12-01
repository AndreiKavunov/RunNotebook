package ru.kavunov.runnotebook.MVVM.Model

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kavunov.runnotebook.bd.AppDatabase
import ru.kavunov.runnotebook.bd.PhotoNoteBTable
import ru.kavunov.runnotebook.bd.PhotoTables

class PhotoModel {
    companion object {

        var db: AppDatabase? = null

        var photoNoteBTable: PhotoNoteBTable? = null
        var photoNoteBTableAll: List<PhotoNoteBTable>? = null

        fun initializeDB(context: Context) : AppDatabase {
            return AppDatabase.getDataseClient(context)
        }

        suspend fun insertData(context: Context, title: String?, time: Long, listPhoto: List<PhotoTables>)= withContext(
            Dispatchers.IO) {
            db = initializeDB(context)
            var list: MutableList<PhotoNoteBTable> = ArrayList()
            db?.photoNotBDAO()?.get()?.let { list.addAll(it) }
            list.sortBy{it.photoNbId}
            var id: Long = 0
            if(list.size > 0) {
                val photoNoteBTable: PhotoNoteBTable = list.last()
                id = photoNoteBTable.photoNbId + 1
            }
            else {
                id = 1
            }
            val photoT = PhotoNoteBTable(photoNbId = id, photo = null, title = title,  dataTime = time)
            db?.photoNotBDAO()?.insert(photoT)

            for (photoTab in listPhoto){
                var photo= PhotoTables(photo = photoTab.photo, Photoid = id)
                db?.photoDAO()?.insert(photo)
            }

        }

        suspend fun updateData(context: Context, id: Long, title: String, time: Long, listPhoto: List<PhotoTables>)= withContext(
            Dispatchers.IO) {
            db = initializeDB(context)
            val photoT = PhotoNoteBTable(photoNbId = id, photo = null, title = title, dataTime = time)
            db?.photoNotBDAO()?.update(photoT)
            db?.photoDAO()?.deleteById(id)
            for (photoTab in listPhoto){
                var photo= PhotoTables(photo = photoTab.photo, Photoid = id)
                db?.photoDAO()?.insert(photo)
            }

        }

        suspend fun getID(context: Context, id: Long) : PhotoNoteBTable? = withContext(
            Dispatchers.IO){
            db = initializeDB(context)
            photoNoteBTable = db?.photoNotBDAO()?.getByName(id)
            return@withContext photoNoteBTable
        }

        suspend fun getAll(context: Context) : List<PhotoNoteBTable>? = withContext(Dispatchers.IO) {
            db = initializeDB(context)
            photoNoteBTableAll = db?.photoNotBDAO()?.get()
            var list: MutableList<PhotoNoteBTable> = ArrayList()
            if (photoNoteBTableAll?.size!! > 0) {
                for(item in photoNoteBTableAll!!){
                    var photo = PhotoDetailModel.getID(context, item.photoNbId)
                    list.add(PhotoNoteBTable(photoNbId = item.photoNbId, photo = photo?.photo,
                        title = item.title, dataTime = item.dataTime))

                }}

            return@withContext list
        }
        suspend fun delete(context: Context, id: Long)= withContext(Dispatchers.IO) {
            db = initializeDB(context)
            db?.photoNotBDAO()?.deleteById(id)

        }


    }
}