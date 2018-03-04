package studymate.mstechnologies.com.studymateandroid.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.valdesekamdem.library.mdtoast.MDToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import studymate.mstechnologies.com.studymateandroid.Fragments.Login_Fragment;
import studymate.mstechnologies.com.studymateandroid.Models.Login;
import studymate.mstechnologies.com.studymateandroid.R;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIClientRetrofit;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIinterfaceRetrofit;
import studymate.mstechnologies.com.studymateandroid.Utils.Utils;

public class MainActivity extends AppCompatActivity {

  private static FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();

      // If savedinstnacestate is null then replace login fragment
      if (savedInstanceState == null) {
        fragmentManager
            .beginTransaction()
            .replace(R.id.frameContainer, new Login_Fragment(),
                Utils.Login_Fragment).commit();
      }

      // On close icon click finish activity
      findViewById(R.id.close_activity).setOnClickListener(
          new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
              finish();

            }
          });

    }

  // Replace Login Fragment with animation
  public void replaceLoginFragment() {
    fragmentManager
        .beginTransaction()
        .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
        .replace(R.id.frameContainer, new Login_Fragment(),
            Utils.Login_Fragment).commit();
  }

  @Override
  public void onBackPressed() {

    // Find the tag of signup and forgot password fragment
    Fragment SignUp_Fragment = fragmentManager
        .findFragmentByTag(Utils.SignUp_Fragment);
    Fragment ForgotPassword_Fragment = fragmentManager
        .findFragmentByTag(Utils.ForgotPassword_Fragment);

    // Check if both are null or not
    // If both are not null then replace login fragment else do backpressed
    // task

    if (SignUp_Fragment != null)
      replaceLoginFragment();
    else if (ForgotPassword_Fragment != null)
      replaceLoginFragment();
    else
      super.onBackPressed();
  }
}
