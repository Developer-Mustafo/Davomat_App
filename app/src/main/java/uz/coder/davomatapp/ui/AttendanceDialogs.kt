@file:Suppress("DEPRECATION")
package uz.coder.davomatapp.ui

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.ErrorDialogBinding
import uz.coder.davomatapp.databinding.InfoDialogBinding
import uz.coder.davomatapp.databinding.NoInternetDialogBinding
import uz.coder.davomatapp.databinding.VerifiedDialogBinding
import uz.coder.davomatapp.todo.isConnected

fun errorDialog(context: Context, message: String): AlertDialog{
    val binding = ErrorDialogBinding.inflate(LayoutInflater.from(context), null, false)
    val alertDialog = AlertDialog.Builder(context)
        .create()
    alertDialog.setView(binding.root)
    alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    binding.ok.setOnClickListener {
        alertDialog.dismiss()
    }
    binding.title.text = context.getString(R.string.error)
    binding.text.text = message
    return alertDialog
}
fun internetErrorDialog(context: Context, onPositive: (()->Unit)?=null): AlertDialog{
    val binding = NoInternetDialogBinding.inflate(LayoutInflater.from(context), null, false)
    val alertDialog = AlertDialog.Builder(context)
        .create()
    alertDialog.setView(binding.root)
    alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    binding.reload.setOnClickListener {
        if (context.isConnected()){
            alertDialog.dismiss()
        }
        onPositive?.invoke()
    }
    return alertDialog
}
fun infoDialog(context: Context, message: String, onPositive: (DialogInterface)->Unit): AlertDialog{
    val binding = InfoDialogBinding.inflate(LayoutInflater.from(context), null, false)
    val alertDialog = AlertDialog.Builder(context)
        .create()
    alertDialog.setView(binding.root)
    alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    binding.ok.setOnClickListener {
        onPositive(alertDialog)
    }
    binding.cancel.setOnClickListener {
        alertDialog.dismiss()
    }
    binding.text.text = message
    return alertDialog
}
fun verifiedDialog(context: Context, onPositive: (DialogInterface)->Unit): AlertDialog{
    val binding = VerifiedDialogBinding.inflate(LayoutInflater.from(context), null, false)
    val alertDialog = AlertDialog.Builder(context)
        .create()
    alertDialog.setView(binding.root)
    alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    binding.ok.setOnClickListener {
        onPositive(alertDialog)
    }
    return alertDialog
}