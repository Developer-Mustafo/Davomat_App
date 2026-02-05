package uz.coder.davomatapp.presentation.navigationCompose

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import uz.coder.davomatapp.presentation.fragment.composeUi.CreateCourseScreen
import uz.coder.davomatapp.presentation.fragment.composeUi.HomeScreen
import uz.coder.davomatapp.presentation.viewModel.NetworkViewModel
import java.util.Map.entry

@Composable
fun AttendanceNavigation(
    modifier: Modifier = Modifier,
    viewModel: NetworkViewModel,
    activity: FragmentActivity?
) {
    val controller = rememberNavController()
    val backStack = remember { mutableStateListOf<Any>(Screen.Home) }
    NavDisplay(backStack, onBack = {
        backStack.removeLastOrNull()
    },
        entryProvider = {key->
            when(key){
                Screen.Home -> NavEntry(Unit){ HomeScreen(networkViewModel = viewModel, activity = activity, navigateToCreateCourse = { backStack.add(Screen.CreateCourse) }) }
                Screen.CreateCourse -> NavEntry(Unit){ CreateCourseScreen(controller = controller, networkViewModel = viewModel, activity = activity) }
                else -> NavEntry(Unit){ Text("Unknown route") }
            }
        })
//    NavHost(controller, startDestination = Screen.Home, modifier = modifier){
//        composable<Screen.Home>{
//            HomeScreen(controller = controller, networkViewModel = viewModel, activity = activity)
//        }
//        composable<Screen.CreateCourse>{
//            CreateCourseScreen(controller = controller, networkViewModel = viewModel, activity = activity)
//        }
//        composable<Screen.CreateGroup>{
//
//        }
//        composable<Screen.CreateStudent>{
//
//        }
//        composable<Screen.CreateAttendance>{
//
//        }
//    }
}