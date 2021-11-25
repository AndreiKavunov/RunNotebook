package ru.kavunov.runnotebook.MVVM.Repo

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.kavunov.runnotebook.MVVM.Model.PhotoModel
import ru.kavunov.runnotebook.MVVM.Model.TrainingModel
import ru.kavunov.runnotebook.bd.PhotoTable
import ru.kavunov.runnotebook.bd.TrainingTable

class DetailTrainingRepo (id: Long) {
    val id = id
    suspend fun refreshData(context: Context, onDataReadyCallbackTrainDet: OnDataReadyCallbackTrainDet)= withContext(
        Dispatchers.IO){
        val trainModel = TrainingModel.getMovieID(context, id)

        if (trainModel != null) {
            onDataReadyCallbackTrainDet.onDataReadytrainDet(trainModel)
        }
    }
}

interface OnDataReadyCallbackTrainDet {
    fun onDataReadytrainDet(data: TrainingTable)

}