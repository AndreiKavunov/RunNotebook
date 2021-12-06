package ru.kavunov.runnotebook.MVVM.Repo

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kavunov.runnotebook.MVVM.Model.PhotoDetailModel
import ru.kavunov.runnotebook.MVVM.Model.PhotoModel
import ru.kavunov.runnotebook.bd.PhotoNoteBTable
import ru.kavunov.runnotebook.bd.PhotoTables

class DetailPhotoRepo(id: Long) {
    val id = id
    suspend fun refreshData(context: Context, onDataReadyCallbackPhotoDet: OnDataReadyCallbackPhotoDet,
                            onDataReadyCallbackPhotosList: OnDataReadyCallbackPhotosList)= withContext(
        Dispatchers.IO){
        val photoModel = PhotoModel.getID(context, id)

        if (photoModel != null) {
            onDataReadyCallbackPhotoDet.onDataReadyPhotoDet(photoModel)

        val photoList = PhotoDetailModel.getAllId(context, photoModel.photoNbId)
            if (photoList != null) {
                onDataReadyCallbackPhotosList.onDataReadyPhotoDetList(photoList)
            }
    } }
}

interface OnDataReadyCallbackPhotoDet {
    fun onDataReadyPhotoDet(data: PhotoNoteBTable)

}

interface OnDataReadyCallbackPhotosList {
    fun onDataReadyPhotoDetList(data: List<PhotoTables>)

}