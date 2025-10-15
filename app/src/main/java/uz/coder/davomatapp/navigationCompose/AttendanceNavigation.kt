package uz.coder.davomatapp.navigationCompose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uz.coder.davomatapp.fragment.composeUi.HomeScreen
import uz.coder.davomatapp.viewModel.NetworkViewModel

@Composable
fun AttendanceNavigation(modifier: Modifier = Modifier, viewModel: NetworkViewModel) {
    val controller = rememberNavController()
    NavHost(controller, startDestination = Screen.Home.route, modifier = modifier){
        composable(Screen.Home.route) {
            HomeScreen(controller = controller, networkViewModel = viewModel)
        }
    }
}

sealed class Screen(val route:String){
    data object Home: Screen("home")
    data object CreateCourse: Screen("create_course")
    data object CreateGroup: Screen("create_group")
    data object CreateStudent: Screen("create_student")
    data object CreateAttendance: Screen("create_attendance")
}