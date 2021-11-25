package ru.kavunov.runnotebook.MVVM.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kavunov.runnotebook.MVVM.Model.PhotoModel
import ru.kavunov.runnotebook.MVVM.Repo.DetailPhotoRepo
import ru.kavunov.runnotebook.MVVM.Repo.OnDataReadyCallbackPhotoDet
import ru.kavunov.runnotebook.bd.PhotoTable

class DetailPhotoViewModel(application: Application) : AndroidViewModel(application){

    lateinit var detailPhotoRepo: DetailPhotoRepo
    val phototable: LiveData<PhotoTable> get() = _phototable
    var _phototable = MutableLiveData<PhotoTable>()

    fun loadPhoto(id: Long){
        CoroutineScope(Dispatchers.Main).launch() {
            detailPhotoRepo= DetailPhotoRepo(id)
            detailPhotoRepo.refreshData(getApplication(), object: OnDataReadyCallbackPhotoDet {

                override fun onDataReadyPhotoDet(data: PhotoTable) {
                    _phototable.postValue(data)
                }
            }
            )}}

    fun insertPhoto( title: String, photo: String, time: Long ){
        CoroutineScope(Dispatchers.Main).launch() {
            PhotoModel.insertData(getApplication(), title, photo, time)
        }
    }

    fun updatetPhoto( id: Long, title: String, photo: String, time: Long ){
        CoroutineScope(Dispatchers.Main).launch() {
            PhotoModel.updateData(getApplication(), id, title, photo, time)
        }
    }

    fun deletePhoto( id: Long){
        CoroutineScope(Dispatchers.Main).launch() {
            PhotoModel.delete(getApplication(), id)
        }
    }
}