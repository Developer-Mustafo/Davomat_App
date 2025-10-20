package uz.coder.davomatapp.data.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import uz.coder.davomatapp.todo.isConnected
import java.util.Objects

@AndroidEntryPoint
class NetworkBroadcast(private val onNetworkState: ((Boolean)->Unit)? = null) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        Log.d(TAG, "onReceive: ")
        context?.let {
            when (Objects.requireNonNull<String?>(intent.action)) {
                "android.net.conn.CONNECTIVITY_CHANGE" -> {
                    if (context.isConnected()){
                        onNetworkState?.invoke(true)
                    }
                    else{
                        onNetworkState?.invoke(false)
                    }
                }
                Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                    if (context.isConnected()){
                        onNetworkState?.invoke(true)
                    }
                    else{
                        onNetworkState?.invoke(false)
                    }
                }
                else ->{}
            }
        }
    }
}

private const val TAG = "NetworkBroadcast"