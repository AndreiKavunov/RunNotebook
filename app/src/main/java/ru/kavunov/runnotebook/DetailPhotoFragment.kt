package ru.kavunov.runnotebook

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.opengl.ETC1
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.kavunov.runnotebook.MVVM.ViewModel.DetailPhotoViewModel
import ru.kavunov.runnotebook.bd.PhotoNoteBTable
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import ru.kavunov.runnotebook.MVVM.ViewModel.DialogViewModel
import ru.kavunov.runnotebook.adapter.PhotoDetAdapter
import ru.kavunov.runnotebook.bd.PhotoTables
import java.util.ArrayList
import android.opengl.ETC1.getHeight

import android.view.View.MeasureSpec
import android.opengl.ETC1.getHeight







private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
val imageRequestCode = 10

class DetailPhotoFragment : Fragment() {
    private var mode: String? = null
    private var photoId: Long? = null

    private lateinit var dialogViewModel: DialogViewModel
    val detailPhotoViewModel: DetailPhotoViewModel by viewModels()
    val adapterPhoto = PhotoDetAdapter(::showDialog, ::transitionOnlyPhoto)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mode = it.getString(ARG_PARAM1)
            photoId = it.getLong(ARG_PARAM2)

        }
        Test.listImgUri.clear()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == imageRequestCode){
            var photoTables = PhotoTables(1, data?.data.toString(), 1)
            Test.listImgUri.add(photoTables)
            adapterPhoto.initData(Test.listImgUri)
            activity?.contentResolver?.takePersistableUriPermission(data?.data!!, Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_photo, container, false)
        val buttonAdd = view.findViewById<FloatingActionButton>(R.id.add_photo_button)
        val buttonSave = view.findViewById<FloatingActionButton>(R.id.save_photo_button)
        val buttoDel= view.findViewById<FloatingActionButton>(R.id.delete_photo_button)
        val title = view.findViewById<TextView>(R.id.photo_text)
        val rc_det_photo = view.findViewById<RecyclerView>(R.id.rc_photo_det_id)

//        title.setOnFocusChangeListener { view, hasFocus ->
//            if(!hasFocus) {
//                Log.d("photo", "1")
//            }
//            else{
//                Log.d("photo", "2")
//            }
//        }

        rc_det_photo.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        rc_det_photo.adapter = adapterPhoto

        if(mode == "change"){
            photoId?.let { detailPhotoViewModel.loadPhoto(it) }
            detailPhotoViewModel.phototable.observe(requireActivity(), Observer(::viewDetail))
            detailPhotoViewModel.photos.observe(requireActivity(),  Observer(::updateListImgUri))
            detailPhotoViewModel.photos.observe(requireActivity(),  Observer(adapterPhoto::initData))
        }

        dialogViewModel = ViewModelProvider(requireActivity()).get(DialogViewModel::class.java)
        dialogViewModel.name.observe(requireActivity(), Observer {
               if(it=="delete"){adapterPhoto.initData(Test.listImgUri)}
        })

        buttonAdd.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            startActivityForResult(intent, imageRequestCode)
        }

        buttoDel.setOnClickListener {
            photoId?.let { it1 -> detailPhotoViewModel.deletePhoto(it1) }
            activity?.supportFragmentManager?.popBackStack()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.main_cont_fragment, PhotoFragment())
                ?.commit()

        }

        buttonSave.setOnClickListener{
            if(Test.listImgUri.size > 0){
                if(mode == "new"){
                detailPhotoViewModel.insertPhoto(title.text.toString(),"null",  System.currentTimeMillis(), Test.listImgUri)

            }
                else{detailPhotoViewModel.updatetPhoto(id = photoId!!, title = title.text.toString(), photo= "null",
                    System.currentTimeMillis(), Test.listImgUri)

                }
                activity?.supportFragmentManager?.popBackStack()
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.main_cont_fragment, PhotoFragment())
                    ?.commit()

            }
        }

        return view
    }
    fun viewDetail(photoNoteBTable: PhotoNoteBTable){
        view?.findViewById<TextView>(R.id.photo_text)?.text = photoNoteBTable.title
    }

     fun updateListImgUri(list: List<PhotoTables>){
         Test.listImgUri.clear()
         Test.listImgUri.addAll(list)
     }

    fun showDialog(position: Int) {
        activity?.supportFragmentManager?.let { DeleteDialog().show(it, "TAG") }
        Test.position = position
    }

    fun transitionOnlyPhoto(id: Long) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.main_cont_fragment, OnlyPhotoFragment.newInstance(id))
            ?.addToBackStack("DetailPhoto")
            ?.commit()
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
object Test{
    var listImgUri: MutableList<PhotoTables> = ArrayList()
    var position: Int? = 0
}

