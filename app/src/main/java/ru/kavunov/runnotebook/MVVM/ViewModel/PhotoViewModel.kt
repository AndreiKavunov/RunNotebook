package ru.kavunov.runnotebook.MVVM.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kavunov.runnotebook.MVVM.Repo.OnDataReadyCallbackPhoto
import ru.kavunov.runnotebook.MVVM.Repo.PhotoRepo

import ru.kavunov.runnotebook.bd.PhotoNoteBTable

class PhotoViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var photoRepo: PhotoRepo


    val listphoto: LiveData<List<PhotoNoteBTable>> get() = _listphoto
    var _listphoto = MutableLiveData<List<PhotoNoteBTable>>()

    fun loadCateg(){
        CoroutineScope(Dispatchers.Main).launch() {
            photoRepo= PhotoRepo()
            photoRepo.refreshData(getApplication(), object: OnDataReadyCallbackPhoto {
                override fun onDataReadyP(data: List<PhotoNoteBTable>) {
                    _listphoto.postValue(data)


                }
            }
            )
        }
    }
}