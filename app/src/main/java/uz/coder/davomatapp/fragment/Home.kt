@file:Suppress("SameParameterValue")

package uz.coder.davomatapp.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import kotlinx.coroutines.launch
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.FragmentHomeBinding
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

class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val networkViewModel by activityViewModels<NetworkViewModel>()
    private val viewModel by viewModels<HomeViewModel>()
    private var list by mutableStateOf<List<Course>>(emptyList())

    private lateinit var studentFilePickerLauncher: ActivityResultLauncher<Intent?>
    private lateinit var attendanceFilePickerLauncher: ActivityResultLauncher<Intent?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        studentFilePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = getPickedFileUri(result.data)
                uri?.let {
                    val filePath = copyUriToTempFile(it)
                    Log.d("StudentFile", "📘 Tanlangan student fayl: $filePath")
                } ?: Log.e("StudentFile", "❌ Fayl tanlanmadi yoki uri null!")
            }
        }

        attendanceFilePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = getPickedFileUri(result.data)
                uri?.let {
                    val filePath = copyUriToTempFile(it)
                    Log.d("AttendanceFile", "🗒️ Tanlangan attendance fayl: $filePath")
                } ?: Log.e("AttendanceFile", "❌ Fayl tanlanmadi yoki uri null!")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val menuItems by lazy {
        listOf(
            MenuItem(requireContext().getString(R.string.go_profile), R.drawable.ic_profile),
            MenuItem(requireContext().getString(R.string.createCourse), R.drawable.ic_course),
            MenuItem(requireContext().getString(R.string.createGroup), R.drawable.ic_group),
            MenuItem(requireContext().getString(R.string.createStudent), R.drawable.ic_student, listOf(
                MenuItem(requireContext().getString(R.string.upload_xls), R.drawable.ic_xlsx),
                MenuItem(requireContext().getString(R.string.add_one), R.drawable.ic_student)
            )),
            MenuItem(requireContext().getString(R.string.createAttendance), R.drawable.ic_attendance, listOf(
                MenuItem(requireContext().getString(R.string.upload_xls), R.drawable.ic_xlsx),
                MenuItem(requireContext().getString(R.string.add_one_by_one), R.drawable.ic_attendance)
            ))
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllCourses(userId)
        observeNetwork()
        observeViewModel()

        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, true)

        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(R.color.theme_background)),
                    topBar = {
                        AttendanceTopAppBar(
                            title = stringResource(R.string.list_of_courses),
                            menus = menuItems,
                            onClicked = { parent, child ->
                                when (parent) {
                                    3 -> if (child == 0) filePicker(0)
                                    4 -> if (child == 0) filePicker(1)
                                }
                            }
                        )
                    },
                    contentWindowInsets = WindowInsets(0)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(colorResource(R.color.theme_background))
                            .padding(it)
                    ) {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            itemsIndexed(list) { _, item ->
                                CourseItem(item = item)
                            }
                        }
                    }
                }
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getAllCourses(userId)
        }
    }

    private fun filePicker(type: Int) {
        val intent = Intent(requireActivity(), FilePickerActivity::class.java)
        intent.putExtra(
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
        when (type) {
            0 -> studentFilePickerLauncher.launch(intent)
            1 -> attendanceFilePickerLauncher.launch(intent)
        }
    }

    private fun getPickedFileUri(data: Intent?): Uri? {
        val files = data?.getParcelableArrayListExtra<com.jaiselrahman.filepicker.model.MediaFile>(
            FilePickerActivity.MEDIA_FILES
        )
        return files?.firstOrNull()?.uri
    }

    private fun copyUriToTempFile(uri: Uri): String? {
        return try {
            val inputStream: InputStream? = requireContext().contentResolver.openInputStream(uri)
            val file = File(requireContext().cacheDir, DocumentsContract.getDocumentId(uri).split(":").last())
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

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it) {
                        is HomeState.Error -> {
                            hideProgress()
                            ErrorDialog.show(requireContext(), it.error).show()
                        }

                        HomeState.Init -> hideProgress()
                        HomeState.Loading -> showProgress()
                        is HomeState.Success -> {
                            hideProgress()
                            list = it.date
                        }
                    }
                }
            }
        }
    }

    private fun observeNetwork() {
        networkViewModel.networkState.observe(viewLifecycleOwner) { state ->
            if (isAdded && state == false) {
                InternetErrorDialog.show(requireContext()).show()
            }
        }
    }

    private fun hideProgress() {
        _binding?.apply {
            loading.visibility = View.GONE
            swipeRefresh.isRefreshing = false
        }
    }

    private fun showProgress() {
        binding.loading.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        ErrorDialog.dismiss()
        InternetErrorDialog.dismiss()
    }
}