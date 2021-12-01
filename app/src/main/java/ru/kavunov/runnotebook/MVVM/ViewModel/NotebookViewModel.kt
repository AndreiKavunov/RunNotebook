package ru.kavunov.runnotebook.MVVM.ViewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kavunov.runnotebook.MVVM.NoteBookRepo

import ru.kavunov.runnotebook.MVVM.OnDataReadyCallbackNotebook
import ru.kavunov.runnotebook.bd.NotebookTable

class NotebookViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var noteBookRepo: NoteBookRepo


    val listnotebook: LiveData<List<NotebookTable>> get() = _listnotebook
    var _listnotebook = MutableLiveData<List<NotebookTable>>()

    fun loadNotebook(){
        CoroutineScope(Dispatchers.Main).launch() {
            noteBookRepo= NoteBookRepo()
            noteBookRepo.refreshData(getApplication(), object: OnDataReadyCallbackNotebook {
                 override fun onDataReadyN(data: List<NotebookTable>) {
                     _listnotebook.postValue(data)

                }
            }
            )}}

}