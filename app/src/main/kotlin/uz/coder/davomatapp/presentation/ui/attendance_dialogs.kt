@file:Suppress("DEPRECATION")

package uz.coder.davomatapp.presentation.ui

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.*

object ErrorDialog {
    private var dialog: Dialog? = null

    fun show(context: Context, message: String): Dialog {
        val binding = ErrorDialogBinding.inflate(LayoutInflater.from(context), null, false)
        dialog = Dialog(context).apply {
            setContentView(binding.root)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            setCancelable(false)
        }
        binding.ok.setOnClickListener { dismiss() }
        binding.title.text = context.getString(R.string.error)
        binding.text.text = message
        dialog?.show()
        return dialog!!
    }

    fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }
}

object InternetErrorDialog {
    private var dialog: Dialog? = null

    fun show(context: Context): Dialog {
        val binding = NoInternetDialogBinding.inflate(LayoutInflater.from(context), null, false)
        dialog = Dialog(context).apply {
            setContentView(binding.root)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setCancelable(false)
            window?.setGravity(Gravity.CENTER)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
        binding.reload.setOnClickListener { dismiss() }
        dialog?.show()
        return dialog!!
    }

    fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }
}

object InfoDialog {
    private var dialog: Dialog? = null

    fun show(context: Context, message: String, onPositive: () -> Unit): Dialog {
        val binding = InfoDialogBinding.inflate(LayoutInflater.from(context), null, false)
        dialog = Dialog(context).apply {
            setCancelable(false)
            setContentView(binding.root)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
        }
        binding.ok.setOnClickListener {
            dismiss()
            onPositive()
        }
        binding.alright.setOnClickListener { dismiss() }
        binding.text.text = message
        dialog?.show()
        return dialog!!
    }

    fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }
}

object VerifiedDialog {
    private var dialog: Dialog? = null

    fun show(context: Context, onPositive: ((DialogInterface) -> Unit)? = null): Dialog {
        val binding = VerifiedDialogBinding.inflate(LayoutInflater.from(context), null, false)
        dialog = Dialog(context).apply {
            setContentView(binding.root)
            window?.setBackgroundDrawableResource(android.R.color.transparent)
            setCancelable(false)
        }
        binding.ok.setOnClickListener { onPositive?.invoke(dialog!!)?:dialog?.dismiss() }
        dialog?.show()
        return dialog!!
    }

    fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }
}
