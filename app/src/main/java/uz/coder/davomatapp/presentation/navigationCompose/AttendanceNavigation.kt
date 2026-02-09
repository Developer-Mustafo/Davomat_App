package uz.coder.davomatapp.presentation.navigationCompose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import uz.coder.davomatapp.presentation.fragment.composeUi.CreateCourseScreen
import uz.coder.davomatapp.presentation.fragment.composeUi.CreateGroupScreen
import uz.coder.davomatapp.presentation.fragment.composeUi.HomeScreen
import uz.coder.davomatapp.presentation.viewModel.NetworkViewModel

@Composable
fun AttendanceNavigation(
    modifier: Modifier = Modifier,
    viewModel: NetworkViewModel,
    activity: FragmentActivity?
) {
    val backStack = remember { mutableStateListOf<Screen>(Screen.Home) }
    NavDisplay(modifier = modifier.fillMaxSize(), backStack = backStack, onBack = {
        backStack.removeLastOrNull()
    },
    entryProvider = entryProvider {
        entry<Screen.CreateAttendance> {
            TODO()
        }
        entry<Screen.CreateCourse> {
            CreateCourseScreen(activity = activity, networkViewModel = viewModel, navigateBack = { backStack.removeLastOrNull() })
        }
        entry<Screen.CreateGroup> {
            CreateGroupScreen(activity = activity, networkViewModel = viewModel, navigateBack = { backStack.removeLastOrNull() })
        }
        entry<Screen.CreateStudent> {
            TODO()
        }
        entry<Screen.Home> {
            HomeScreen(networkViewModel = viewModel, activity = activity, navigateToCreateCourse = { backStack.add(Screen.CreateCourse) }, navigateToCreateGroup = { backStack.add(
                Screen.CreateGroup) })
        }
    })
}