package studymate.mstechnologies.com.studymateandroid.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.valdesekamdem.library.mdtoast.MDToast;
import studymate.mstechnologies.com.studymateandroid.Fragments.Login_Fragment;
import studymate.mstechnologies.com.studymateandroid.R;
import studymate.mstechnologies.com.studymateandroid.Utils.Utils;

public class MainActivity extends AppCompatActivity {

  private static FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
       try {
         SharedPreferences prefs = this.getSharedPreferences(Utils.Login_Preferences, Context.MODE_PRIVATE);
         String loginEmail = prefs.getString("Email", "");
         int loginId = prefs.getInt("Id", -1);
         int loginType = prefs.getInt("Type", 0);
         int firstLogin = prefs.getInt("First_Login", -1);
         //IF USER ALREADY HAS COMPLETED HIS PROFILE
         int completedProfile = prefs.getInt("Profile_Completed", 0);
         if (completedProfile == 1) {
           Intent intent = new Intent(MainActivity.this, HomeActivity.class);
           intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
           startActivity(intent);
         }
         if (loginEmail.length() > 0 && loginId != -1) {
           if (firstLogin == 1) {
             Intent goToProfileEdit = new Intent(MainActivity.this, CompleteProfileActivity.class);
             goToProfileEdit.putExtra("Id", loginId);
             goToProfileEdit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
             startActivity(goToProfileEdit);
             MDToast.makeText(getApplicationContext(), getResources().getString(R.string.CompleteProfile),
                 MDToast.LENGTH_LONG, MDToast.TYPE_INFO).show();
           }
           else
           {
             Intent homeIntent = new Intent(MainActivity.this,HomeActivity.class);
             homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
             homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
             startActivity(homeIntent);
           }
         } else {
           //SHOW PROMPT FOR LOGIN DETAILS
         }
       }
       catch (Exception e)
       {

       }

      // If savedinstnacestate is null then replace login fragment
      if (savedInstanceState == null) {
        fragmentManager
            .beginTransaction()
            .replace(R.id.frameContainer, new Login_Fragment(),
                Utils.Login_Fragment).commit();
      }


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
