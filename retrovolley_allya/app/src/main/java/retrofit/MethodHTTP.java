package retrofit;

import com.example.retrovolley_allya.Request;
import com.example.retrovolley_allya.UserResponse;

import model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MethodHTTP {
    //GET all user
    @GET("volley/User_Registration.php")
    Call<UserResponse> getUser();

    //GET Specific using username and password
    @GET("volley/Login.php?")
    Call<UserResponse> login(@Query("email") String email, @Query("password") String password);

    //GET specific using id
    @GET("volley/User.php?")
    Call<UserResponse> getUserByID(@Query("id") int id);

    //submit user registration
    @POST("volley/User_Registration.php")
    Call<Request> sendUser(@Body User user);

    //update user
    @POST("volley/User.php?")
    Call<Request> updateUser(@Body User user);

    @DELETE("volley/User.php?")
    Call<Request> deleteUser(@Query("id") int id);
}
