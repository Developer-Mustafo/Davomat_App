package uz.coder.davomatapp.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import uz.coder.davomatapp.App.application
import uz.coder.davomatapp.R
import uz.coder.davomatapp.model.Attendance
import uz.coder.davomatapp.model.Course
import uz.coder.davomatapp.model.Group
import uz.coder.davomatapp.model.Student
import uz.coder.davomatapp.model.StudentCourses
import uz.coder.davomatapp.viewModel.HomeStudentViewModel
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceTopAppBar(modifier: Modifier = Modifier, title: String, menus: List<MenuItem> = emptyList(), onClicked:((Int, Int)-> Unit)? = null) {
    var menuExpanded by remember { mutableStateOf(false) }
    var selectedSubMenu by remember { mutableStateOf<Pair<List<MenuItem>?, Int>?>(Pair(null, -1)) }
    CenterAlignedTopAppBar(
        title = {
            Text(
                title,
                color = colorResource(R.color.white)
            )
        },
        modifier = modifier.fillMaxWidth(),
        windowInsets = WindowInsets(0),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.dark_yellow)
        ),
        actions = {
            IconButton(onClick = { menuExpanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null
                )
            }
            DropdownMenu(menuExpanded, onDismissRequest = {
                menuExpanded=false
            }) {
                menus.forEachIndexed {index, menu ->
                    DropdownMenuItem(text = {
                        Text(menu.text, color = colorResource(R.color.dark_yellow))
                    }, onClick = {
                        if (menu.groups.isNotEmpty()){
                            selectedSubMenu=Pair(menu.groups, index)
                        }else{
                            menuExpanded=!menuExpanded
                            onClicked?.invoke(index, -1)
                        }
                    }, leadingIcon = {
                        Icon(painterResource(menu.icon), null, tint = colorResource(R.color.dark_yellow))
                    })
                }
            }
            selectedSubMenu?.first?.let {
                DropdownMenu(
                    expanded = true,
                    onDismissRequest = { selectedSubMenu = null }
                ) {
                    it.forEachIndexed { index, subMenu ->
                        DropdownMenuItem(
                            text = { Text(subMenu.text, color = colorResource(R.color.dark_yellow)) },
                            leadingIcon = { Icon(painterResource(subMenu.icon), contentDescription = null, tint = colorResource(R.color.dark_yellow)) },
                            onClick = {
                                onClicked?.invoke(selectedSubMenu?.second?:-1, index)
                                selectedSubMenu = null
                                menuExpanded = false
                            }
                        )
                    }
                }
            }
        }
    )
}
data class MenuItem(
    val text:String,
    val icon:Int,
    val groups: List<MenuItem> = emptyList()
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AttendanceCalendarPager(
    student: Student?,
    attendanceList: List<Attendance>
) {
    val today = LocalDate.now()
    val startMonth = YearMonth.from(student?.createdDate?:return)
    val currentMonth = YearMonth.from(today)
    val months = remember {
        generateSequence(startMonth) { it.plusMonths(1) }
            .takeWhile { it <= currentMonth }
            .toList()
    }

    val pagerState = rememberPagerState(initialPage = months.size - 1){
        months.size
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            val month = months[page]
            val daysInMonth = (1..month.lengthOfMonth()).map { day ->
                LocalDate.of(month.year, month.month, day)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${month.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${month.year}",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(7),
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(daysInMonth) { date ->
                        val attendance = attendanceList.find { it.date == date }
                        val isBeforeJoin = date.isBefore(student.createdDate)
                        val isFuture = date.isAfter(today)

                        val color = when {
                            isBeforeJoin || isFuture -> Color.LightGray.copy(alpha = 0.5f)
                            attendance?.status == "+" -> Color(0xFF4CAF50)
                            attendance?.status == "-" -> Color(0xFFF44336)
                            else -> Color.Gray
                        }

                        Box(
                            modifier = Modifier
                                .size(45.dp)
                                .background(color, shape = MaterialTheme.shapes.medium),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = date.dayOfMonth.toString(),
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun StudentProfile(
    student: Student?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color(0xFFBBDEFB)),
            contentAlignment = Alignment.Center
        ) {
            val initial = student?.fullName?.firstOrNull()?.toString() ?: "?"
            Text(
                text = initial,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = student?.fullName?.ifBlank { application.getString(R.string.unkown_user) }?:application.getString(R.string.unkown_user),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}
@Composable
fun StudentCourseItem(modifier: Modifier = Modifier, item: StudentCourses) {
    val viewModel = viewModel<HomeStudentViewModel>()
    Card(
        onClick = {
            viewModel.clicked(item.group)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.theme_background)
        )
    ) {
        Row(
            modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Column(
                modifier
                    .fillMaxWidth()
                    .weight(11.5f)
            ) {
                Text(item.course.title, modifier.fillMaxWidth(), fontSize = 25.sp)
                if (item.course.description.isNotEmpty()) {
                    Text(item.course.description, modifier.fillMaxWidth(), fontSize = 20.sp)
                }
            }
        }
    }
}
@Composable
fun CourseItem(modifier: Modifier = Modifier, item: Course, onClick: ((Course) -> Unit)?=null) {
    Card(
        onClick = {
            onClick?.invoke(item)
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.theme_background)
        )
    ) {
        Row(
            modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Column(
                modifier
                    .fillMaxWidth()
                    .weight(11.5f)
            ) {
                Text(item.title, modifier.fillMaxWidth(), fontSize = 25.sp)
                if (item.description.isNotEmpty()) {
                    Text(item.description, modifier.fillMaxWidth(), fontSize = 20.sp)
                }
            }
        }
    }
}

@Composable
fun GroupItem(modifier: Modifier = Modifier, item: Group, onClick: ((Group) -> Unit)? = null) {
    Card(
        onClick = { onClick?.invoke(item) },
        modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(colorResource(R.color.theme_background)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier
                .fillMaxWidth()
                .padding(15.dp)
                .padding(vertical = 20.dp)
        ) {
            Text(item.title, fontSize = 17.sp)
        }
    }
}