package uz.coder.davomatapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import uz.coder.davomatapp.R
import uz.coder.davomatapp.domain.model.Group
import uz.coder.davomatapp.presentation.ui.AttendanceTopAppBar
import uz.coder.davomatapp.presentation.ui.GroupItem

@AndroidEntryPoint
class HomeStudentInfo : Fragment() {
    private val navArgs by navArgs<HomeStudentInfoArgs>()
    private val gson = Gson()
    private lateinit var composeView: ComposeView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            fitsSystemWindows = false
        }.also {
            composeView = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, true)
        val type = object : TypeToken<List<Group>>(){}.type
        val list = gson.fromJson<List<Group>>(navArgs.text, type)
        println(list)
        composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    AttendanceTopAppBar(title = stringResource(R.string.groups))
                }, contentWindowInsets = WindowInsets(0)) {
                    HomeStudentInfoView(Modifier
                        .fillMaxSize(), paddingValues = it, list)
                }
            }
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }

        }
        val dispatcher = requireActivity().onBackPressedDispatcher
        dispatcher.addCallback(viewLifecycleOwner, callback)
    }

    @Composable
    fun HomeStudentInfoView(
        modifier: Modifier = Modifier,
        paddingValues: PaddingValues,
        list: List<Group>
    ) {
        Column(modifier.fillMaxSize().background(colorResource(R.color.theme_background)).padding(paddingValues)) {
            LazyColumn(modifier.fillMaxSize()) {
                itemsIndexed(list){_,item->
                    GroupItem(item = item){
                        findNavController().navigate(HomeStudentInfoDirections.actionHomeStudentInfoToAttendance(it.id))
                    }
                }
            }
        }
    }
}