package ru.kavunov.runnotebook.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.kavunov.runnotebook.R
import ru.kavunov.runnotebook.bd.PhotoTables
import java.util.ArrayList

class PhotoDetAdapter(private val onLongClick: (Int) -> Unit, private val onClick: (Long) -> Unit): RecyclerView.Adapter<PhotoDetAdapter.PhotoDetHolder>() {
    var photoList: MutableList<PhotoTables> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoDetHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_det_item, parent, false)
        view.layoutParams = ViewGroup.LayoutParams((parent.width * 0.95).toInt(),ViewGroup.LayoutParams.MATCH_PARENT)
        return PhotoDetHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoDetHolder, position: Int) {
        holder.bind(photoList[position], position)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    fun initData(listPhotoTables: List<PhotoTables>?) {

        if (listPhotoTables!=null){
            photoList.clear()
            photoList.addAll(listPhotoTables)
            notifyDataSetChanged()
        }
    }
    inner class PhotoDetHolder(item: View): RecyclerView.ViewHolder(item) {

        var image = item.findViewById<ImageView>(R.id.photo_det_id)
        fun bind(photoTables: PhotoTables, position: Int){
            try {
                if (photoTables.photo != "null") {
                    image.setImageURI(Uri.parse(photoTables.photo))
                } else {
                    image.setImageResource(R.drawable.no_photo)
                }
            }
            catch (e: Exception) {
                image.setImageResource(R.drawable.no_photo)
            }

            image.setOnClickListener { view ->
                photoTables.id?.let {
                    onClick(it)
                }
            }

            image.setOnLongClickListener {
                 onLongClick(position)
                true
            }

        }
    }
}
