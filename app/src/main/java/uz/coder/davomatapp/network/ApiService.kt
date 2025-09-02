package uz.coder.davomatapp.network

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import uz.coder.davomatapp.network.dto.LoginResponse
import uz.coder.davomatapp.network.dto.RegisterRequest
import uz.coder.davomatapp.network.dto.RegisterResponse
import uz.coder.davomatapp.network.dto.ResponseDTO
import uz.coder.davomatapp.network.dto.UserRequest
import uz.coder.davomatapp.network.dto.UserResponse

interface ApiService {
    /***----------------User----------------***/
    @POST("/api/user/login")
    suspend fun loginUser(@Query("email") email:String, @Query("password") password:String): ResponseDTO<LoginResponse>
    @POST("/api/user/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): ResponseDTO<RegisterResponse>
    @DELETE("/api/user/delete/{id}")
    suspend fun deleteUser(@Path("id") id: Long): ResponseDTO<Int>
    @GET("/api/user/{id}")
    suspend fun getUser(@Path("id") id: Long): ResponseDTO<UserResponse>
    @GET("/api/user/phone/{phoneNumber}")
    suspend fun getUserByPhoneNumber(@Path("phoneNumber") phoneNumber: String): ResponseDTO<UserResponse>
    @PUT("/api/user/update")
    suspend fun updateUser(@Body userRequest: UserRequest): ResponseDTO<UserResponse>
    /***----------------User----------------***/
}