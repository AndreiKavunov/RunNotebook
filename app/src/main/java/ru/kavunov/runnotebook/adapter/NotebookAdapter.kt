package ru.kavunov.runnotebook.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.kavunov.runnotebook.OnClickAdapterNotebook
import ru.kavunov.runnotebook.R
import ru.kavunov.runnotebook.bd.NotebookTable
import java.text.SimpleDateFormat
import java.util.*

class NotebookAdapter: RecyclerView.Adapter<NotebookHolder>() {
    var notebookList: MutableList<NotebookTable> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotebookHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notebook_item, parent, false)
        return NotebookHolder(view)
    }

    override fun onBindViewHolder(holder: NotebookHolder, position: Int) {
        holder.bind(notebookList[position])
    }

    override fun getItemCount(): Int {
        return notebookList.size
    }

    fun initData(listNotB: List<NotebookTable>?) {
        if (listNotB!=null){
            notebookList.clear()
            notebookList.addAll(listNotB)
            notebookList.sortByDescending{it.dataTime}
            notifyDataSetChanged()
            Log.d("tag", "111")
//            notifyItemInserted(listNotB.size)

        }
    }

    fun addData(listNotB: List<NotebookTable>?) {
        if (listNotB!=null){
            notebookList.clear()
            notebookList.addAll(listNotB)
            notebookList.sortByDescending{it.dataTime}
            notifyItemInserted(0)

        }
    }

    fun removeItem(pos: Int){
        notebookList.removeAt(pos)
        notifyItemRangeChanged(0, notebookList.size)
        notifyItemRemoved(pos)

    }
}
class NotebookHolder(item: View):RecyclerView.ViewHolder(item) {
    private var titleText: TextView = item.findViewById(R.id.title_nb)
    private var timeText: TextView = item.findViewById(R.id.time_nb)
    fun bind(noteBookTable: NotebookTable){
        titleText.text = noteBookTable.title
        timeText.text = noteBookTable.dataTime.toString()
        timeText.text =convertLongToTime(noteBookTable.dataTime)
        itemView.setOnClickListener { view ->
            noteBookTable.ntbId?.let {
                (itemView.context as OnClickAdapterNotebook)?.transitionAdapterDetailNotebook(it)
            }
        }
    }
}

fun convertLongToTime(time: Long): String {
    val date = Date(time)
//        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
    val format = SimpleDateFormat("dd.MM.yyyy HH:mm")

    return format.format(date)
}