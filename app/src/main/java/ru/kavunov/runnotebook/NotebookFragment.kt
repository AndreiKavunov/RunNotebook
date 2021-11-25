package ru.kavunov.runnotebook

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import jp.wasabeef.recyclerview.animators.LandingAnimator
import ru.kavunov.runnotebook.MVVM.ViewModel.NotebookViewModel
import ru.kavunov.runnotebook.adapter.NotebookAdapter
import java.text.SimpleDateFormat
import java.util.*
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class NotebookFragment : Fragment() {

    private var mode: String? = null
    private var id: Long? = null
    private val notebookViewModel: NotebookViewModel by viewModels()
    private var onClickNotebook: OnClickNotebook? = null
    private val notebookAdapter = NotebookAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mode = it.getString(ARG_PARAM1)
            id = it.getLong(ARG_PARAM2)
                 }
        Log.d("tag", mode.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notebook, container, false)
        val rc_notebook = view.findViewById<RecyclerView>(R.id.rc_noteB)
        notebookViewModel.loadNotebook()
        notebookViewModel.listnotebook.observe(requireActivity(), Observer(notebookAdapter::initData))
//        when(mode){
//            "add" -> notebookViewModel.listnotebook.observe(requireActivity(), Observer(notebookAdapter::addData))
////            "del" -> notebookViewModel.listnotebook.observe(requireActivity(), Observer(notebookAdapter::removeItem))
//            "del" -> notebookAdapter.removeItem(id!!.toInt())
//            else -> notebookViewModel.listnotebook.observe(requireActivity(), Observer(notebookAdapter::initData))
//        }

        rc_notebook.layoutManager = LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false)
        rc_notebook.adapter = notebookAdapter
        rc_notebook.itemAnimator = LandingAnimator(OvershootInterpolator(1f))
        rc_notebook.itemAnimator?.apply {

            addDuration = 1000
            removeDuration = 1000
            moveDuration = 1000
            changeDuration = 1000
        }
        view.findViewById<FloatingActionButton>(R.id.transition_detail_not_button)?.apply {
            setOnClickListener{
                onClickNotebook?.transitionDetailNotebook()
                var tim = System.currentTimeMillis()
                Log.d("tag",tim.toString())
                convertLongToTime(tim)
            }
        }


        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnClickNotebook){
            onClickNotebook = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        onClickNotebook = null
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd.MM.yyyy HH:mm")
//        val format = SimpleDateFormat("dd-MM-yy kk:mm")
        return format.format(date)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: Long) =
            NotebookFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putLong(ARG_PARAM2, param2)
                           }
            }
    }

}
