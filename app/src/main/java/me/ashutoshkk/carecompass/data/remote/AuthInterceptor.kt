package me.ashutoshkk.carecompass.data.remote

import android.util.Log
import me.ashutoshkk.carecompass.data.datastore.DataStoreManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val dataStoreManager: DataStoreManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            dataStoreManager.getLogInInfo().first()
        }.accessToken

        val request = chain.request()
        val shouldAddToken = request.headers["isAuthorized"]=="true"
        val requestBuilder = request.newBuilder().removeHeader("isAuthorized")
        if(shouldAddToken){
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        Log.d("Ashu", "Access Token: $token")

        return chain.proceed(requestBuilder.build())
    }
}