package ru.kavunov.runnotebook.MVVM.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kavunov.runnotebook.MVVM.Model.PhotoDetailModel
import ru.kavunov.runnotebook.MVVM.Model.PhotoModel
import ru.kavunov.runnotebook.MVVM.Repo.DetailPhotoRepo
import ru.kavunov.runnotebook.MVVM.Repo.OnDataReadyCallbackPhotoDet
import ru.kavunov.runnotebook.MVVM.Repo.OnDataReadyCallbackPhotosList
import ru.kavunov.runnotebook.bd.PhotoNoteBTable
import ru.kavunov.runnotebook.bd.PhotoTables

class DetailPhotoViewModel(application: Application) : AndroidViewModel(application){

    lateinit var detailPhotoRepo: DetailPhotoRepo
    val phototable: LiveData<PhotoNoteBTable> get() = _phototable
    var _phototable = MutableLiveData<PhotoNoteBTable>()

    val photos: LiveData<List<PhotoTables>> get() = _photos
    var _photos = MutableLiveData<List<PhotoTables>>()

    val photo: LiveData<PhotoTables> get() = _photo
    var _photo = MutableLiveData<PhotoTables>()

    fun loadPhoto(id: Long){
        CoroutineScope(Dispatchers.Main).launch() {
            detailPhotoRepo= DetailPhotoRepo(id)
            detailPhotoRepo.refreshData(getApplication(), object: OnDataReadyCallbackPhotoDet {

                override fun onDataReadyPhotoDet(data: PhotoNoteBTable) {
                    _phototable.postValue(data)
                }
            }, object : OnDataReadyCallbackPhotosList {
                override fun onDataReadyPhotoDetList(data: List<PhotoTables>) {
                    _photos.postValue(data)
                }

            }
            )}}

    fun loadOnlyPhoto(id: Long){
        CoroutineScope(Dispatchers.Main).launch() {
            val photoModel = PhotoDetailModel.get(getApplication(), id)
            _photo.postValue(photoModel)
            }}

    fun insertPhoto( title: String, photo: String, time: Long, list: List<PhotoTables> ){
        CoroutineScope(Dispatchers.Main).launch() {
            PhotoModel.insertData(getApplication(), title,  time, list)
        }
    }

    fun updatetPhoto( id: Long, title: String, photo: String, time: Long, list: List<PhotoTables> ){
        CoroutineScope(Dispatchers.Main).launch() {
            PhotoModel.updateData(getApplication(), id, title, time, list)
        }
    }

    fun deletePhoto( id: Long){
        CoroutineScope(Dispatchers.Main).launch() {
            PhotoModel.delete(getApplication(), id)
        }
    }
}