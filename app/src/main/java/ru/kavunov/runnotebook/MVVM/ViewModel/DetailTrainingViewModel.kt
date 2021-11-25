package ru.kavunov.runnotebook.MVVM.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kavunov.runnotebook.MVVM.Model.PhotoModel
import ru.kavunov.runnotebook.MVVM.Model.TrainingModel
import ru.kavunov.runnotebook.MVVM.Repo.DetailPhotoRepo
import ru.kavunov.runnotebook.MVVM.Repo.DetailTrainingRepo
import ru.kavunov.runnotebook.MVVM.Repo.OnDataReadyCallbackPhotoDet
import ru.kavunov.runnotebook.MVVM.Repo.OnDataReadyCallbackTrainDet
import ru.kavunov.runnotebook.bd.PhotoTable
import ru.kavunov.runnotebook.bd.TrainingTable

class DetailTrainingViewModel (application: Application) : AndroidViewModel(application){

    lateinit var detailTrainingRepo: DetailTrainingRepo
    val traintable: LiveData<TrainingTable> get() = _traintable
    var _traintable = MutableLiveData<TrainingTable>()

    fun loadTraining(id: Long){
        CoroutineScope(Dispatchers.Main).launch() {
            detailTrainingRepo= DetailTrainingRepo(id)
            detailTrainingRepo.refreshData(getApplication(), object: OnDataReadyCallbackTrainDet {

                override fun onDataReadytrainDet(data: TrainingTable) {
                    _traintable.postValue(data)
                }
            }
            )}}

    fun updatetTrain( id: Long, dayOfWeek: String, description: String){
        CoroutineScope(Dispatchers.Main).launch() {
            TrainingModel.updateData(getApplication(), id, dayOfWeek, description)
        }
    }

}