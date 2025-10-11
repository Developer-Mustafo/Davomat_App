@file:Suppress("DEPRECATION")

package uz.coder.davomatapp.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.HorizontalPagerIndicator
import uz.coder.davomatapp.R
import uz.coder.davomatapp.model.Attendance
import uz.coder.davomatapp.model.Group
import uz.coder.davomatapp.model.Student
import uz.coder.davomatapp.model.StudentCourses
import uz.coder.davomatapp.viewModel.HomeStudentViewModel
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceTopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(
                stringResource(R.string.groups),
                fontSize = 20.sp,
                color = colorResource(R.color.white)
            )
        },
        modifier = modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(R.color.dark_yellow)
        )
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AttendanceCalendarPager(
    student: Student?,
    attendanceList: List<Attendance>
) {
    val today = LocalDate.now()
    val startMonth = YearMonth.from(student?.createdDate?:return)
    val currentMonth = YearMonth.from(today)

    // student.createdDate dan hozirgacha bo'lgan barcha oylar ro'yxati
    val months = remember {
        generateSequence(startMonth) { it.plusMonths(1) }
            .takeWhile { it <= currentMonth }
            .toList()
    }
    Log.d(TAG, "AttendanceCalendarPager: $months")

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
        // Avatar
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

        // Ism
        Text(
            text = student?.fullName?.ifBlank { "Noma’lum talaba" }?:"Noma’lum talaba",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}
@Composable
fun CourseItem(modifier: Modifier = Modifier, item: StudentCourses) {
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
private const val TAG = "composeUi"