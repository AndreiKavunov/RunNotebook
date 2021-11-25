package ru.kavunov.runnotebook

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.kavunov.runnotebook.MVVM.ViewModel.DetailPhotoViewModel
import ru.kavunov.runnotebook.bd.PhotoTable
import androidx.lifecycle.Observer
import androidx.transition.TransitionInflater

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
val imageRequestCode = 10

class DetailPhotoFragment : Fragment() {
    private var mode: String? = null
    private var photoId: Long? = null
    var imgUri: String? = null
    val detailPhotoViewModel: DetailPhotoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mode = it.getString(ARG_PARAM1)
            photoId = it.getLong(ARG_PARAM2)

        }
//        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move).apply {
//            duration = 2000
//        }
//        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move).apply {
//            duration = 2000
//        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == imageRequestCode){
            view?.findViewById<ImageView>(R.id.photo_det_id)?.setImageURI(data?.data)
            imgUri = data?.data.toString()
//            contentResolver.takePersistableUriPermission(data?.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)

        }        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_photo, container, false)
        val buttonAdd = view.findViewById<FloatingActionButton>(R.id.add_photo_button)
        val buttonSave = view.findViewById<FloatingActionButton>(R.id.save_photo_button)
        val buttoDel= view.findViewById<FloatingActionButton>(R.id.delete_photo_button)
        val title = view.findViewById<TextView>(R.id.photo_text)

        if(mode == "change"){
            photoId?.let { detailPhotoViewModel.loadPhoto(it) }
            detailPhotoViewModel.phototable.observe(requireActivity(), Observer(::viewDetail))
        }

        buttonAdd.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            startActivityForResult(intent, imageRequestCode)
        }

        buttoDel.setOnClickListener {
            photoId?.let { it1 -> detailPhotoViewModel.deletePhoto(it1) }
            getActivity()?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_cont_fragment, PhotoFragment())
                ?.commit()
        }

        buttonSave.setOnClickListener{
            if(imgUri != null){
                if(mode == "new"){
                detailPhotoViewModel.insertPhoto(title.text.toString(), imgUri.toString(),
                    System.currentTimeMillis())
            }
                else{detailPhotoViewModel.updatetPhoto(id = photoId!!, title = title.text.toString(), photo= imgUri.toString(),
                    System.currentTimeMillis())

                }
                activity?.onBackPressed()
            }
        }

        return view
    }
    fun viewDetail(photoTable: PhotoTable){
        view?.findViewById<TextView>(R.id.photo_text)?.text = photoTable.title
        view?.findViewById<ImageView>(R.id.photo_det_id)?.transitionName = photoTable.photoId.toString()
        try {
            view?.findViewById<ImageView>(R.id.photo_det_id)
                ?.setImageURI(Uri.parse(photoTable.photo))
        }
        catch (e: Exception){
            view?.findViewById<ImageView>(R.id.photo_det_id)
                ?.setImageResource(R.drawable.no_photo)
        }

    }

    companion object {

        fun newInstance(param1: String, param2: Long) =
            DetailPhotoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putLong(ARG_PARAM2, param2)
                }
            }
    }
}

