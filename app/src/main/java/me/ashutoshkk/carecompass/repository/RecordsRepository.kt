package me.ashutoshkk.carecompass.repository

import me.ashutoshkk.carecompass.SingleLiveEvent
import me.ashutoshkk.carecompass.api.BlockchainAPI
import me.ashutoshkk.carecompass.api.NetworkResult
import me.ashutoshkk.carecompass.models.GetRecordsResponse
import javax.inject.Inject

class RecordsRepository @Inject constructor(private val blockchainAPI: BlockchainAPI) {
    val records = SingleLiveEvent<NetworkResult<GetRecordsResponse>>()

    suspend fun getRecords(){
        records.postValue(NetworkResult.Loading())
        try {
            val response = blockchainAPI.getRecords()
            when (response.code()) {
                200 -> {
                    if(response.body()!=null){
                        records.postValue(NetworkResult.Success(200, response.body()!!))
                    }
                    else{
                        records.postValue(NetworkResult.Error(200, "Something went wrong\nError: Response is null"))
                    }
                }
                else -> records.postValue(NetworkResult.Error(response.code(), "Something went wrong\nError code : ${response.code()}"))
            }
        } catch (e: Exception) {
            records.value = NetworkResult.Error(-1, e.message)
        }
    }
}