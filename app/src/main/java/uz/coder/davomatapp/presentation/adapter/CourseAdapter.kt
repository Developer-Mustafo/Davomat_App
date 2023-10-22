package uz.coder.davomatapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.coder.davomatapp.databinding.ItemCourseBinding
import uz.coder.davomatapp.domain.coure.Course
import uz.coder.davomatapp.presentation.callback.CourseDiffUtil

class CourseAdapter(val editCourse:(Int)->Unit,val onClickCourse:(Int)->Unit):ListAdapter<Course,CourseAdapter.VH>(CourseDiffUtil()) {
    inner class VH(private val binding: ItemCourseBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(id:Int,course: Course){
            binding.apply {
                courseImg.setImageResource(course.img)
                name.text = course.name
                edit.setOnClickListener {
                    editCourse.invoke(id)
                }
                itemView.setOnClickListener {
                    onClickCourse.invoke(id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemCourseBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position).id,getItem(position))
    }
}
