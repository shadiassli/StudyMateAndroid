package studymate.mstechnologies.com.studymateandroid.Retrofit;

import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by xboxp on 3/4/2018.
 */

public class APIClientRetrofit
{
  protected static Retrofit retrofit = null ;

  public static Retrofit getClient()
  {
    if(retrofit==null)
    {
      retrofit = new Retrofit.Builder().baseUrl(BaseUrl.BASE_URL).addConverterFactory(
          GsonConverterFactory.create()).build();
    }
    return retrofit ;
  }
}
