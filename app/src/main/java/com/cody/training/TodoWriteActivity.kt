package com.cody.training

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.cody.training.databinding.ActivityTodoWriteBinding
import com.cody.training.model.Todo
import com.cody.training.ui.todo.TodoWriteViewModel
import com.cody.training.ui.todo.TodoWriteViewModelFactory
import com.cody.training.utils.*
import com.google.gson.Gson
import org.apache.commons.io.IOUtils
import java.io.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TodoWriteActivity : AppCompatActivity() {

    private lateinit var viewModel: TodoWriteViewModel

    private lateinit var binding: ActivityTodoWriteBinding

    private var cameraOutputPath: String? = null

    private var cameraOutputUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val todo = try {
            Gson().fromJson(intent.getStringExtra("todo"), Todo::class.java)
        } catch (e: Exception) {
            null
        }

        val factory = TodoWriteViewModelFactory(todo)
        viewModel = ViewModelProvider(this, factory).get(TodoWriteViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_todo_write)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.buttonPhoto.setOnClickListener {
            if (Build.VERSION.SDK_INT < 23 ||
                hasPermissions(
                    this,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                val file = try {
                    createTempFile()
                } catch (e: IOException) {
                    null
                }
                if (file != null) {
                    showImagePicker(
                        this,
                        ActivityRequestCode.OPEN_CAMERA,
                        ActivityRequestCode.OPEN_GALLERY,
                        cameraOutputUri!!
                    )
                }
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    PermissionRequestCode.IMAGE_PICKER
                )
            }
        }

        binding.buttonSave.setOnClickListener {
            viewModel.saveTodo()
            finish()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PermissionRequestCode.IMAGE_PICKER) {
            if (grantResults.isEmpty() || grantResults[0] != 0) {
                showAlert(this, "Access to camera and gallery is required", null)
                return
            }

            val file = try {
                createTempFile()
            } catch (error: IOException) {
                null
            }

            val uri = cameraOutputUri

            if (file != null && uri != null) {
                showImagePicker(
                    this,
                    ActivityRequestCode.OPEN_CAMERA,
                    ActivityRequestCode.OPEN_GALLERY,
                    uri
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when {
            resultCode != Activity.RESULT_OK -> {
                super.onActivityResult(requestCode, resultCode, data)
            }

            requestCode == ActivityRequestCode.OPEN_CAMERA -> {
                viewModel.preview.value = cameraOutputPath
            }

            requestCode == ActivityRequestCode.OPEN_GALLERY -> {
                viewModel.preview.value = try {
                    val uri = data!!.data!!
                    val createCopyFile = createCopyFile(uri)
                    createCopyFile.absolutePath
                } catch (e: Exception) {
                    null
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createTempFile(): File {
        val format = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss").format(LocalDateTime.now())
        val externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        val tempFile = File.createTempFile("JPEG_" + format + '_', ".jpg", externalFilesDir)
        cameraOutputUri = FileProvider.getUriForFile(this, Path.FILE_PROVIDER, tempFile)
        cameraOutputPath = tempFile.absolutePath
        return tempFile
    }

    @Throws(IOException::class)
    private fun createCopyFile(uri: Uri): File {
        val openFileDescriptor = contentResolver.openFileDescriptor(uri, "r", null)!!
        val fileInputStream = FileInputStream(openFileDescriptor.fileDescriptor)
        val externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        val copyFile = File(externalFilesDir, contentResolver.getFileName(uri))
        IOUtils.copy(fileInputStream as InputStream, FileOutputStream(copyFile) as OutputStream)
        return copyFile
    }
}