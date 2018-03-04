package studymate.mstechnologies.com.studymateandroid.Retrofit;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import studymate.mstechnologies.com.studymateandroid.Models.Login;

/**
 * Created by xboxp on 3/4/2018.
 */

public interface APIinterfaceRetrofit
{
//  @POST("AddNewUsera") Call<String> addNewUser(@Body Register register);

  @POST("loginUser")
  Call<Login> LoginUser(@Body Login login);

 /* @GET("getProjectsDetails")
  Call<ArrayList<Project>> getProjects();

  @GET("getProject/{id}")
  Call<Project> isProjectOwner(@Path("id") int id);

  @GET("getProjectt/{id}")
  Call<Integer> hasProject(@Path("id") int id);


  @POST("updatepro")
  Call<Project> UpdateProject(@Body Project project);
  */

}
