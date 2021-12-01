package ru.kavunov.runnotebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import ru.kavunov.runnotebook.MVVM.ViewModel.TrainingViewModel
import ru.kavunov.runnotebook.bd.TrainingTable

class TrainFragment : Fragment() {

    private val trainingViewModel: TrainingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_train, container, false)
        trainingViewModel.loadTraining()
        trainingViewModel.listtraining.observe(requireActivity(), Observer(::initData))

        view.findViewById<TextView>(R.id.day1).setOnClickListener {
            transitionDetailTraining(1)
        }
        view.findViewById<TextView>(R.id.day2).setOnClickListener {
            transitionDetailTraining(2)
        }
        view.findViewById<TextView>(R.id.day3).setOnClickListener {
            transitionDetailTraining(3)
        }
        view.findViewById<TextView>(R.id.day4).setOnClickListener {
            transitionDetailTraining(4)
        }
        view.findViewById<TextView>(R.id.day5).setOnClickListener {
            transitionDetailTraining(5)
        }
        view.findViewById<TextView>(R.id.day6).setOnClickListener {
            transitionDetailTraining(6)
        }
        view.findViewById<TextView>(R.id.day7).setOnClickListener {
            transitionDetailTraining(7)
        }

        return view
    }

    fun initData(listTrain: List<TrainingTable>?){
        if (listTrain != null) {
            view?.findViewById<TextView>(R.id.day1)?.text = listTrain.get(0).dayOfWeek + ": \n\n " + listTrain.get(0).description
            view?.findViewById<TextView>(R.id.day2)?.text = listTrain.get(1).dayOfWeek + ": \n\n " + listTrain.get(1).description
            view?.findViewById<TextView>(R.id.day3)?.text = listTrain.get(2).dayOfWeek + ": \n\n " + listTrain.get(2).description
            view?.findViewById<TextView>(R.id.day4)?.text = listTrain.get(3).dayOfWeek + ": \n\n " + listTrain.get(3).description
            view?.findViewById<TextView>(R.id.day5)?.text = listTrain.get(4).dayOfWeek + ": \n\n " + listTrain.get(4).description
            view?.findViewById<TextView>(R.id.day6)?.text = listTrain.get(5).dayOfWeek + ": \n\n " + listTrain.get(5).description
            view?.findViewById<TextView>(R.id.day7)?.text = listTrain.get(6).dayOfWeek + ": \n\n " + listTrain.get(6).description
            }
    }


    fun transitionDetailTraining(day: Long) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.main_cont_fragment, DetailTrainingFragment.newInstance(day))
            ?.addToBackStack("Training")
            ?.commit()
    }

}