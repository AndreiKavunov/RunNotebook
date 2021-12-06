package ru.kavunov.runnotebook.MVVM

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kavunov.runnotebook.MVVM.Model.NotesModel
import ru.kavunov.runnotebook.MVVM.Model.TrainingModel
import ru.kavunov.runnotebook.bd.NotebookTable

class NoteBookRepo (){
    suspend fun refreshData(context: Context, onDataReadyCallbackNotebook: OnDataReadyCallbackNotebook)= withContext(Dispatchers.IO){
        startBd(context)
        val listNotebook = NotesModel.getAll(context)
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
        TrainingModel.insertData(context, 1, "Понедельник", "Отдых")
        TrainingModel.insertData(context, 2, "Вторник", "Отдых")
        TrainingModel.insertData(context, 3, "Среда", "Отдых")
        TrainingModel.insertData(context, 4, "Четверг", "Отдых")
        TrainingModel.insertData(context, 5, "Пятница", "Отдых")
        TrainingModel.insertData(context, 6, "Суббота", "Отдых")
        TrainingModel.insertData(context, 7, "Воскресенье", "Отдых")

    }    }