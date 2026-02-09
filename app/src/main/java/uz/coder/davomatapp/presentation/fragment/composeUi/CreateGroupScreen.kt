package uz.coder.davomatapp.presentation.fragment.composeUi

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import uz.coder.davomatapp.presentation.ui.AttendanceTopAppBar
import uz.coder.davomatapp.presentation.ui.ErrorDialog
import uz.coder.davomatapp.presentation.ui.InternetErrorDialog
import uz.coder.davomatapp.presentation.viewModel.CreateGroupViewModel
import uz.coder.davomatapp.presentation.viewModel.NetworkViewModel
import uz.coder.davomatapp.presentation.viewModel.state.CreateGroupState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateGroupScreen(
    modifier: Modifier = Modifier,
    activity: FragmentActivity?,
    networkViewModel: NetworkViewModel,
    navigateBack: () -> Unit,
) {
    val viewModel = hiltViewModel<CreateGroupViewModel>()
    val context = LocalContext.current

    var groupName by remember { mutableStateOf("") }
    var selectedCourse by remember { mutableStateOf<Pair<Long, String>?>(null) }
    var isDropdownExpanded by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val courses = viewModel.courses.collectAsState(initial = emptyList())

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AttendanceTopAppBar(
                title = "Create Group",
                menus = emptyList(),
                onClicked = null
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Group Name
            OutlinedTextField(
                value = groupName,
                onValueChange = { groupName = it },
                label = { Text("Group Name") },
                singleLine = true,
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth()
            )

            // Course Dropdown
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = selectedCourse?.second ?: "Select Course",
                    onValueChange = {},
                    readOnly = true,
                    enabled = !isLoading,
                    trailingIcon = {
                        IconButton(onClick = { isDropdownExpanded = !isDropdownExpanded }) {
                            Icon(
                                imageVector = if (isDropdownExpanded) Icons.Default.ArrowDropUp
                                else Icons.Default.ArrowDropDown,
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                DropdownMenu(
                    expanded = isDropdownExpanded,
                    onDismissRequest = { isDropdownExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    courses.value.forEach { course ->
                        DropdownMenuItem(
                            text = { Text(course.second) },
                            onClick = {
                                selectedCourse = course
                                isDropdownExpanded = false
                            }
                        )
                    }
                }
            }

            // Create Button
            Button(
                onClick = {
                    selectedCourse?.let {
                        viewModel.createGroup(
                            title = groupName,
                            courseId = it.first
                        )
                    }
                },
                enabled = !isLoading && groupName.isNotBlank() && selectedCourse != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Create")
                }
            }
        }
    }

    // State Handling
    LaunchedEffect(viewModel.state) {
        viewModel.state.collect { state ->
            when (state) {
                CreateGroupState.Init -> isLoading = false
                CreateGroupState.Loading -> isLoading = true
                is CreateGroupState.Error -> {
                    isLoading = false
                    ErrorDialog.show(context, state.error).show()
                }
                CreateGroupState.Success -> {
                    isLoading = false
                    navigateBack()
                }
            }
        }
    }

    // Network
    DisposableEffect(Unit) {
        networkViewModel.networkState.observe(activity!!) { connected ->
            if (connected == false) {
                InternetErrorDialog.show(activity).show()
            }
        }
        onDispose { }
    }
}
