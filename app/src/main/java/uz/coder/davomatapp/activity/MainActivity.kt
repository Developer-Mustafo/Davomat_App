package uz.coder.davomatapp.activity

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import uz.coder.davomatapp.broadcast.NetworkBroadcast
import uz.coder.davomatapp.databinding.ActivityMainBinding
import uz.coder.davomatapp.viewModel.NetworkViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var networkBroadcast: NetworkBroadcast
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel:NetworkViewModel by viewModels()
    private val statusBarColor = 0xFFFF9800.toInt()
    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(SystemBarStyle.dark(statusBarColor))
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        networkBroadcast = NetworkBroadcast{
            viewModel.updateNetworkState(it)
        }
        val filter = IntentFilter().apply {
            addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        }
        registerReceiver(networkBroadcast, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkBroadcast)
    }
}