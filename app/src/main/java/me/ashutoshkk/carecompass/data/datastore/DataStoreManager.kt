package me.ashutoshkk.carecompass.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import me.ashutoshkk.carecompass.domain.model.LogInInfo
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore("dataStore")

class DataStoreManager(private val context : Context) {

    companion object PreferenceKey{
        val accessToken = stringPreferencesKey("accessToken")
        val refreshToken = stringPreferencesKey("refreshToken")
        val logInState = booleanPreferencesKey("logInState")
        val firstName = stringPreferencesKey("firstName")
        val lastName = stringPreferencesKey("lastName")
        val role = stringPreferencesKey("role")
        val email = stringPreferencesKey("email")
        val userId = stringPreferencesKey("userId")
    }

    suspend fun storeLogInInfo(logInInfo: LogInInfo){
        context.dataStore.edit {
            it[accessToken] = logInInfo.accessToken!!
            it[refreshToken] = logInInfo.refreshToken!!
            it[logInState] = logInInfo.logInState
            it[firstName] = logInInfo.firstName!!
            it[lastName] = logInInfo.lastName!!
            it[role] = logInInfo.role!!
            it[email] = logInInfo.email!!
            it[userId] = logInInfo.userId!!
        }
    }

    suspend fun getLogInInfo() = context.dataStore.data.map {
        LogInInfo(
            accessToken = it[accessToken]?:"",
            refreshToken = it[refreshToken]?:"",
            logInState = it[logInState]?:false,
            firstName = it[firstName]?:"",
            lastName = it[lastName]?:"",
            role = it[role]?:"",
            email = it[email]?:"",
            userId = it[userId]
        )
    }

    suspend fun getAccessTokenFlow() = context.dataStore.data.map {
        it[accessToken]?:""
    }

    suspend fun getRefreshTokenFlow() = context.dataStore.data.map {
        it[refreshToken]?:""
    }


    suspend fun getAccessToken() : String {
        var token : String? = null
        return runBlocking {
            val flow = getAccessTokenFlow()
            flow.collect{
                token = it
            }
            token.toString()
        }
    }

    suspend fun getRefreshToken() : String {
        var token : String? = null
        return runBlocking {
            val flow = getRefreshTokenFlow()
            flow.collect{
                token = it
            }
            token.toString()
        }
    }

    suspend fun saveAccessToken(aToken : String){
        context.dataStore.edit {
            it[accessToken] = aToken
        }
    }

    suspend fun saveEmail(e: String){
        context.dataStore.edit {
            it[email] = e
        }
    }

    suspend fun deleteAccessToken(){
        context.dataStore.edit {
            it[accessToken] = ""
        }
    }

    suspend fun deleteLogInInfo(){
        storeLogInInfo(LogInInfo("", "", false, "", "", "", "", ""))
    }
}