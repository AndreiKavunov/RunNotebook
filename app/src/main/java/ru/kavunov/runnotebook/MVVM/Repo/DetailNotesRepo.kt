package ru.kavunov.runnotebook.MVVM

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kavunov.runnotebook.MVVM.Model.NotesModel
import ru.kavunov.runnotebook.bd.NotebookTable

class DetailNbRepo(id: Long) {
    val id = id
    suspend fun refreshData(context: Context, onDataReadyCallbackDetailNb: OnDataReadyCallbackDetailNb)= withContext(
        Dispatchers.IO){
        val notebookModel = NotesModel.getMovieID(context, id)

        if (notebookModel != null) {
            onDataReadyCallbackDetailNb.onDataReadyDetailNb(notebookModel)
        }
    }
}

interface OnDataReadyCallbackDetailNb {
    fun onDataReadyDetailNb(data: NotebookTable)

}