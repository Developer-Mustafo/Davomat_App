package uz.coder.davomatapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.coder.davomatapp.R
import uz.coder.davomatapp.domain.davomat.Davomat

class DavomatAdapter:ListAdapter<Davomat,DavomatAdapter.VH>(DavomatDiff()) {
    inner class VH(view:View):RecyclerView.ViewHolder(view){
        val name: TextView = view.findViewById(R.id.name)
        val surname: TextView = view.findViewById(R.id.surname)
        val time: TextView = view.findViewById(R.id.time)
        val davomat: TextView = view.findViewById(R.id.davomat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layout = when(viewType){
            ENABLED-> R.layout.item_davomat_enabled
            DISABLED->R.layout.item_davomat_disabled
            else->throw RuntimeException("look at viewType $viewType")
        }
        return VH(LayoutInflater.from(parent.context).inflate(layout,parent,false))
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).davomat == "Bor"){
            ENABLED
        }else{
            DISABLED
        }
    }
    override fun onBindViewHolder(holder: VH, position: Int) {
        val str = "Davomati: "
        holder.name.text = getItem(position).name
        holder.surname.text = getItem(position).surname
        holder.time.text = getItem(position).vaqt
        holder.davomat.text = str.plus(getItem(position).davomat)
    }
    companion object{
        const val ENABLED = 0
        const val DISABLED = 1
        const val POOL_SIZE = 15
    }
}