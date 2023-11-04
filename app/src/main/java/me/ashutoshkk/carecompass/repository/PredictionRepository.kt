package me.ashutoshkk.carecompass.repository

import me.ashutoshkk.carecompass.SingleLiveEvent
import me.ashutoshkk.carecompass.api.MLApis
import me.ashutoshkk.carecompass.api.NetworkResult
import me.ashutoshkk.carecompass.models.PredictRequest
import me.ashutoshkk.carecompass.models.PredictResponse
import javax.inject.Inject

class PredictionRepository @Inject constructor(private val mlApis: MLApis) {

    val predictResponse = SingleLiveEvent<NetworkResult<PredictResponse>>()

    suspend fun predict(predictRequest: PredictRequest){
        predictResponse.value = NetworkResult.Loading()
        try {
            val response = mlApis.predict(predictRequest)
            when (response.code()) {
                200 -> {
                    if(response.body()!=null){
                        predictResponse.postValue(NetworkResult.Success(200, response.body()!!))
                    }
                    else{
                        predictResponse.postValue(NetworkResult.Error(200, "Something went wrong\nError: Response is null"))
                    }
                }
                else -> predictResponse.postValue(NetworkResult.Error(response.code(), "Something went wrong\nError code : ${response.code()}"))
            }
        } catch (e: Exception) {
            predictResponse.value = NetworkResult.Error(-1, e.message)
        }
    }
}