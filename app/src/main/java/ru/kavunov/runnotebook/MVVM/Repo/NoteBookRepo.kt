package ru.kavunov.runnotebook.MVVM

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kavunov.runnotebook.MVVM.Model.NotebookModel
import ru.kavunov.runnotebook.MVVM.Model.TrainingModel
import ru.kavunov.runnotebook.bd.NotebookTable

class NoteBookRepo (){
    suspend fun refreshData(context: Context, onDataReadyCallbackNotebook: OnDataReadyCallbackNotebook)= withContext(Dispatchers.IO){
        startBd(context)
        val listNotebook = NotebookModel.getAll(context)
        if (listNotebook != null) {
            onDataReadyCallbackNotebook.onDataReadyN(listNotebook)
        }
    }
}
interface OnDataReadyCallbackNotebook{
    fun onDataReadyN(data: List<NotebookTable>)

}

suspend fun startBd(context: Context)= withContext(Dispatchers.IO){
    if(TrainingModel.getAll(context)?.size == 0){
        TrainingModel.insertData(context, 1, "Понедельник", "empty")
        TrainingModel.insertData(context, 2, "Вторник", "empty")
        TrainingModel.insertData(context, 3, "Среда", "empty")
        TrainingModel.insertData(context, 4, "Четверг", "empty")
        TrainingModel.insertData(context, 5, "Пятница", "empty")
        TrainingModel.insertData(context, 6, "Суббота", "empty")
        TrainingModel.insertData(context, 7, "Воскресенье", "empty")

    }    }