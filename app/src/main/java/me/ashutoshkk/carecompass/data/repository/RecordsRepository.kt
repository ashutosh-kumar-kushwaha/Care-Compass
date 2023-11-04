package me.ashutoshkk.carecompass.data.repository

import me.ashutoshkk.carecompass.common.SingleLiveEvent
import me.ashutoshkk.carecompass.data.remote.BlockchainAPI
import me.ashutoshkk.carecompass.common.Resource
import me.ashutoshkk.carecompass.domain.model.GetRecordsResponse
import javax.inject.Inject

class RecordsRepository @Inject constructor(private val blockchainAPI: BlockchainAPI) {
    val records = SingleLiveEvent<Resource<GetRecordsResponse>>()

    suspend fun getRecords(){
        records.postValue(Resource.Loading())
        try {
            val response = blockchainAPI.getRecords()
            when (response.code()) {
                200 -> {
                    if(response.body()!=null){
                        records.postValue(Resource.Success(200, response.body()!!))
                    }
                    else{
                        records.postValue(Resource.Error(200, "Something went wrong\nError: Response is null"))
                    }
                }
                else -> records.postValue(Resource.Error(response.code(), "Something went wrong\nError code : ${response.code()}"))
            }
        } catch (e: Exception) {
            records.value = Resource.Error(-1, e.message)
        }
    }
}