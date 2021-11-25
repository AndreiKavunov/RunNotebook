package ru.kavunov.runnotebook.MVVM.Repo

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kavunov.runnotebook.MVVM.Model.PhotoModel
import ru.kavunov.runnotebook.bd.PhotoTable

class PhotoRepo {
    suspend fun refreshData(context: Context, onDataReadyCallbackPhoto: OnDataReadyCallbackPhoto)= withContext(
        Dispatchers.IO){
        val listPhoto = PhotoModel.getAll(context)

        if (listPhoto != null) {
            onDataReadyCallbackPhoto.onDataReadyP(listPhoto)
        }
    }
}
interface OnDataReadyCallbackPhoto {
    fun onDataReadyP(data: List<PhotoTable>)

}