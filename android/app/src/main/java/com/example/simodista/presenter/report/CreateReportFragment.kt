package com.example.simodista.presenter.report

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.simodista.R
import com.example.simodista.databinding.FragmentCreateReportBinding
import com.example.simodista.model.ReportForm
import com.example.simodista.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class CreateReportFragment : Fragment() {
    lateinit var binding: FragmentCreateReportBinding
    lateinit var viewModel: CreateReportViewModel
    lateinit var storageReference: StorageReference
    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var firebaseAuth: FirebaseAuth

    companion object{
        private const val FILE_NAME = "photo.jpg"
        private const val REQUEST_CODE = 42
    }

    private lateinit var photoFile: File

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity?)?.supportActionBar?.title = "Report"
        return inflater.inflate(R.layout.fragment_create_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateReportBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(CreateReportViewModel::class.java)
        storageReference = FirebaseStorage.getInstance().reference
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnSubmit.isEnabled = false

        binding.fabUploadPhoto.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            photoFile = getPhotoFile(FILE_NAME)

            val fileProvider = FileProvider.getUriForFile(requireContext(), "com.example.android.FileProvider", photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

            if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), 101);
            }else{
                if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {

                    startActivityForResult(takePictureIntent, REQUEST_CODE)
                } else {
                    Toast.makeText(requireContext(), "Unable to open camera", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnSubmit.setOnClickListener {
            if(checkField()){
                binding.progressBar4.visibility = View.VISIBLE
                binding.backgroundDim.visibility = View.VISIBLE
                activity?.window?.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

                val image = storageReference.child("pictures/" + photoFile.name)
                val docRef = firebaseFirestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
                var user : User? = null

                docRef.get().addOnSuccessListener { documentSnapshot ->
                    user = documentSnapshot.toObject<User>()
                }

                image.putFile(Uri.fromFile(photoFile)).addOnSuccessListener {
                    image.downloadUrl.addOnSuccessListener{
                        createReport(user, it)
                    }
                }.removeOnFailureListener{
                    Toast.makeText(requireContext(), it.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.getImageBitmap().observe(viewLifecycleOwner,{
            binding.imageView.setImageBitmap(it)
        })

    }

    private fun checkField() : Boolean{
        if(binding.etDescription.text.isEmpty() ){
            binding.etDescription.error = "Please fill description form!"
            return false
        }

        if(!this::photoFile.isInitialized){
            Toast.makeText(requireContext(), "Please take picture first!", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun createReport(user: User?, uri: Uri) {
        firebaseFirestore.collection("reports").get().addOnSuccessListener{snap->
            val reportForm = ReportForm(
                    id = snap.size() + 1,
                    user = user,
                    image_uri = uri.toString(),
                    date = SimpleDateFormat("dd-MM-yyyy_HH:mm:ss", Locale.getDefault()).format(Date()),
                    status = false
            )

            val reportId = (System.currentTimeMillis()/1000).toString() + user?.email
            val document = firebaseFirestore.collection("reports").document(reportId)
            document.set(reportForm).addOnFailureListener {
                Toast.makeText(requireContext(), "Register Success", Toast.LENGTH_SHORT).show()
            }

            binding.progressBar4.visibility = View.VISIBLE
            activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            view?.findNavController()?.navigate(R.id.action_createReportFragment2_to_userHomeFragment)
        }
    }

    private fun getPhotoFile(fileName: String): File {
        val storageDirectory = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d("Content URI", Uri.fromFile(photoFile).toString())
            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
            viewModel.setImageBitmap(takenImage)
            binding.btnSubmit.isEnabled = true
        } else {
            super.onActivityResult(requestCode, resultCode, data)
            Toast.makeText(requireContext(), "Please take a picture before submit report form", Toast.LENGTH_SHORT).show()
        }
    }


}