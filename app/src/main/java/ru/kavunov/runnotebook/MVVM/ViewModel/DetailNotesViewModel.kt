package ru.kavunov.runnotebook.MVVM.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kavunov.runnotebook.MVVM.DetailNbRepo
import ru.kavunov.runnotebook.MVVM.Model.NotesModel
import ru.kavunov.runnotebook.MVVM.OnDataReadyCallbackDetailNb
import ru.kavunov.runnotebook.bd.NotebookTable

class DetailNotesViewModel(application: Application) : AndroidViewModel(application){

    lateinit var detailNbRepo: DetailNbRepo
    val notebookTable: LiveData<NotebookTable> get() = _notebookTable
    var _notebookTable = MutableLiveData<NotebookTable>()

    fun loadNotebook(id: Long){
        CoroutineScope(Dispatchers.Main).launch() {
            detailNbRepo= DetailNbRepo(id)
            detailNbRepo.refreshData(getApplication(), object: OnDataReadyCallbackDetailNb {
                override fun onDataReadyDetailNb(data: NotebookTable) {
                    _notebookTable.postValue(data)
                }
            }
            )}}

    fun insertNotebook(title: String, description: String, time: Long){
        CoroutineScope(Dispatchers.Main).launch() {
            NotesModel.insertData(getApplication(), title, description, time)
        }
    }

    fun updatetNotebook(id: Long, title: String, description: String, time: Long){
        CoroutineScope(Dispatchers.Main).launch() {
            NotesModel.updateData(getApplication(), id, title, description, time)
        }
    }

    fun deleteNotebook( id: Long){
        CoroutineScope(Dispatchers.Main).launch() {
            NotesModel.delete(getApplication(), id)
        }
    }
}