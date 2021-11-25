package ru.kavunov.runnotebook.MVVM.Repo

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kavunov.runnotebook.MVVM.Model.PhotoModel
import ru.kavunov.runnotebook.bd.PhotoTable

class DetailPhotoRepo(id: Long) {
    val id = id
    suspend fun refreshData(context: Context, onDataReadyCallbackPhotoDet: OnDataReadyCallbackPhotoDet)= withContext(
        Dispatchers.IO){
        val photoModel = PhotoModel.getMovieID(context, id)

        if (photoModel != null) {
            onDataReadyCallbackPhotoDet.onDataReadyPhotoDet(photoModel)
        }
    }
}

interface OnDataReadyCallbackPhotoDet {
    fun onDataReadyPhotoDet(data: PhotoTable)

}