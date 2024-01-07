package uz.coder.davomatapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.coder.davomatapp.databinding.ItemDavomatCourseBinding
import uz.coder.davomatapp.domain.coure.Course
import uz.coder.davomatapp.presentation.callback.CourseDiffUtil
import javax.inject.Inject

class DavomatAdapterCourse @Inject constructor(private val onClick:(Int)->Unit):ListAdapter<Course, DavomatAdapterCourse.VH>(CourseDiffUtil()) {
    inner class VH(val binding: ItemDavomatCourseBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemDavomatCourseBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.name.text = getItem(position).name
        holder.itemView.setOnClickListener {
            onClick.invoke(getItem(position).id)
        }
    }
}
