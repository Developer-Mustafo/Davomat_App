package uz.coder.davomatapp.presentation.callback

import androidx.recyclerview.widget.DiffUtil
import uz.coder.davomatapp.domain.davomat.Davomat
import javax.inject.Inject

class DavomatDiff @Inject constructor():DiffUtil.ItemCallback<Davomat>() {
    override fun areItemsTheSame(oldItem: Davomat, newItem: Davomat): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Davomat, newItem: Davomat): Boolean {
        return oldItem == newItem
    }

}
