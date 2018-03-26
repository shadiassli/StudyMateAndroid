package studymate.mstechnologies.com.studymateandroid.Retrofit;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import studymate.mstechnologies.com.studymateandroid.Models.CompleteProfile;
import studymate.mstechnologies.com.studymateandroid.Models.Login;
import studymate.mstechnologies.com.studymateandroid.Models.Major;
import studymate.mstechnologies.com.studymateandroid.Models.Register;
import studymate.mstechnologies.com.studymateandroid.Models.User;

/**
 * Created by xboxp on 3/4/2018.
 */

public interface APIinterfaceRetrofit
{
  // Call to check the sign-in credentials in the server
  @POST("loginUser")
  Call<Login> LoginUser(@Body Login login);
  // Call to register a new user in the backend server
  @POST("addNewUser")
  Call<String> Register(@Body Register register);
  // Call to get a certain user from the server by his/her Id
  @GET("getUserById")
  Call<User> getUser(@Path("id") int id);

  // Call to update the missing user data in EditProfile activity
  @POST("completeProfile")
  Call<String> updateProfile(@Body CompleteProfile cp);
  // Call to check the connection to our remote Server
  @GET("checkConnection")
  Call<String> checkConnection();

  @GET("getDepartments")
  Call<ArrayList<String>> getDepartments();

  @GET("getMajorsByDepartment/{department}")
  Call<ArrayList<String>> getMajors(@Path("department") String selectedDep);
}
