package uz.coder.davomatapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.coder.davomatapp.databinding.ItemStudentBinding
import uz.coder.davomatapp.domain.student.Student

class AdapterStudent(private val onclick:(Int)->Unit, private val update:(Int)->Unit):ListAdapter<Student,AdapterStudent.VH>(DiffCallBack()) {

    inner class VH(var binding: ItemStudentBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemStudentBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.name.text = getItem(position).name
        holder.binding.surname.text = getItem(position).surname
        holder.binding.edit.setOnClickListener { update.invoke(position) }
        holder.itemView.setOnClickListener{onclick.invoke(position)}
    }

}