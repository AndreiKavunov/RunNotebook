package ru.kavunov.runnotebook.MVVM.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kavunov.runnotebook.MVVM.NoteBookRepo
import ru.kavunov.runnotebook.MVVM.OnDataReadyCallbackNotebook
import ru.kavunov.runnotebook.MVVM.Repo.OnDataReadyCallbackTraining
import ru.kavunov.runnotebook.MVVM.Repo.TrainingRepo
import ru.kavunov.runnotebook.bd.NotebookTable
import ru.kavunov.runnotebook.bd.TrainingTable

class TrainingViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var trainingRepo: TrainingRepo
    val listtraining: LiveData<List<TrainingTable>> get() = _listtraining
    var _listtraining = MutableLiveData<List<TrainingTable>>()

    fun loadTraining(){
        CoroutineScope(Dispatchers.Main).launch() {
            trainingRepo= TrainingRepo()
            trainingRepo.refreshData(getApplication(), object: OnDataReadyCallbackTraining {
                override fun onDataReadyT(data: List<TrainingTable>) {
                    _listtraining.postValue(data)
                }
            }
            )}}

}