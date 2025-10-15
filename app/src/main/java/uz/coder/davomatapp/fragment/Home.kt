package uz.coder.davomatapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import uz.coder.davomatapp.databinding.FragmentHomeBinding
import uz.coder.davomatapp.navigationCompose.AttendanceNavigation
import uz.coder.davomatapp.ui.ErrorDialog
import uz.coder.davomatapp.ui.InternetErrorDialog
import uz.coder.davomatapp.ui.VerifiedDialog
import uz.coder.davomatapp.viewModel.NetworkViewModel

class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val activityViewModels by activityViewModels<NetworkViewModel>()
        super.onViewCreated(view, savedInstanceState)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AttendanceNavigation(modifier = Modifier.fillMaxSize(), viewModel = activityViewModels)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        ErrorDialog.dismiss()
        InternetErrorDialog.dismiss()
        VerifiedDialog.dismiss()
    }
}