package ru.kavunov.runnotebook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import ru.kavunov.runnotebook.MVVM.ViewModel.DialogViewModel

class QuitDialog: DialogFragment() {

    private lateinit var dialogViewModel: DialogViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.quit_dialog_fragment, container, false)
        dialogViewModel = ViewModelProvider(requireActivity()).get(DialogViewModel::class.java)

        view.findViewById<Button>(R.id.buttonNo).setOnClickListener{
            dismiss()
        }

        view.findViewById<Button>(R.id.buttonYes).setOnClickListener {
            activity?.finish()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)

    }

}