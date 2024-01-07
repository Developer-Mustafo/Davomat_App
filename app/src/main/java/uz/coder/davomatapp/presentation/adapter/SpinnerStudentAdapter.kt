package uz.coder.davomatapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import uz.coder.davomatapp.databinding.ItemSpinnerBinding
import uz.coder.davomatapp.domain.coure.Course
import javax.inject.Inject

class SpinnerStudentAdapter @Inject constructor(private val list: List<Course>):BaseAdapter() {
    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any = list[position].name


    override fun getItemId(position: Int): Long = list[position].id.toLong()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val binding: ItemSpinnerBinding = if (convertView == null) {
            ItemSpinnerBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
        } else {
            ItemSpinnerBinding.bind(convertView)
        }
        binding.txt.text = list[position].name
        return binding.root
    }
}