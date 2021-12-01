package ru.kavunov.runnotebook.MVVM.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DialogViewModel : ViewModel() {
    val name = MutableLiveData<String>()

    fun sendName(text: String) {
          name.value = text
    }
}