package studymate.mstechnologies.com.studymateandroid.Retrofit;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import studymate.mstechnologies.com.studymateandroid.Models.Answer;
import studymate.mstechnologies.com.studymateandroid.Models.CompleteProfile;
import studymate.mstechnologies.com.studymateandroid.Models.Login;
import studymate.mstechnologies.com.studymateandroid.Models.MainQuestions;
import studymate.mstechnologies.com.studymateandroid.Models.Major;
import studymate.mstechnologies.com.studymateandroid.Models.Questions;
import studymate.mstechnologies.com.studymateandroid.Models.Register;
import studymate.mstechnologies.com.studymateandroid.Models.User;
import studymate.mstechnologies.com.studymateandroid.Models.UserAnswer;
import studymate.mstechnologies.com.studymateandroid.Models.Users;

/**
 * Created by xboxp on 3/4/2018.
 */

public interface APIinterfaceRetrofit
{
  // Call to check the sign-in credentials in the server
  @POST("login")
  Call<Login> LoginUser(@Body Login login);
  // Call to register a new user in the backend server
  @POST("addNewUser")
  Call<String> Register(@Body Register register);
  // Call to get a certain user from the server by his/her Id
  @GET("getUserById/{id}")
  Call<Users> getUser(@Path("id") int id);

  // Call to update the missing user data in CompleteProfileActivity activity
  @POST("completeProfile")
  Call<String> updateProfile(@Body CompleteProfile cp);
  // Call to check the connection to our remote Server
  @GET("checkConnection")
  Call<String> checkConnection();

  @GET("getDepartments")
  Call<ArrayList<String>> getDepartments();

  @GET("getMajorsByDepartment/{department}")
  Call<ArrayList<String>> getMajors(@Path("department") String selectedDep);

  @POST("addQuestion")
  Call<String> addQuestion(@Body Questions q);

  @GET("getQuestionFeed/{id}"
      + "")
  Call<ArrayList<MainQuestions>> getQuestions(@Path("id") int id);

  @POST("addAnswer")
  Call<String> addAnswer(@Body Answer answer);

  @GET("getQuestion/{id}")
  Call<MainQuestions> getQuestion(@Path("id") int id);

  @GET("getAnswers/{id}")
  Call<ArrayList<UserAnswer>> getAnswers(@Path("id") int id);

  @GET("sendEmail/{Email}")
  Call<String> forgotPassword(@Path("Email") String Email);

  @POST("EditQuestion")
  Call<Void> updateQuestion(@Body Questions q);

  @GET("deleteQuestion/{id}")
  Call<Void> deleteQuestion(@Path("id") int id);

}
