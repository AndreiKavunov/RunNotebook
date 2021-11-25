package ru.kavunov.runnotebook.MVVM.Repo

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kavunov.runnotebook.MVVM.Model.TrainingModel
import ru.kavunov.runnotebook.bd.TrainingTable

class TrainingRepo {
    suspend fun refreshData(context: Context, onDataReadyCallbackTraining: OnDataReadyCallbackTraining)= withContext(
        Dispatchers.IO){
        val listTrain = TrainingModel.getAll(context)

        if (listTrain != null) {
            onDataReadyCallbackTraining.onDataReadyT(listTrain)
        }
    }
}
interface OnDataReadyCallbackTraining {
    fun onDataReadyT(data: List<TrainingTable>)

}