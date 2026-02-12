package uz.coder.davomatapp.presentation.fragment.composeUi

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHost
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.jaiselrahman.filepicker.model.MediaFile
import uz.coder.davomatapp.R
import uz.coder.davomatapp.domain.model.Course
import uz.coder.davomatapp.presentation.ui.AttendanceTopAppBar
import uz.coder.davomatapp.presentation.ui.CourseItem
import uz.coder.davomatapp.presentation.ui.ErrorDialog
import uz.coder.davomatapp.presentation.ui.InternetErrorDialog
import uz.coder.davomatapp.presentation.ui.MenuItem
import uz.coder.davomatapp.presentation.viewModel.HomeViewModel
import uz.coder.davomatapp.presentation.viewModel.NetworkViewModel
import uz.coder.davomatapp.presentation.viewModel.state.HomeState
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    networkViewModel: NetworkViewModel,
    activity: FragmentActivity?,
    navigateToCreateCourse: () -> Unit,
    navigateToCreateGroup: () -> Unit
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var list by remember { mutableStateOf<List<Course>>(emptyList()) }

    val studentLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = getPickedFileUri(result.data)
            uri?.let {
                val filePath = copyUriToTempFile(it, context)
                val file = File(filePath!!)
                viewModel.uploadStudentExcel(file)
                Log.d("StudentFile", "📘 Tanlangan student fayl: $filePath")
            }
        }
    }

    val attendanceLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = getPickedFileUri(result.data)
            uri?.let {
                val filePath = copyUriToTempFile(it, context)
                val file = File(filePath!!)
                viewModel.uploadAttendanceExcel(file)
                Log.d("AttendanceFile", "🗒️ Tanlangan attendance fayl: $filePath")
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background, // ✅ to‘g‘ri yo‘l
        topBar = {
            AttendanceTopAppBar(
                title = stringResource(R.string.list_of_courses),
                menus = menuItems(context),
                onClicked = { parent, child ->
                    when (parent) {
                        0 -> activity?.toProfile()
                        1 -> navigateToCreateCourse()
                        2 -> navigateToCreateGroup()
                        3 -> if (child == 0) filePicker(activity!!, studentLauncher) else createStudent()
                        4 -> if (child == 0) filePicker(activity!!, attendanceLauncher) else createAttendance()
                    }
                }
            )
        },
        contentWindowInsets = WindowInsets(0)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                itemsIndexed(list) { _, item ->
                    CourseItem(item = item)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.state.collect {
            when (it) {
                HomeState.Done -> Unit
                is HomeState.Error -> ErrorDialog.show(context, it.error).show()
                HomeState.Init -> Unit
                HomeState.Loading -> Unit
                is HomeState.Success -> list = it.date
            }
        }
    }

    DisposableEffect(lifecycleOwner) {
        observeNetwork(networkViewModel, viewModel, activity!!, lifecycleOwner)
        onDispose { }
    }
}


private fun observeNetwork(
    networkViewModel: NetworkViewModel,
    viewModel: HomeViewModel,
    context: FragmentActivity,
    viewLifecycleOwner: LifecycleOwner
) {
    networkViewModel.networkState.observe(viewLifecycleOwner) { state ->
        state?.let {
            if (!it) {
                InternetErrorDialog.show(context).show()
            } else {
                viewModel.getAllCourses()
            }
        }
    }
}

fun FragmentActivity.toProfile() {
    val controller =
        (this.supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHost).navController
    controller.navigate(R.id.action_home_to_profile)
}

private fun copyUriToTempFile(uri: Uri, context: Context): String? {
    return try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val file = File(context.cacheDir, DocumentsContract.getDocumentId(uri).split(":").last())
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close()
        outputStream.close()
        file.absolutePath
    } catch (e: Exception) {
        Log.e("FileCopy", "❌ Faylni nusxalashda xatolik: ${e.message}")
        null
    }
}

private fun filePicker(
    activity: Activity,
    launcher: ActivityResultLauncher<Intent>
) {
    val intent = Intent(activity, FilePickerActivity::class.java).apply {
        putExtra(
            FilePickerActivity.CONFIGS,
            Configurations.Builder()
                .setCheckPermission(true)
                .setShowFiles(true)
                .setShowImages(false)
                .setShowAudios(false)
                .setShowVideos(false)
                .setMaxSelection(1)
                .setSuffixes("xlsx")
                .build()
        )
    }
    launcher.launch(intent)
}

private fun getPickedFileUri(data: Intent?): Uri? {
    @Suppress("DEPRECATION")
    val files = data?.getParcelableArrayListExtra<MediaFile>(
        FilePickerActivity.MEDIA_FILES
    )
    return files?.firstOrNull()?.uri
}

private fun menuItems(context: Context) = listOf(
    MenuItem(context.getString(R.string.go_profile), R.drawable.ic_profile),
    MenuItem(context.getString(R.string.createCourse), R.drawable.ic_course),
    MenuItem(context.getString(R.string.createGroup), R.drawable.ic_group),
    MenuItem(
        context.getString(R.string.createStudent),
        R.drawable.ic_student,
        listOf(
            MenuItem(context.getString(R.string.upload_xls), R.drawable.ic_xlsx),
            MenuItem(context.getString(R.string.add_one), R.drawable.ic_student)
        )
    ),
    MenuItem(
        context.getString(R.string.createAttendance),
        R.drawable.ic_attendance,
        listOf(
            MenuItem(context.getString(R.string.upload_xls), R.drawable.ic_xlsx),
            MenuItem(context.getString(R.string.add_one_by_one), R.drawable.ic_attendance)
        )
    )
)
private fun createStudent() {
     TODO("Create Student")
}
private fun createAttendance() {
     TODO("Create Attendance")
}
