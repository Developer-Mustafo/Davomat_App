package uz.coder.davomatapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.coder.davomatapp.databinding.ItemSettingBinding
import uz.coder.davomatapp.domain.admin.ItemSettingModel
import uz.coder.davomatapp.presentation.callback.SettingDiffUtill

class SettingAdapter(private val onClick:(Int)->Unit):ListAdapter<ItemSettingModel,SettingAdapter.VH>(SettingDiffUtill()){
    inner class VH(val binding: ItemSettingBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(position:Int){
            binding.icon.setImageResource(getItem(position).img)
            binding.txt.text = getItem(position).name
            itemView.setOnClickListener {
                onClick(getItem(position).id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH = VH(ItemSettingBinding.inflate(
        LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(position)
    }
}