package ru.kavunov.runnotebook

import android.content.Context
import android.os.Bundle
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
    private val notebookAdapter = NotebookAdapter(::transitionAdapterDetail)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mode = it.getString(ARG_PARAM1)
            id = it.getLong(ARG_PARAM2)
                 }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notebook, container, false)
        val rc_notebook = view.findViewById<RecyclerView>(R.id.rc_noteB)
        notebookViewModel.loadNotebook()
        notebookViewModel.listnotebook.observe(requireActivity(), Observer(notebookAdapter::initData))

        rc_notebook.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rc_notebook.adapter = notebookAdapter

        view.findViewById<FloatingActionButton>(R.id.transition_detail_not_button)?.apply {
            setOnClickListener{
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.main_cont_fragment, DetailNoteFragment.newInstance("new", 1))
                    ?.addToBackStack("Notebook")
                    ?.commit()
            }
        }


        return view
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
    fun transitionAdapterDetail(id: Long) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.main_cont_fragment, DetailNoteFragment.newInstance("change", id))
            ?.addToBackStack("Notebook")
            ?.commit()
    }

}
