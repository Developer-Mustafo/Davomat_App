package uz.coder.davomatapp.presentation.ui

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Edit
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import uz.coder.davomatapp.App.Companion.application
import uz.coder.davomatapp.R
import uz.coder.davomatapp.domain.model.Attendance
import uz.coder.davomatapp.domain.model.Course
import uz.coder.davomatapp.domain.model.Group
import uz.coder.davomatapp.domain.model.Student
import uz.coder.davomatapp.domain.model.StudentCourses
import uz.coder.davomatapp.todo.MyYearMonth
import uz.coder.davomatapp.todo.formattedDate
import uz.coder.davomatapp.todo.orToday

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    menus: List<MenuItem> = emptyList(),
    onClicked: ((Int, Int) -> Unit)? = null
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var selectedSubMenu by remember { mutableStateOf<Pair<List<MenuItem>?, Int>?>(null) }

    CenterAlignedTopAppBar(
        title = {
            Text(
                title,
                color = MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
        },
        modifier = modifier.fillMaxWidth(),
        windowInsets = WindowInsets(0),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        actions = {
            IconButton(onClick = { menuExpanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                menus.forEachIndexed { index, menu ->
                    DropdownMenuItem(
                        text = { Text(menu.text) },
                        leadingIcon = {
                            Icon(
                                painterResource(menu.icon),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        onClick = {
                            if (menu.groups.isNotEmpty()) {
                                selectedSubMenu = menu.groups to index
                            } else {
                                menuExpanded = false
                                onClicked?.invoke(index, -1)
                            }
                        }
                    )
                }
            }

            selectedSubMenu?.first?.let { subs ->
                DropdownMenu(
                    expanded = true,
                    onDismissRequest = { selectedSubMenu = null }
                ) {
                    subs.forEachIndexed { index, sub ->
                        DropdownMenuItem(
                            text = { Text(sub.text) },
                            leadingIcon = {
                                Icon(
                                    painterResource(sub.icon),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            },
                            onClick = {
                                onClicked?.invoke(selectedSubMenu?.second ?: -1, index)
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
    if (student == null) return

    val today = LocalDate.orToday()
    val startDate = LocalDate.parse(student.createdDate.formattedDate())
    val startMonth = MyYearMonth(startDate.year, startDate.month.number)
    val currentMonth = MyYearMonth(today.year, today.month.number)

    val months = remember {
        generateSequence(startMonth) { it.plusMonths(1) }
            .takeWhile { it <= currentMonth }
            .toList()
    }

    val pagerState = rememberPagerState(initialPage = months.size - 1) { months.size }

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
                LocalDate(month.year, month.month, day)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "${month.month.toString().lowercase().replaceFirstChar { it.uppercase() }} ${month.year}",
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
                        val isBeforeJoin = date < startDate
                        val isFuture = date > today

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
                                text = date.day.toString(),
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
fun StudentCourseItem(
    modifier: Modifier = Modifier,
    item: StudentCourses,
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        onClick = { onClick?.invoke() }
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                item.course.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            if (item.course.description.isNotBlank()) {
                Text(
                    item.course.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
fun CourseItem(
    modifier: Modifier = Modifier,
    item: Course,
    onClick: ((Course) -> Unit)? = null,
    onEdit: ((Course) -> Unit)? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        onClick = { onClick?.invoke(item) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Badge
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.title.firstOrNull()?.uppercase() ?: "?",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )
                if (item.description.isNotBlank()) {
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        maxLines = 2
                    )
                }
            }

            if (onEdit != null) {
                IconButton(onClick = { onEdit(item) }) {
                    Icon(
                        imageVector = Icons.Outlined.Edit,
                        contentDescription = "Edit",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}


@Composable
fun GroupItem(
    modifier: Modifier = Modifier,
    item: Group,
    onClick: ((Group) -> Unit)? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        onClick = { onClick?.invoke(item) }
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                item.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}