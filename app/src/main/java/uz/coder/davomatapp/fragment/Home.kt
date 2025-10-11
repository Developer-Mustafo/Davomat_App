package uz.coder.davomatapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.FragmentHomeBinding
import uz.coder.davomatapp.ui.AttendanceTopAppBar
import uz.coder.davomatapp.ui.InternetErrorDialog
import uz.coder.davomatapp.ui.MenuItem
import uz.coder.davomatapp.viewModel.NetworkViewModel
import java.lang.RuntimeException

class Home : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding?:throw RuntimeException("binding not init")
    private val networkViewModel by activityViewModels<NetworkViewModel>()
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
            )))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNetwork()
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, true)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Scaffold(modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.theme_background)), topBar = {
                    AttendanceTopAppBar(title = stringResource(R.string.list_of_courses), menus = menuItems, onClicked = { parent, child->
                        if (parent!=-1){
                            when(parent){
                                0->{
                                    findNavController().navigate(R.id.action_home_to_profile)
                                }
                                1->{
                                    //kurs
                                }
                                2->{
                                    //guruh
                                }
                                3->{
                                    //student
                                    when(child){
                                        0->{
                                            //student exel
                                        }
                                        1->{
                                            //student 1ta
                                        }
                                    }
                                }
                                4->{
                                    //davomat
                                    when(child){
                                        0->{
                                            //davomat exel
                                        }
                                        1->{
                                            //davomat 1ta
                                        }
                                    }
                                }
                                else->{}
                            }
                        }
                    })
                }, contentWindowInsets = WindowInsets(0)) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(R.color.theme_background))
                        .padding(it)) {

                    }
                }
            }
        }
    }

    private fun observeNetwork() {
        networkViewModel.networkState.observe(viewLifecycleOwner){state->
            if (isAdded){
                state?.let { it ->
                    if (!it){
                        InternetErrorDialog.show(requireContext()).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        InternetErrorDialog.dismiss()
    }
}