package studymate.mstechnologies.com.studymateandroid.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import studymate.mstechnologies.com.studymateandroid.Fragments.HomeFragment;
import studymate.mstechnologies.com.studymateandroid.Fragments.ProfileFragment;
import studymate.mstechnologies.com.studymateandroid.Fragments.SearchFragment;
import studymate.mstechnologies.com.studymateandroid.R;

public class HomeActivity extends AppCompatActivity {

  private BottomNavigationView mHomeNav;
  private FrameLayout mMainFrame;
  private HomeFragment homeFragment;
  private ProfileFragment profileFragment;
  private SearchFragment searchFragment;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    WidgetsInit();
    Listeners();

  }

  private void Listeners()
  {
    mHomeNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
      @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
          case R.id.nav_home_item:{setFragment(homeFragment); return true;}
          case R.id.nav_profile_item: { setFragment(profileFragment); return true;}
          case R.id.nav_search_item: { setFragment(searchFragment); return true;}
          default:return false;
        }
      }
    });
  }

  private void setFragment(Fragment fragment)
  {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.replace(R.id.home_main_frame,fragment).addToBackStack(fragment.getTag());
    fragmentTransaction.commit();
  }

  private void WidgetsInit()
  {
    homeFragment = new HomeFragment();
    profileFragment = new ProfileFragment();
    searchFragment = new SearchFragment();
    mMainFrame = (FrameLayout)findViewById(R.id.home_main_frame);
    mHomeNav = (BottomNavigationView)findViewById(R.id.home_nav);
    setFragment(homeFragment);

  }
}

