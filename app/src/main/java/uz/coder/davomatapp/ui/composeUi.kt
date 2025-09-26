package uz.coder.davomatapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import uz.coder.davomatapp.R
import uz.coder.davomatapp.model.Group
import uz.coder.davomatapp.model.StudentCourses
import uz.coder.davomatapp.viewModel.HomeStudentViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CourseItem(modifier: Modifier = Modifier, item: StudentCourses){
    val viewModel = viewModel<HomeStudentViewModel>()
    Card(onClick = {
        viewModel.clicked(item.group)
    }, modifier = modifier.fillMaxWidth().padding(10.dp), shape = RoundedCornerShape(20.dp), elevation = CardDefaults.elevatedCardElevation(8.dp), colors = CardDefaults.cardColors(
            colorResource(R.color.theme_background)
        )) {
        Row(modifier
            .fillMaxWidth()
            .padding(15.dp)) {
            Column(modifier.fillMaxWidth().weight(11.5f)) {
                Text(item.course.title, modifier.fillMaxWidth(), fontSize = 25.sp)
                if (item.course.description!=""){
                    Text(item.course.description, modifier.fillMaxWidth(), fontSize = 20.sp)
                }
            }
        }
    }
}

@Composable
fun GroupItem(modifier: Modifier = Modifier, item: Group) {
    Card(modifier.fillMaxWidth().padding(10.dp), colors = CardDefaults.cardColors(colorResource(R.color.theme_background))) {
        Column(modifier.fillMaxWidth().padding(15.dp).padding(vertical = 20.dp)) {
            Text(item.title, fontSize = 17.sp)
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceTopAppBar(modifier: Modifier= Modifier){
    TopAppBar(title = {
        Text(stringResource(R.string.groups), fontSize = 20.sp, color = colorResource(R.color.white))
    }, modifier = modifier.fillMaxWidth(), colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(R.color.dark_yellow)))
}