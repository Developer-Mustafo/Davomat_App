package uz.coder.davomatapp.fragment.composeUi

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import uz.coder.davomatapp.R
import uz.coder.davomatapp.model.Course
import uz.coder.davomatapp.todo.userId
import uz.coder.davomatapp.ui.AttendanceTopAppBar
import uz.coder.davomatapp.ui.CourseItem
import uz.coder.davomatapp.ui.ErrorDialog
import uz.coder.davomatapp.ui.InternetErrorDialog
import uz.coder.davomatapp.ui.MenuItem
import uz.coder.davomatapp.viewModel.HomeViewModel
import uz.coder.davomatapp.viewModel.NetworkViewModel
import uz.coder.davomatapp.viewModel.state.HomeState
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
    networkViewModel: NetworkViewModel
) {
    val viewModel = viewModel<HomeViewModel>()
    val context = LocalContext.current
    val activity = context as FragmentActivity
    val lifecycleOwner = LocalLifecycleOwner.current
    var list by remember {
        mutableStateOf<List<Course>>(emptyList())
    }
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
            } ?: Log.e("StudentFile", "❌ Fayl tanlanmadi yoki uri null!")
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
            } ?: Log.e("AttendanceFile", "❌ Fayl tanlanmadi yoki uri null!")
        }
    }

    // 🔹 UI qismi
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.theme_background)),
        topBar = {
            AttendanceTopAppBar(
                title = stringResource(R.string.list_of_courses),
                menus = menuItems(context),
                onClicked = { parent, child ->
                    when (parent) {
                        0 -> activity.toProfile()
                        1 -> createCourse()
                        2 -> createGroup()
                        3 -> if (child == 0)
                            filePicker(activity, studentLauncher)
                        else createStudent()
                        4 -> if (child == 0)
                            filePicker(activity, attendanceLauncher)
                        else createAttendance()
                    }
                }
            )
        },
        contentWindowInsets = WindowInsets(0)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.theme_background))
                .padding(padding)
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                itemsIndexed(list) { _, item ->
                    CourseItem(item = item)
                }
            }
        }
    }
    LaunchedEffect(viewModel.state) {
        viewModel.state.collect {
            when(it){
                HomeState.Done -> {

                }
                is HomeState.Error -> {
                    ErrorDialog.show(context, it.error).show()
                }
                HomeState.Init -> {

                }
                HomeState.Loading -> {

                }
                is HomeState.Success -> {
                    list = it.date
                }
            }
        }
    }
    // 🔹 Internet tarmoq holatini kuzatish
    DisposableEffect(lifecycleOwner) {
        observeNetwork(networkViewModel, viewModel, activity, lifecycleOwner)
        onDispose { }
    }
}

// 🔹 Internet kuzatuvchi funksiya
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
                viewModel.getAllCourses(userId)
            }
        }
    }
}

// 🔹 Navigatsiya
fun FragmentActivity.toProfile() {
    val controller =
        (this.supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHost).navController
    controller.navigate(R.id.action_home_to_profile)
}

// 🔹 Faylni nusxalash (URI → File)
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

// 🔹 Fayl tanlash oynasi
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

// 🔹 Fayl URI olish
private fun getPickedFileUri(data: Intent?): Uri? {
    @Suppress("DEPRECATION")
    val files = data?.getParcelableArrayListExtra<com.jaiselrahman.filepicker.model.MediaFile>(
        FilePickerActivity.MEDIA_FILES
    )
    return files?.firstOrNull()?.uri
}

// 🔹 Menular
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

// 🔹 Bo‘sh funksiya (keyin to‘ldiriladi)
private fun createCourse() {
     TODO("Create Course")
}
private fun createGroup() {
     TODO("Create Group")
}
private fun createStudent() {
     TODO("Create Student")
}
private fun createAttendance() {
     TODO("Create Attendance")
}
