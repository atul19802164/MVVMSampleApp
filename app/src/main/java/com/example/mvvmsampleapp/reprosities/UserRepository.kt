package com.example.mvvmsampleapp.reprosities
import com.example.mvvmsampleapp.data.network.MyApi
import com.example.mvvmsampleapp.network.SafeApiRequest
import com.example.mvvmsampleapp.network.responses.AuthResponse
import com.example.mvvmsampleapp.data.db.AppDatabase
import com.example.mvvmsampleapp.data.db.entities.User

class UserRepository(var api:MyApi,var db: AppDatabase
):SafeApiRequest(){
   suspend fun login(email:String,password:String):AuthResponse{
      return apiRequest {api.userLogin(email,password)  }
    }
    suspend fun signup(name:String,email:String,password:String):AuthResponse{
        return apiRequest {api.userSignup(name,email,password)  }
    }
    suspend fun saveUser(user:User){
        db.getUserDao().upsert(user)
    }
fun getUser()=db.getUserDao().getuser()
}