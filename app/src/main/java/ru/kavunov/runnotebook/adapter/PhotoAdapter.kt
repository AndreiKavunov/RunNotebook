package ru.kavunov.runnotebook.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import ru.kavunov.runnotebook.OnClickAdapterPhoto
import ru.kavunov.runnotebook.R
import ru.kavunov.runnotebook.bd.PhotoTable
import java.util.ArrayList

class PhotoAdapter : RecyclerView.Adapter<PhotoHolder>() {
    var photoList: MutableList<PhotoTable> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        return PhotoHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        holder.bind(photoList[position])
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    fun initData(listPhoto: List<PhotoTable>?) {
        if (listPhoto!=null){
            photoList.clear()
            photoList.addAll(listPhoto)
            photoList.sortByDescending{it.dataTime}
            notifyDataSetChanged()
        }
    }
}
class PhotoHolder(item: View): RecyclerView.ViewHolder(item) {
    var image = item.findViewById<ImageView>(R.id.photo_id)
    fun bind(photoTable: PhotoTable){
        try {
            image.transitionName = photoTable.photoId.toString()
            Log.d("tag1", image.transitionName)
            if (photoTable.photo != "null") {
                image.setImageURI(Uri.parse(photoTable.photo))
            } else {
                image.setImageResource(R.drawable.no_photo)
            }
        }
        catch (e: Exception) {
            image.setImageResource(R.drawable.no_photo)
        }
        itemView.setOnClickListener { view ->
            photoTable.photoId?.let {
                (itemView.context as OnClickAdapterPhoto)?.transitionAdapterDetailPhoto(image, it)
            }
        }

    }
}