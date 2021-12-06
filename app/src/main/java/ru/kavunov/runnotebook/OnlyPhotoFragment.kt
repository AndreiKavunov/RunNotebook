package ru.kavunov.runnotebook

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import ru.kavunov.runnotebook.MVVM.ViewModel.DetailPhotoViewModel
import ru.kavunov.runnotebook.bd.PhotoNoteBTable
import ru.kavunov.runnotebook.bd.PhotoTables


private const val PARAM1 = "param1"

class OnlyPhotoFragment : Fragment() {

    private var photoId: Long? = null
    val detailPhotoViewModel: DetailPhotoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            photoId = it.getLong(PARAM1)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_only_photo, container, false)

        photoId?.let { detailPhotoViewModel.loadOnlyPhoto(it) }
        detailPhotoViewModel.photo.observe(requireActivity(), Observer(::viewDetail))


        return view
    }
    fun viewDetail(photoTables: PhotoTables){
        val image = view?.findViewById<ImageView>(R.id.imageId)
        try {
            if (photoTables.photo != "null") {
                image?.setImageURI(Uri.parse(photoTables.photo))
            } else {
                image?.setImageResource(R.drawable.no_photo)
            }
        }
        catch (e: Exception) {
            image?.setImageResource(R.drawable.no_photo)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: Long) =
            OnlyPhotoFragment().apply {
                arguments = Bundle().apply {
                    putLong(PARAM1, param1)

                }
            }
    }
}