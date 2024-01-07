package uz.coder.davomatapp.presentation.callback

import androidx.recyclerview.widget.DiffUtil
import uz.coder.davomatapp.domain.coure.Course
import javax.inject.Inject

class CourseDiffUtil @Inject constructor():DiffUtil.ItemCallback<Course>() {
    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem == newItem
    }
}