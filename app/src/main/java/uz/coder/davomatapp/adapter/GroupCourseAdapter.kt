package uz.coder.davomatapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.coder.davomatapp.databinding.ItemCourseWithGroupsBinding
import uz.coder.davomatapp.model.Group
import uz.coder.davomatapp.model.StudentCourses

class GroupCourseAdapter(
    private val clicked: (List<Group>) -> Unit
) : ListAdapter<StudentCourses, GroupCourseAdapter.CourseViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CourseViewHolder(ItemCourseWithGroupsBinding.inflate(LayoutInflater.from(parent.context), null, false))

    override fun onBindViewHolder(
        holder: CourseViewHolder,
        position: Int
    ) {
        holder.bind(position)
    }

    inner class CourseViewHolder(private val binding: ItemCourseWithGroupsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val studentCourses = getItem(position)
            binding.apply {
                textViewCourseTitle.text = studentCourses.course.title
                textViewCourseDescription.text = studentCourses.course.description
            }
            itemView.setOnClickListener {
                clicked(studentCourses.group)
            }
        }
    }
    data object DiffCallback: DiffUtil.ItemCallback<StudentCourses>(){
        override fun areItemsTheSame(
            oldItem: StudentCourses,
            newItem: StudentCourses
        ): Boolean {
            return oldItem.course.id==newItem.course.id
        }

        override fun areContentsTheSame(
            oldItem: StudentCourses,
            newItem: StudentCourses
        ): Boolean {
            return oldItem==newItem
        }

    }
}