package uz.coder.davomatapp.presentation.fragment.composeUi

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import uz.coder.davomatapp.R
import uz.coder.davomatapp.presentation.ui.AttendanceTopAppBar
import uz.coder.davomatapp.presentation.ui.ErrorDialog
import uz.coder.davomatapp.presentation.ui.InternetErrorDialog
import uz.coder.davomatapp.presentation.viewModel.CreateCourseViewModel
import uz.coder.davomatapp.presentation.viewModel.NetworkViewModel
import uz.coder.davomatapp.presentation.viewModel.state.CreateCourseState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCourseScreen(
    modifier: Modifier = Modifier,
    controller: NavHostController,
    activity: FragmentActivity?,
    networkViewModel: NetworkViewModel
) {
    val viewModel = hiltViewModel<CreateCourseViewModel>()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(R.color.theme_background)),
        topBar = {
            AttendanceTopAppBar(
                title = stringResource(R.string.createCourse),
                menus = emptyList(),
                onClicked = { _, _ -> }
            )
        },
        contentWindowInsets = WindowInsets(0)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.theme_background))
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(R.string.course_title)) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                singleLine = true
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(stringResource(R.string.course_description)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp),
                enabled = !isLoading,
                maxLines = 5
            )

            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        viewModel.createCourse(
                            title = title,
                            description = description.ifBlank { null }
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading && title.isNotBlank()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(stringResource(R.string.create))
                }
            }
        }
    }

    LaunchedEffect(viewModel.state) {
        viewModel.state.collect { state ->
            when (state) {
                CreateCourseState.Init -> {
                    isLoading = false
                }
                CreateCourseState.Loading -> {
                    isLoading = true
                }
                is CreateCourseState.Error -> {
                    isLoading = false
                    ErrorDialog.show(context, state.error).show()
                }
                CreateCourseState.Success -> {
                    isLoading = false
                    controller.popBackStack()
                }
            }
        }
    }

    DisposableEffect(lifecycleOwner) {
        observeNetwork(networkViewModel, activity!!, lifecycleOwner)
        onDispose { }
    }

    BackHandler {
        controller.popBackStack()
    }
}

private fun observeNetwork(
    networkViewModel: NetworkViewModel,
    context: FragmentActivity,
    viewLifecycleOwner: LifecycleOwner
) {
    networkViewModel.networkState.observe(viewLifecycleOwner) { state ->
        state?.let {
            if (!it) {
                InternetErrorDialog.show(context).show()
            }
        }
    }
}