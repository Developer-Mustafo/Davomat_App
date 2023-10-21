package uz.coder.davomatapp.presentation.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import uz.coder.davomatapp.BuildConfig
import uz.coder.davomatapp.R
import uz.coder.davomatapp.databinding.DialogBinding
import uz.coder.davomatapp.databinding.FragmentStudentBinding
import uz.coder.davomatapp.presentation.viewmodel.StudentParamViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class StudentFragment : Fragment() {
    private val args by navArgs<StudentFragmentArgs>()
    private var filePath:String? = null
    private var condition = 0
    private var _binding:FragmentStudentBinding? = null
    private lateinit var dialogBinding: DialogBinding
    private lateinit var viewModel: StudentParamViewModel
    private val binding:FragmentStudentBinding
        get() = _binding?:throw RuntimeException("binding not init")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogBinding = DialogBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[StudentParamViewModel::class.java]
        when(args.status){
            ADD->launchAdd()
            EDIT->launchEdit()
            else->throw RuntimeException("status not found ${args.status}")
        }
        viewModel.finish.observe(viewLifecycleOwner){
            findNavController().navigate(R.id.homeFragment)
        }
        with(binding){
            studentImg.setOnClickListener {
                showAlertDialog()
            }
           name.addTextChangedListener(object :TextWatcher{
               override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

               }

               override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                   viewModel.resetInputName()
               }

               override fun afterTextChanged(p0: Editable?) {

               }
           })
            surname.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.resetInputSurName()
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })
            phone.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.resetInputPhone()
                }

                override fun afterTextChanged(p0: Editable?) {

                }
            })
            age.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.resetInputAge()
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })
            viewModel.errorInputName.observe(viewLifecycleOwner){
                val massage = if (it){
                    getString(R.string.Name)
                }else{
                    null
                }
                name1.error = massage
            }
            viewModel.errorInputSurName.observe(viewLifecycleOwner){
                val massage = if (it){
                    getString(R.string.SurName)
                }else{
                    null
                }
                surname1.error = massage
            }
            viewModel.errorInputPhone.observe(viewLifecycleOwner){
                val massage = if (it){
                    getString(R.string.Phone)
                }else{
                    null
                }
                phone1.error = massage
            }
            viewModel.errorInputAge.observe(viewLifecycleOwner){
                val massage = if (it){
                    getString(R.string.Age)
                }else{
                    null
                }
                age1.error = massage
            }
        }

    }

    @SuppressLint("InflateParams")
    private fun showAlertDialog() {
        val dialog = AlertDialog.Builder(requireContext()).create()
        dialog.setView(dialogBinding.root)
        dialogBinding.camera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                takePicture()
            }else{
                permissionCameraAndFile()
            }
            takePicture()
            dialog.dismiss()
        }
        dialogBinding.galery.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            startActivityForResult(intent, 1)
            dialog.dismiss()
        }
        dialog.show()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val uri = data.data ?: return
            val inputStream = ContextWrapper(requireContext()).contentResolver?.openInputStream(uri)
            val times: String = SimpleDateFormat("yyMMdd_HHmmss", Locale.US).format(Date())
            val file = File(ContextWrapper(requireContext()).filesDir, times)
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            filePath = file.absolutePath
            Toast.makeText(requireContext(), filePath, Toast.LENGTH_SHORT).show()
        }
    }

    private fun permissionCameraAndFile() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) && ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                android.Manifest.permission.CAMERA
            )){
            Toast.makeText(requireContext(), "Dany qilishni bosdingiz", Toast.LENGTH_SHORT)
                .show()
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.create()
            alertDialog.setTitle("Ruxsat berasizmi ?")
            alertDialog.setPositiveButton("Ha") { _, _ ->
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.CAMERA
                    ),
                    2
                )
            }
            alertDialog.setNegativeButton("Yo'q") { dialog, _ ->
                dialog.dismiss()
            }
            alertDialog.show()
        }else{
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA
                ),
                1
            )
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Bu yerga 2 marta bossa tushadi", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", ContextWrapper(requireContext()).packageName, null)
                intent.data = uri
                startActivity(intent)

                condition = 1
            }
        } else {
            if (condition == 1) {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", ContextWrapper(requireContext()).packageName, null)
                intent.data = uri
                startActivity(intent)
            }
        }
    }
    private fun launchEdit() {
        binding.apply {
            Log.d("aaa", "launchEdit: ${args.id}")
            viewModel.getItemById(args.id)
            viewModel.student.observe(viewLifecycleOwner){
                val student = it
                Log.d("aaa", "121: ${it.id}")
                name.setText(student.name)
                surname.setText(student.surname)
                phone.setText(student.phone)
                age.setText(student.age.toString())
            }
            save.setOnClickListener {
                        val inputName = name.text.toString().trim()
                        val inputSurName = surname.text.toString().trim()
                        val inputPhone = phone.text.toString().trim()
                        val inputAge = age.text.toString().trim()
                        viewModel.editStudent(inputName,inputSurName,inputPhone,inputAge,filePath)
                }
        }
    }

    private fun launchAdd() {
        binding.apply {
                save.setOnClickListener {
                    val inputName = name.text.toString()
                    val inputSurName = surname.text.toString()
                    val inputPhone = phone.text.toString()
                    val inputAge = age.text.toString().trim()
                    viewModel.addStudent(inputName, inputSurName, inputPhone,inputAge,filePath)
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object{
        const val ADD = "add"
        const val EDIT = "edit"
    }
    private fun takePicture(){
        val file = try {
            createEmptyFile()

        } catch (e: Exception) {
            null
        }
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(ContextWrapper(requireContext()).packageManager)
        val uri = file?.let {
            FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID, it)
        }
        takeCaptureNewMethodResultLauncher.launch(uri)
    }
    private val takeCaptureNewMethodResultLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()){
        if (it){
            if (filePath != null){
                Toast.makeText(requireContext(), filePath.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
    @Throws(IOException::class)
    private fun createEmptyFile(): File {
        val time = SimpleDateFormat("yyMMdd_HHmmss", Locale.US).format(Date())
        val fileStorage = ContextWrapper(requireContext()).getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile(
            "Image_${time}",
            ".png",
            fileStorage
        )
        filePath = file.absolutePath
        return file
    }
}