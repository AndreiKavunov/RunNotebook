package ru.kavunov.runnotebook

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.kavunov.runnotebook.MVVM.ViewModel.DetailNbViewModel
import androidx.lifecycle.Observer
import ru.kavunov.runnotebook.bd.NotebookTable
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DetailNoteFragment : Fragment() {

    private var mode: String? = null
    private var id: Long? = null
    private val detailNbViewModel: DetailNbViewModel by viewModels()

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
        val view = inflater.inflate(R.layout.fragment_detail_note, container, false)
        val buttonSave = view.findViewById<FloatingActionButton>(R.id.det_save_id)
        val buttonDelete = view.findViewById<FloatingActionButton>(R.id.det_delete_id)
        val title = view.findViewById<TextView>(R.id.det_title_id)
        val time = view.findViewById<TextView>(R.id.data_notb_det_id)
        val deckription = view.findViewById<TextView>(R.id.det_deckrip_id)
        if(mode == "new"){
            time.visibility = View.GONE
            buttonDelete.visibility = View.GONE}
        if(mode == "change"){
            id?.let { detailNbViewModel.loadNotebook(it) }
            detailNbViewModel.notebookTable.observe(requireActivity(), Observer(::viewDetail))
        }

        buttonSave.setOnClickListener{
            if(mode == "new"){
                if(title.text.toString() != ""){
            detailNbViewModel.insertNotebook(title.text.toString(), deckription.text.toString(),
                System.currentTimeMillis())
                getActivity()?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.main_cont_fragment, NotebookFragment.newInstance("add", 1))
                    ?.commit()
                              }
                else{
                    title.hint = "Введите заголовок заметки"
                              }
                }
            else {
                if (title.text.toString() != "") {
                    detailNbViewModel.updatetNotebook(
                        id!!, title.text.toString(), deckription.text.toString(),
                        System.currentTimeMillis()
                    )

                    getActivity()?.onBackPressed()
                }
                else{
                    title.hint = "Введите заголовок заметки"
                }
            }

                 }

        buttonDelete.setOnClickListener{
            id?.let { it1 -> detailNbViewModel.deleteNotebook(it1)
                getActivity()?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.main_cont_fragment, NotebookFragment.newInstance("del", it1))
                    ?.commit()
            }
        }

        return view
    }

    fun viewDetail(notebookTable: NotebookTable){
        view?.findViewById<TextView>(R.id.det_title_id)?.text = notebookTable.title
        view?.findViewById<TextView>(R.id.det_deckrip_id)?.text = notebookTable.description
        view?.findViewById<TextView>(R.id.data_notb_det_id)?.text = convertLongToTime(notebookTable.dataTime)
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd.MM.yyyy HH:mm")
        return format.format(date)
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: Long) =
            DetailNoteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putLong(ARG_PARAM2, param2)
                }
            }
    }
}