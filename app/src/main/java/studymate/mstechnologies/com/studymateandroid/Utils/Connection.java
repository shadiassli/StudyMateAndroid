package studymate.mstechnologies.com.studymateandroid.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIClientRetrofit;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIinterfaceRetrofit;

/**
 * Created by xboxp on 3/15/2018.
 */

public class Connection
{
  static boolean connected = false;
  public static Boolean isConnected()
  {

    Retrofit retrofit = APIClientRetrofit.getClient();
    APIinterfaceRetrofit CheckConnection = retrofit.create(APIinterfaceRetrofit.class);
    Call<String> _checkConnection = CheckConnection.checkConnection();
    _checkConnection.enqueue(new Callback<String>() {
      @Override public void onResponse(Call<String> call, Response<String> response) {
        if (response.code()==200 || response.code()==204)
        {
          if (response.body()=="Connected")
          {
            connected = true;
          }
          else
            connected=false;
        }
      }

      @Override public void onFailure(Call<String> call, Throwable t) {
           connected = false;
      }
    });
    return connected;
  }
}
