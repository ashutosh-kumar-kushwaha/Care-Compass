package me.ashutoshkk.carecompass.repository

import me.ashutoshkk.carecompass.SingleLiveEvent
import me.ashutoshkk.carecompass.api.BlockchainAPI
import me.ashutoshkk.carecompass.api.NetworkResult
import me.ashutoshkk.carecompass.models.DefaultResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class AddRecordRepository @Inject constructor(private val blockchainAPI: BlockchainAPI){
    val addRecordResponse = SingleLiveEvent<NetworkResult<DefaultResponse>>()

    suspend fun addRecord(images: MutableList<MultipartBody.Part>, requestBody: RequestBody) {
        addRecordResponse.postValue(NetworkResult.Loading())
        try {
            val response = blockchainAPI.addRecord(images, requestBody)
            when (response.code()) {
                200 -> {
                        addRecordResponse.postValue(NetworkResult.Success(200, DefaultResponse("Added", true)))
                }
                else -> addRecordResponse.postValue(NetworkResult.Error(response.code(), "Something went wrong\nError code : ${response.code()}"))
            }
        } catch (e: Exception) {
            addRecordResponse.value = NetworkResult.Error(-1, e.message)
            e.printStackTrace()
        }
    }
}