package ru.kavunov.runnotebook

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.kavunov.runnotebook.MVVM.ViewModel.PhotoViewModel
import ru.kavunov.runnotebook.adapter.NotebookAdapter
import ru.kavunov.runnotebook.adapter.PhotoAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager

class PhotoFragment : Fragment() {

    private val photoViewModel: PhotoViewModel by viewModels()
    private var onClickPhoto: OnClickPhoto? = null
    private val adapterPhoto = PhotoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo, container, false)
        val rc_photo = view.findViewById<RecyclerView>(R.id.rc_photo)
        photoViewModel.loadCateg()
        photoViewModel.listphoto.observe(requireActivity(), Observer(adapterPhoto::initData))
        rc_photo.layoutManager = GridLayoutManager(getActivity(), 3)
        rc_photo.adapter = adapterPhoto

        view.findViewById<FloatingActionButton>(R.id.transition_detail_photo)?.apply {
            setOnClickListener{
                onClickPhoto!!.transitionDetailPhoto()
            }
        }
             return view
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnClickPhoto){
            onClickPhoto = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        onClickPhoto = null
    }


}