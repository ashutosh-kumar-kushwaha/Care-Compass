package me.ashutoshkk.carecompass.data.repository

import me.ashutoshkk.carecompass.common.SingleLiveEvent
import me.ashutoshkk.carecompass.data.remote.MLApis
import me.ashutoshkk.carecompass.common.Resource
import me.ashutoshkk.carecompass.domain.model.PredictRequest
import me.ashutoshkk.carecompass.domain.model.PredictResponse
import javax.inject.Inject

class PredictionRepository @Inject constructor(private val mlApis: MLApis) {

    val predictResponse = SingleLiveEvent<Resource<PredictResponse>>()

    suspend fun predict(predictRequest: PredictRequest){
        predictResponse.value = Resource.Loading()
        try {
            val response = mlApis.predict(predictRequest)
            when (response.code()) {
                200 -> {
                    if(response.body()!=null){
                        predictResponse.postValue(Resource.Success(200, response.body()!!))
                    }
                    else{
                        predictResponse.postValue(Resource.Error(200, "Something went wrong\nError: Response is null"))
                    }
                }
                else -> predictResponse.postValue(Resource.Error(response.code(), "Something went wrong\nError code : ${response.code()}"))
            }
        } catch (e: Exception) {
            predictResponse.value = Resource.Error(-1, e.message)
        }
    }
}