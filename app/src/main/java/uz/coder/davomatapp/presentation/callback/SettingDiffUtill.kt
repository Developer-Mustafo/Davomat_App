package uz.coder.davomatapp.presentation.callback

import androidx.recyclerview.widget.DiffUtil
import uz.coder.davomatapp.domain.admin.ItemSettingModel
import javax.inject.Inject

class SettingDiffUtill @Inject constructor():DiffUtil.ItemCallback<ItemSettingModel>() {
    override fun areItemsTheSame(oldItem: ItemSettingModel, newItem: ItemSettingModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ItemSettingModel, newItem: ItemSettingModel): Boolean {
        return oldItem == newItem
    }
}