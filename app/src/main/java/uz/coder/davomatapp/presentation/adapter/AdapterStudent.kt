package uz.coder.davomatapp.presentation.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.ItemStudentBinding
import uz.coder.davomatapp.domain.student.Student
import uz.coder.davomatapp.presentation.callback.DiffCallBack
import java.io.File

class AdapterStudent(private val onclick:(Int)->Unit, private val update:(Int)->Unit):ListAdapter<Student,AdapterStudent.VH>(
    DiffCallBack()
) {

    inner class VH(var binding: ItemStudentBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemStudentBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.name.text = getItem(position).name
        holder.binding.surname.text = getItem(position).surname
        if (getItem(position).img == ""){
            holder.binding.studentImg.setImageResource(R.drawable.college_education_graduate_svgrepo_com)
        }else{
            holder.binding.studentImg.setImageURI(Uri.fromFile(File(getItem(position).img)))
        }
        holder.binding.edit.setOnClickListener { update.invoke(getItem(position).id) }
        holder.itemView.setOnClickListener{onclick.invoke(position)}
    }

}