package ru.kavunov.runnotebook

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.kavunov.runnotebook.MVVM.ViewModel.DialogViewModel

class MainActivity : AppCompatActivity(){

    private var notebookFragment: NotebookFragment? = null
    private lateinit var dialogViewModel: DialogViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
        dialogViewModel = ViewModelProvider(this).get(DialogViewModel::class.java)
        val boNav = findViewById<BottomNavigationView>(R.id.BotNavV)
        boNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.bot_text -> {
                    launchNotebook()
                    true}
                R.id.bot_photo -> {
                    launchPhoto()
                    true}
                R.id.bot_calc -> {
                    launchCalc()
                    true}
                R.id.bot_train -> {
                    launchTrain()
                    true}
                else -> false
            }

        }

        if(savedInstanceState == null){
            notebookFragment = NotebookFragment()
            notebookFragment?.apply {
                supportFragmentManager.beginTransaction()
                    .add(R.id.main_cont_fragment, this, "NotebookFragment")
                    .commit()
            } }
        else {
            notebookFragment =
                supportFragmentManager.findFragmentByTag("NotebookFragment") as? NotebookFragment
        }
    }
    fun launchNotebook(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_cont_fragment, NotebookFragment())
            .commit()
        Frag.display = ConstanceFragment.BOT_NAV
    }
    fun launchPhoto() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_cont_fragment, PhotoFragment())
            .commit()
        Frag.display = ConstanceFragment.BOT_NAV
    }
    fun launchCalc() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_cont_fragment, CalculatorFragment())
            .commit()
        Frag.display = ConstanceFragment.BOT_NAV
    }
    fun launchTrain() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_cont_fragment, TrainFragment())
            .commit()
        Frag.display = ConstanceFragment.BOT_NAV
    }


   }
object Frag{
    var display: String = ""
}