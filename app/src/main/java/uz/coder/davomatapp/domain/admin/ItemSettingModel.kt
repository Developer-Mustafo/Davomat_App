package uz.coder.davomatapp.domain.admin

import uz.coder.davomatapp.R
import javax.inject.Inject

data class ItemSettingModel @Inject constructor(
    val id:Int,
    val img:Int = R.drawable.parametr,
    val name:String = "Settings"
)