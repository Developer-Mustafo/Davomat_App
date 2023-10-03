package uz.coder.davomatapp.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import uz.coder.davomatapp.domain.student.Student


class DiffCallBack: DiffUtil.ItemCallback<Student>() {
    override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
        return oldItem == newItem
    }


}