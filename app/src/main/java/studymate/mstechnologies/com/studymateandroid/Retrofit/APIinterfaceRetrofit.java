package studymate.mstechnologies.com.studymateandroid.Retrofit;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import studymate.mstechnologies.com.studymateandroid.Models.Login;
import studymate.mstechnologies.com.studymateandroid.Models.Register;

/**
 * Created by xboxp on 3/4/2018.
 */

public interface APIinterfaceRetrofit
{

  @POST("loginUser")
  Call<Login> LoginUser(@Body Login login);

  @POST("addNewUser")
  Call<String> Register(@Body Register register);

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
