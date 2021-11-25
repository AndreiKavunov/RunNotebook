package ru.kavunov.runnotebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.kavunov.runnotebook.Interface.OnClickTraining

class MainActivity : AppCompatActivity(), OnClickNotebook, OnClickAdapterNotebook, OnClickPhoto,
OnClickAdapterPhoto, OnClickTraining {

    private var notebookFragment: NotebookFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
    }
    fun launchPhoto() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_cont_fragment, PhotoFragment())
            .commit()
    }
    fun launchCalc() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_cont_fragment, CalculatorFragment())
            .commit()
    }
    fun launchTrain() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_cont_fragment, TrainFragment())
            .commit()
    }

    override fun transitionDetailNotebook() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_cont_fragment, DetailNoteFragment.newInstance("new", 111))
            .addToBackStack(null)
            .commit()
    }

    override fun transitionAdapterDetailNotebook(id: Long) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_cont_fragment, DetailNoteFragment.newInstance("change", id))
            .addToBackStack(null)
            .commit()
    }

    override fun transitionDetailPhoto() {
         supportFragmentManager.beginTransaction()
            .replace(R.id.main_cont_fragment, DetailPhotoFragment.newInstance("new", 111))
            .addToBackStack(null)
            .commit()
    }

    override fun transitionAdapterDetailPhoto(imageView : ImageView, id: Long) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_cont_fragment, DetailPhotoFragment.newInstance("change", id))
            .addToBackStack(null)
//            .addSharedElement(imageView, id.toString())
            .commit()
    }

    override fun transitionDetailTraining(day: Long) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_cont_fragment, DetailTrainingFragment.newInstance(day))
            .addToBackStack(null)
            .commit()
    }
}