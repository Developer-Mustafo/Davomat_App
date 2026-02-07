package uz.coder.davomatapp.presentation.navigationCompose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import uz.coder.davomatapp.presentation.fragment.composeUi.CreateCourseScreen
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
    entryProvider = {key->
        when(key){
            Screen.CreateAttendance -> TODO()
            Screen.CreateCourse -> NavEntry(Screen.CreateCourse){CreateCourseScreen(activity = activity, networkViewModel = viewModel, navigateBack = { backStack.removeLastOrNull() })}
            Screen.CreateGroup -> TODO()
            Screen.CreateStudent -> TODO()
            Screen.Home -> NavEntry(Screen.Home){HomeScreen(networkViewModel = viewModel, activity = activity, navigateToCreateCourse = { backStack.add(Screen.CreateCourse) })}
        }
    })
}