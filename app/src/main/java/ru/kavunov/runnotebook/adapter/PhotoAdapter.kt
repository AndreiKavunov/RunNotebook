package ru.kavunov.runnotebook.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.kavunov.runnotebook.R
import ru.kavunov.runnotebook.bd.PhotoNoteBTable
import java.util.ArrayList

class PhotoAdapter(private val onClick: (Long) -> Unit) : RecyclerView.Adapter<PhotoAdapter.PhotoHolder>() {
    var photoNoteBList: MutableList<PhotoNoteBTable> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        return PhotoHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bind(photoNoteBList[position])
    }

    override fun getItemCount(): Int {
        return photoNoteBList.size
    }

    fun initData(listPhotoNoteB: List<PhotoNoteBTable>?) {
        if (listPhotoNoteB!=null){
            photoNoteBList.clear()
            photoNoteBList.addAll(listPhotoNoteB)
            photoNoteBList.sortByDescending{it.dataTime}
            notifyDataSetChanged()
        }
    }

    inner class PhotoHolder(item: View): RecyclerView.ViewHolder(item) {
        var image = item.findViewById<ImageView>(R.id.photo_id)
        fun bind(photoNoteBTable: PhotoNoteBTable){
            try {
                if (photoNoteBTable.photo != "null") {
                    image.setImageURI(Uri.parse(photoNoteBTable.photo))
                } else {
                    image.setImageResource(R.drawable.no_photo)
                }
            }
            catch (e: Exception) {
                image.setImageResource(R.drawable.no_photo)
            }
            itemView.setOnClickListener { view ->
                photoNoteBTable.photoNbId?.let {
                    onClick(it)
                }
            }

        }
    }

}
