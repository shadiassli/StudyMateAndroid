package studymate.mstechnologies.com.studymateandroid.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.valdesekamdem.library.mdtoast.MDToast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import studymate.mstechnologies.com.studymateandroid.Activities.EditProfile;
import studymate.mstechnologies.com.studymateandroid.Activities.MainActivity;
import studymate.mstechnologies.com.studymateandroid.Models.Register;
import studymate.mstechnologies.com.studymateandroid.R;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIClientRetrofit;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIinterfaceRetrofit;
import studymate.mstechnologies.com.studymateandroid.Utils.CustomToast;
import studymate.mstechnologies.com.studymateandroid.Utils.Utils;

public class SignUp_Fragment extends Fragment implements OnClickListener {
	private static View view;
	private static EditText fullName, emailId, mobileNumber, birthdate,
			password, confirmPassword;
	private static TextView login;
	private static Button signUpButton;
	private static CheckBox terms_conditions;

	public SignUp_Fragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.signup_layout, container, false);
		initViews();
		setListeners();
		return view;
	}

	// Initialize all views
	private void initViews() {
		fullName = (EditText) view.findViewById(R.id.fullName);
		emailId = (EditText) view.findViewById(R.id.userEmailId);
		mobileNumber = (EditText) view.findViewById(R.id.mobileNumber);
		birthdate = (EditText) view.findViewById(R.id.birthdate);
		password = (EditText) view.findViewById(R.id.password);
		confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
		signUpButton = (Button) view.findViewById(R.id.signUpBtn);
		login = (TextView) view.findViewById(R.id.already_user);
		terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);

		// Setting text selector over textviews
		XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
		try {
			ColorStateList csl = ColorStateList.createFromXml(getResources(),
					xrp);

			login.setTextColor(csl);
			terms_conditions.setTextColor(csl);
		} catch (Exception e) {
		}
	}

	// Set Listeners
	private void setListeners() {
		signUpButton.setOnClickListener(this);
		login.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signUpBtn:

			// Call checkValidation method
			checkValidation();
			break;

		case R.id.already_user:

			// Replace login fragment
			new MainActivity().replaceLoginFragment();
			break;
		}

	}

	// Check Validation Method
	private void checkValidation() {

		// Get all edittext texts
		String getFullName = fullName.getText().toString();
		final String getEmailId = emailId.getText().toString();
		String getMobileNumber = mobileNumber.getText().toString();
		String getBirthdate = birthdate.getText().toString();
		String getPassword = password.getText().toString();
		String getConfirmPassword = confirmPassword.getText().toString();

		// Pattern match for email id
		Pattern p = Pattern.compile(Utils.regEx);
		Matcher m = p.matcher(getEmailId);

		// Check if all strings are null or not
		if (getFullName.equals("") || getFullName.length() == 0
				|| getEmailId.equals("") || getEmailId.length() == 0
				|| getMobileNumber.equals("") || getMobileNumber.length() == 0
				|| getBirthdate.equals("") || getBirthdate.length() == 0
				|| getPassword.equals("") || getPassword.length() == 0
				|| getConfirmPassword.equals("")
				|| getConfirmPassword.length() == 0)

			new CustomToast().Show_Toast(getActivity(), view,
					getResources().getString(R.string.AllFieldsRequired));

		// Check if email id valid or not
		else if (!m.find())
			new CustomToast().Show_Toast(getActivity(), view,
					getResources().getString(R.string.InvalidEmail));

		// Check if both password should be equal
		else if (!getConfirmPassword.equals(getPassword))
			new CustomToast().Show_Toast(getActivity(), view,
					getResources().getString(R.string.PasswordsDontMatch));

		// Make sure user should check Terms and Conditions checkbox
		else if (!terms_conditions.isChecked())
			new CustomToast().Show_Toast(getActivity(), view,
					getResources().getString(R.string.SelectTermsAndConditions));

		// Else do signup or do your stuff
		else
		{
			Register register = new Register(getFullName,getEmailId,getPassword,getMobileNumber,getBirthdate);
			Retrofit retrofit = APIClientRetrofit.getClient();
			APIinterfaceRetrofit _register = retrofit.create(APIinterfaceRetrofit.class);
			Call<String> registerCall = _register.Register(register);
			registerCall.enqueue(new Callback<String>() {
				@Override public void onResponse(Call<String> call, Response<String> response)
				{

					if(response.code()==200 || response.code()==204)
				{

					SharedPreferences
							settings = getContext().getSharedPreferences(Utils.Login_Preferences, Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = settings.edit();
					editor.putString("Email",getEmailId);
					editor.putInt("Type",0);
					editor.putInt("Id",Integer.parseInt(response.body()));
					editor.putInt("First_Login",1);
					editor.commit();

					Intent goToProfileEdit = new Intent(getActivity(), EditProfile.class);
					goToProfileEdit.putExtra("Id",response.body());
					goToProfileEdit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(goToProfileEdit);
				}
				else if(response.code()==302)
					{
						MDToast.makeText(getContext(),getResources().getString(R.string.UserAlreadyExists),4000,MDToast.TYPE_ERROR).show();
					}
				}

				@Override public void onFailure(Call<String> call, Throwable t)
        {
          MDToast.makeText(getContext(),getResources().getString(R.string.NetworkConnectionFailure),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

				}
			});
		}

	}
}
