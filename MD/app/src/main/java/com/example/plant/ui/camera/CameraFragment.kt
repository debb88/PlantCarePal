package com.example.plant.ui.camera

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.plant.MainActivity
import com.example.plant.R
import com.example.plant.ViewModelFactory
import com.example.plant.databinding.FragmentCameraBinding
import com.example.plant.getImageUri
import com.example.plant.pref.DataStoreViewModel
import com.example.plant.pref.UserPreference
import com.example.plant.pref.dataStore
import com.example.plant.reduceFileImage
import com.example.plant.ui.detail.DetailActivity
import com.example.plant.ui.network.ApiConfig
import com.example.plant.ui.network.response.DetectResponse
import com.example.plant.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CameraFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CameraFragment : Fragment() {
    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null
    private var resettingPreview = false
    private var isAnalyzing = false
    private lateinit var cameraViewModel: CameraViewModel

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Permission request granted", Toast.LENGTH_LONG).show()
                startCamera()
            } else {
                Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        if (allPermissionsGranted()) {
            currentImageUri = getImageUri(requireContext())
            launcherIntentCamera.launch(currentImageUri!!)
        } else {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.imagePreview.setImageURI(it)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        cameraViewModel = ViewModelProvider(this)[CameraViewModel::class.java]

        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.galleryButton.setOnClickListener {
            startGallery()
        }
        binding.cameraButton.setOnClickListener {
            startCamera()
        }
        binding.analyzeButton.setOnClickListener{
            val pref = UserPreference.getInstance(requireContext().applicationContext.dataStore)
            val datastoreViewModel = ViewModelProvider(this, ViewModelFactory(pref))[DataStoreViewModel::class.java]
            datastoreViewModel.getTokenKey().observe(viewLifecycleOwner){
                analyzeImage(it)
            }
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        if(resettingPreview){
            resetPreview()
            resettingPreview = false
        }
        showImage()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            currentImageUri = it.getParcelable("imageUri")
            showImage()
        }
    }

    private fun analyzeImage(token :String ) {
        if (isAnalyzing) return
        isAnalyzing = true
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
            Log.d("Image File", "showImage: ${imageFile.path}")
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                requestImageFile
            )

            showLoading(true)
            binding.analyzeButton.isEnabled = false

            val client = ApiConfig.getApiService().detectImage("Bearer $token", multipartBody)
            client.enqueue(object : Callback<DetectResponse> {
                override fun onResponse(
                    call: Call<DetectResponse>,
                    response: Response<DetectResponse>
                ) {
                    isAnalyzing = false
                    binding.analyzeButton.isEnabled = true
                    showLoading(false)

                    if(response.isSuccessful){
                        showLoading(false)
                        val responseBody = response.body()
                        if(responseBody != null){
                            Log.d(TAG, "${responseBody.data?.id}")
                            Log.d(TAG, "${responseBody.message}")
                            Log.d(TAG, "${responseBody.data?.percentage}")
                            resettingPreview = true
                            val intentDetail = Intent(context, DetailActivity::class.java)

                            intentDetail.putExtra(DetailActivity.ID, "${responseBody.data?.id}")

                            startActivity(intentDetail)
//                            resetPreview()
                        }
                    }else{
                        val responseBody = response.errorBody()?.string()
                        val errorMessage = JSONObject(responseBody).getString("message")
                        showLoading(false)
                        showToast(errorMessage)
                        Log.d(TAG, "${response.message()}")
                    }
                }

                override fun onFailure(call: Call<DetectResponse>, t: Throwable) {
                    isAnalyzing = false
                    binding.analyzeButton.isEnabled = true
                    showLoading(false)
                    val errorMessage = t.message ?: getString(R.string.failure_response)
                    Log.d(TAG, "onFailure: ${t.message}")
                    showToast(errorMessage)
                }

            })

        } ?: showToast(getString(R.string.empty_image_warning))
    }

    private fun resetPreview(){
        binding.imagePreview.setImageResource(R.drawable.ic_placeholder)
        currentImageUri = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isAnalyzing = false
        _binding = null
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
        const val TAG = "CameraFragment"
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}