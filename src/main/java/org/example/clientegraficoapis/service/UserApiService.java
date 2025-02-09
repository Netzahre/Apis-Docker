package org.example.clientegraficoapis.service;
import org.example.clientegraficoapis.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApiService {

    @POST("login")
    Call<User> signIn(@Body User user);

    @GET("user")
    Call<User> logIn(@Query("username") String user, @Query("password") String password);
}
