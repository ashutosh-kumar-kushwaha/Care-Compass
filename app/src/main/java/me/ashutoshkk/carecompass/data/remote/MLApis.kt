package me.ashutoshkk.carecompass.data.remote

import me.ashutoshkk.carecompass.domain.model.PredictRequest
import me.ashutoshkk.carecompass.domain.model.PredictResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface MLApis {
    @Headers("isAuthorized: false")
    @POST("predict")
    suspend fun predict(@Body predictRequest: PredictRequest) : Response<PredictResponse>
}