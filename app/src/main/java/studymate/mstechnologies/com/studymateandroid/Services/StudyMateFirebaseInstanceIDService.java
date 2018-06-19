package studymate.mstechnologies.com.studymateandroid.Services;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import java.util.prefs.Preferences;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xboxp on 5/31/2018.
 */

public class StudyMateFirebaseInstanceIDService extends FirebaseInstanceIdService
{
  private String token;
  private int id;
  private boolean isLogin;

  @Override
  public void onTokenRefresh()
  {
    token = FirebaseInstanceId.getInstance().getToken();

  }

  private void sendTokenToServer(String token,final int id)
  {


}}
