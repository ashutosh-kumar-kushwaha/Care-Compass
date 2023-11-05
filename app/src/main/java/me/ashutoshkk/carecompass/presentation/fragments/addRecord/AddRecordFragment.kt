package me.ashutoshkk.carecompass.presentation.fragments.addRecord

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import me.ashutoshkk.carecompass.presentation.viewModels.AddRecordViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import me.ashutoshkk.carecompass.common.Resource
import me.ashutoshkk.carecompass.common.URIPathHelper
import me.ashutoshkk.carecompass.databinding.FragmentAddRecordBinding
import me.ashutoshkk.carecompass.domain.model.AddRecordRequest
import me.ashutoshkk.carecompass.presentation.adapters.ImageAdapter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class AddRecordFragment : Fragment(){

    private var _binding: FragmentAddRecordBinding? = null
    private val binding : FragmentAddRecordBinding get() = _binding!!

    private val imageAdapter = ImageAdapter()
    private val imageList = mutableListOf<Uri>()

    private val addRecordViewModel by viewModels<AddRecordViewModel>()


    private var startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val imageUri = result.data?.data
            imageList.add(imageUri!!)
            imageAdapter.submitList(imageList + listOf())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddRecordBinding.inflate(inflater, container, false)

        binding.uploadBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            startForResult.launch(intent)
        }

        binding.submitBtn.setOnClickListener {
            upload()
        }



        binding.imagesRecyclerView.adapter = imageAdapter
        binding.imagesRecyclerView.layoutManager = GridLayoutManager(requireContext(), 5)

        return binding.root
    }

    fun upload(){
        val uriPathHelper = URIPathHelper()
        val images = mutableListOf<MultipartBody.Part>()
        imageList.forEach { uri ->
            val path = uriPathHelper.getPath(requireContext(), uri)
            val file = File(path!!)
            val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart = MultipartBody.Part.createFormData("images", file.name, requestBody)
            images.add(imageMultipart)
        }
        val requestBody = Gson().toJson(AddRecordRequest(addRecordViewModel.description.value!!, addRecordViewModel.from.value!!, addRecordViewModel.address.value!!, addRecordViewModel.clinicName.value!!, addRecordViewModel.symptoms.value!!, addRecordViewModel.to.value!!))
//        val reviewMsgRequest = reviewViewModel.message.value!!.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val jsonRequest = requestBody.toString().toRequestBody("text/plain".toMediaTypeOrNull())
//        val reviewRequest = requestBody.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        addRecordViewModel.addRecord(images, jsonRequest)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        addRecordViewModel.response.observe(viewLifecycleOwner){
            when(it){
                is Resource.Success -> {
                    Toast.makeText(requireContext(),"Added", Toast.LENGTH_SHORT).show()
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(),"Added", Toast.LENGTH_SHORT).show()
                }
                is Resource.Loading -> {

                }
            }
        }
    }

}