package ru.kavunov.runnotebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.kavunov.runnotebook.MVVM.ViewModel.DetailTrainingViewModel
import ru.kavunov.runnotebook.bd.TrainingTable

private const val ARG_PARAM1 = "param1"

class DetailTrainingFragment : Fragment() {

    private var trainId: Long? = null
    val detailTrainingViewModel: DetailTrainingViewModel by viewModels()
    var trainingT: TrainingTable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            trainId = it.getLong(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_training, container, false)
        val buttonSave = view.findViewById<FloatingActionButton>(R.id.save_training_id)
        val trainingText = view.findViewById<TextView>(R.id.training_id)
        if(trainId != null){
            trainId?.let { detailTrainingViewModel.loadTraining(it) }
            detailTrainingViewModel.traintable.observe(requireActivity(), Observer(::viewDetail))
        }

        buttonSave.setOnClickListener {
            if(trainId != null) {
                detailTrainingViewModel.updatetTrain(trainId!!, trainingT!!.dayOfWeek, trainingText.text.toString())
                activity?.supportFragmentManager?.popBackStack()
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.main_cont_fragment, TrainFragment())
                    ?.commit()
                Frag.display = ConstanceFragment.BOT_NAV

            }
        }

        return view
    }

    fun viewDetail(trainingTable: TrainingTable){
        view?.findViewById<TextView>(R.id.day_week_id)?.text = trainingTable.dayOfWeek
        view?.findViewById<TextView>(R.id.training_id)?.text = trainingTable.description
        trainingT = trainingTable
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: Long) =
            DetailTrainingFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PARAM1, param1)
                }
            }
    }
}