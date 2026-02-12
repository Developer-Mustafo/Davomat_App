package uz.coder.davomatapp.data.network

import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import uz.coder.davomatapp.data.network.dto.AttendanceResponse
import uz.coder.davomatapp.data.network.dto.CourseDTO
import uz.coder.davomatapp.data.network.dto.CreateAttendanceRequest
import uz.coder.davomatapp.data.network.dto.CreateCourseRequest
import uz.coder.davomatapp.data.network.dto.CreateGroupRequest
import uz.coder.davomatapp.data.network.dto.CreateStudentRequest
import uz.coder.davomatapp.data.network.dto.GroupDTO
import uz.coder.davomatapp.data.network.dto.LoginRequest
import uz.coder.davomatapp.data.network.dto.LoginResponse
import uz.coder.davomatapp.data.network.dto.RegisterRequest
import uz.coder.davomatapp.data.network.dto.RegisterResponse
import uz.coder.davomatapp.data.network.dto.ResponseDTO
import uz.coder.davomatapp.data.network.dto.StudentCoursesDTO
import uz.coder.davomatapp.data.network.dto.StudentResponse
import uz.coder.davomatapp.data.network.dto.UserRequest
import uz.coder.davomatapp.data.network.dto.UserResponse

interface ApiService {
    /***----------------User----------------***/
    @POST("auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest): LoginResponse
    @POST("auth/register")
    suspend fun registerUser(@Body registerRequest: RegisterRequest): ResponseDTO<RegisterResponse>
    @DELETE("api/user/delete")
    suspend fun deleteUser(): ResponseDTO<Int>
    @GET("api/user/me")
    suspend fun getUser(): ResponseDTO<UserResponse>
    @GET("api/user/find-by-phone-number/{phoneNumber}")
    suspend fun getUserByPhoneNumber(@Path("phoneNumber") phoneNumber: String): ResponseDTO<UserResponse>
    @PUT("api/user/update")
    suspend fun updateUser(@Body userRequest: UserRequest): ResponseDTO<UserResponse>
    /***----------------User----------------***/

    /***-------------Student---------------***/
    @GET("api/student/seeCourses")
    suspend fun seeCourses(): ResponseDTO<List<StudentCoursesDTO>>
    @GET("api/student/findByGroupIdAndUserId")
    suspend fun findByGroupId(@Query("groupId") groupId: Long): ResponseDTO<StudentResponse>
    @POST("api/student/addStudent")
    suspend fun createStudent(@Body request: CreateStudentRequest): ResponseDTO<StudentResponse>
    @POST("api/student/upload-excel")
    @Multipart
    suspend fun uploadStudentExcel(@Part file: MultipartBody.Part): ResponseDTO<String>
    /***-------------Student---------------***/

    /***-------------Attendance---------------***/
    @GET("api/attendance/student/{studentId}")
    suspend fun attendanceList(@Path("studentId") studentId: Long): ResponseDTO<List<AttendanceResponse>>
    @POST("api/attendance/add")
    suspend fun createAttendance(@Body request: CreateAttendanceRequest): ResponseDTO<AttendanceResponse>
    @Multipart
    @POST("api/attendance/excel")
    suspend fun uploadAttendanceExcel(@Part file: MultipartBody.Part): ResponseDTO<String>
    /***-------------Attendance---------------***/

    /***-------------Course---------------***/
    @GET("api/course/getAllCourses")
    suspend fun getAllCourses(): ResponseDTO<List<CourseDTO>>

    @POST("api/course/create")
    suspend fun createCourse(@Body request: CreateCourseRequest): ResponseDTO<CourseDTO>
    /***-------------Course---------------***/

    /***-------------Group----------------***/
    @POST("api/group/addGroup")
    suspend fun createGroup(@Body request: CreateGroupRequest): ResponseDTO<GroupDTO>
    /***-------------Group----------------***/
}