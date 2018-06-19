package studymate.mstechnologies.com.studymateandroid.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.valdesekamdem.library.mdtoast.MDToast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import studymate.mstechnologies.com.studymateandroid.Activities.CompleteProfileActivity;
import studymate.mstechnologies.com.studymateandroid.Activities.HomeActivity;
import studymate.mstechnologies.com.studymateandroid.Models.Login;
import studymate.mstechnologies.com.studymateandroid.R;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIClientRetrofit;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIinterfaceRetrofit;
import studymate.mstechnologies.com.studymateandroid.Utils.CustomToast;
import studymate.mstechnologies.com.studymateandroid.Utils.Utils;

public class Login_Fragment extends Fragment implements OnClickListener {
	private static View view;

	private static EditText emailid, password;
	private static Button loginButton;
	private static TextView forgotPassword, signUp;
	private static CheckBox show_hide_password;
	private static LinearLayout loginLayout;
	private static Animation shakeAnimation;
	private static FragmentManager fragmentManager;
	private ProgressBar progressBar;

	public Login_Fragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.login_layout, container, false);
		initViews();
		setListeners();
		return view;
	}

	// Initiate Views
	private void initViews() {
		fragmentManager = getActivity().getSupportFragmentManager();

		progressBar=(ProgressBar)view.findViewById(R.id.log_in_spin_kit_pb);
		emailid = (EditText) view.findViewById(R.id.login_emailid);
		password = (EditText) view.findViewById(R.id.login_password);
		loginButton = (Button) view.findViewById(R.id.loginBtn);
		forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
		signUp = (TextView) view.findViewById(R.id.createAccount);
		show_hide_password = (CheckBox) view
				.findViewById(R.id.show_hide_password);
		loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);

		// Load ShakeAnimation
		shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
				R.anim.shake);

		// Setting text selector over textviews
	//	@SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
		try {
			//ColorStateList csl = ColorStateList.createFromXml(getResources(),
				//	xrp);

			//forgotPassword.setTextColor(csl);
		//	show_hide_password.setTextColor(csl);
		//	signUp.setTextColor(csl);
		} catch (Exception e) {
		}
	}

	// Set Listeners
	private void setListeners() {
		loginButton.setOnClickListener(this);
		forgotPassword.setOnClickListener(this);
		signUp.setOnClickListener(this);

		// Set check listener over checkbox for showing and hiding password
		show_hide_password
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton button,
							boolean isChecked) {

						// If it is checkec then show password else hide
						// password
						if (isChecked) {

							show_hide_password.setText(R.string.hide_pwd);// change
																			// checkbox
																			// text

							password.setInputType(InputType.TYPE_CLASS_TEXT);
							password.setTransformationMethod(HideReturnsTransformationMethod
									.getInstance());// show password
						} else {
							show_hide_password.setText(R.string.show_pwd);// change
																			// checkbox
																			// text

							password.setInputType(InputType.TYPE_CLASS_TEXT
									| InputType.TYPE_TEXT_VARIATION_PASSWORD);
							password.setTransformationMethod(PasswordTransformationMethod
									.getInstance());// hide password

						}

					}
				});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.loginBtn:
			checkValidation();
			break;

		case R.id.forgot_password:

			// Replace forgot password fragment with animation
			fragmentManager
					.beginTransaction()
					.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
					.replace(R.id.frameContainer,
							new ForgotPassword_Fragment(),
							Utils.ForgotPassword_Fragment).commit();
			break;
		case R.id.createAccount:

			// Replace signup frgament with animation
			fragmentManager
					.beginTransaction()
					.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
					.replace(R.id.frameContainer, new SignUp_Fragment(),
							Utils.SignUp_Fragment).commit();
			break;
		}

	}

	// Check Validation before login
	private void checkValidation() {
		// Get email id and password
		final String getEmailId = emailid.getText().toString();
		String getPassword = password.getText().toString();

		// Check patter for email id
		Pattern p = Pattern.compile(Utils.regEx);

		Matcher m = p.matcher(getEmailId);

		// Check for both field is empty or not
		if (getEmailId.equals("") || getEmailId.length() == 0
				|| getPassword.equals("") || getPassword.length() == 0) {
			loginLayout.startAnimation(shakeAnimation);
			new CustomToast().Show_Toast(getActivity(), view,
					getResources().getString(R.string.Enterbothcredentials));

		}
		// Check if email id is valid or not
		else if (!m.find())
			new CustomToast().Show_Toast(getActivity(), view,
					getResources().getString(R.string.InvalidEmail));
		// Else do login and do your stuff
		else
		{
			Login login = new Login(getEmailId,getPassword);

			FoldingCube fc = new FoldingCube();
			progressBar.setIndeterminateDrawable(fc);
			progressBar.setVisibility(View.VISIBLE);
			Retrofit retrofit = APIClientRetrofit.getClient();
			APIinterfaceRetrofit _login = retrofit.create(APIinterfaceRetrofit.class);
			Call<Login> loginCall = _login.LoginUser(login);
			loginCall.enqueue(new Callback<Login>() {
				@Override public void onResponse(Call<Login> call, Response<Login> response)
				{
					if(response.code()==200 || response.code()==204)
          {
          	progressBar.setVisibility(View.INVISIBLE);
            MDToast.makeText(getContext(),getResources().getString(R.string.HoldOn),MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show();
            SharedPreferences settings = getContext().getSharedPreferences(Utils.Login_Preferences, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("Email", getEmailId);
            editor.putInt("Type",response.body().getType());
					editor.putInt("Profile_Completed",0);
           editor.putInt("Id",response.body().getId());
           editor.putInt("First_Login",response.body().getFirstLogin());
           editor.commit();
           int firstLogin = response.body().getFirstLogin();
           if(firstLogin == 1)
           {
             Intent goToProfileEdit = new Intent(getActivity(), CompleteProfileActivity.class);
             goToProfileEdit.putExtra("Id",response.body().getId());
             goToProfileEdit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
             startActivity(goToProfileEdit);
             getActivity().finish();

           }
           else
					 {
						 Intent goToHomePage = new Intent(getActivity(), HomeActivity.class);
						 goToHomePage.putExtra("Id",response.body().getId());
						 goToHomePage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
						 startActivity(goToHomePage);
						 getActivity().finish();
					 }
        }
         else if (response.code()==404)
         {
         	progressBar.setVisibility(View.INVISIBLE);
           new CustomToast().Show_Toast(getActivity(),view,getResources().getString(R.string.NoSuchUser));
        }

				}

			@Override public void onFailure(Call<Login> call, Throwable t)
       {
       MDToast.makeText(getActivity(),getResources().getString(R.string.NetworkConnectionFailure),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
       progressBar.setVisibility(View.INVISIBLE);
				}
			});

		}

	}
}
