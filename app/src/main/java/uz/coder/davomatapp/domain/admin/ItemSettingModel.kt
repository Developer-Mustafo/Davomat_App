package uz.coder.davomatapp.domain.admin

import uz.coder.davomatapp.R

data class ItemSettingModel(
    val id:Int,
    val img:Int = R.drawable.settings_svgrepo_com,
    val name:String = "Settings"
)