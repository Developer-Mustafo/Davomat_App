package uz.coder.davomatapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.coder.davomatapp.databinding.ItemCourseBinding
import uz.coder.davomatapp.domain.coure.Course
import uz.coder.davomatapp.presentation.callback.CourseDiffUtil
import javax.inject.Inject

class CourseAdapter @Inject constructor(private val edit:(Int)->Unit,private val onClick:(Int)->Unit):ListAdapter<Course,CourseAdapter.VH>(CourseDiffUtil()) {
    inner class VH(val binding: ItemCourseBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemCourseBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.name.text = getItem(position).name
        holder.binding.edit.setOnClickListener {
            edit.invoke(getItem(position).id)
        }
        holder.itemView.setOnClickListener {
            onClick.invoke(getItem(position).id)
        }
    }
}
