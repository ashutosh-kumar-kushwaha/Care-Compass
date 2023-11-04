package me.ashutoshkk.carecompass.data.repository

import me.ashutoshkk.carecompass.common.SingleLiveEvent
import me.ashutoshkk.carecompass.data.remote.BlockchainAPI
import me.ashutoshkk.carecompass.common.Resource
import me.ashutoshkk.carecompass.domain.model.DefaultResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class AddRecordRepository @Inject constructor(private val blockchainAPI: BlockchainAPI){
    val addRecordResponse = SingleLiveEvent<Resource<DefaultResponse>>()

    suspend fun addRecord(images: MutableList<MultipartBody.Part>, requestBody: RequestBody) {
        addRecordResponse.postValue(Resource.Loading())
        try {
            val response = blockchainAPI.addRecord(images, requestBody)
            when (response.code()) {
                200 -> {
                        addRecordResponse.postValue(Resource.Success(200, DefaultResponse("Added", true)))
                }
                else -> addRecordResponse.postValue(Resource.Error(response.code(), "Something went wrong\nError code : ${response.code()}"))
            }
        } catch (e: Exception) {
            addRecordResponse.value = Resource.Error(-1, e.message)
            e.printStackTrace()
        }
    }
}