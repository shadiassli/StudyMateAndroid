package studymate.mstechnologies.com.studymateandroid.Fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.valdesekamdem.library.mdtoast.MDToast;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import studymate.mstechnologies.com.studymateandroid.Activities.CompleteProfileActivity;
import studymate.mstechnologies.com.studymateandroid.Activities.MainActivity;
import studymate.mstechnologies.com.studymateandroid.Models.Register;
import studymate.mstechnologies.com.studymateandroid.R;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIClientRetrofit;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIinterfaceRetrofit;
import studymate.mstechnologies.com.studymateandroid.Utils.Utils;

public class SignUp_Fragment extends Fragment implements OnClickListener,DatePickerDialog.OnDateSetListener {

  private int day,month,year , day_final,month_final,year_final;
  private String finalBdate;
	private  View view;
	private  EditText fullName, emailId, mobileNumber,
			password, confirmPassword;
	private   ImageButton birthdate;
	private  TextView login;
	private  Button signUpButton;
	private  CheckBox terms_conditions;
  private ProgressBar progressBar;
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
		 progressBar = (ProgressBar)view.findViewById(R.id.sign_up_spin_kit_pb);
		fullName = (EditText) view.findViewById(R.id.fullName);
		emailId = (EditText) view.findViewById(R.id.userEmailId);
		mobileNumber = (EditText) view.findViewById(R.id.mobileNumber);
		birthdate = (ImageButton) view.findViewById(R.id.birthdate);
		password = (EditText) view.findViewById(R.id.password);
		confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
		signUpButton = (Button) view.findViewById(R.id.signUpBtn);
		login = (TextView) view.findViewById(R.id.already_user);
		terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);

		// Setting text selector over textviews
		//XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
		try {
		//	ColorStateList csl = ColorStateList.createFromXml(getResources(),
			///		xrp);

		//	login.setTextColor(csl);
			//terms_conditions.setTextColor(csl);
		} catch (Exception e) {
		}
	}



	// Set Listeners
	private void setListeners() {
		signUpButton.setOnClickListener(this);
		login.setOnClickListener(this);
		birthdate.setOnClickListener(this);
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

      case R.id.birthdate: {
        Calendar cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog =
            new DatePickerDialog(getActivity(), SignUp_Fragment.this, year, month, day);
        datePickerDialog.show();
      }
      break;
		}

	}

	// Check Validation Method
	private void checkValidation() {

		// Get all edittext texts
		String getFullName = fullName.getText().toString();
		final String getEmailId = emailId.getText().toString();
		String getMobileNumber = mobileNumber.getText().toString();
		//String getBirthdate = birthdate.getText().toString();
		String getPassword = password.getText().toString();
		String getConfirmPassword = confirmPassword.getText().toString();

		// Pattern match for email id
		Pattern p = Pattern.compile(Utils.regEx);
		Matcher m = p.matcher(getEmailId);

		// Check if all strings are null or not
		if (getFullName.equals("") || getFullName.length() == 0
				|| getEmailId.equals("") || getEmailId.length() == 0
				|| getMobileNumber.equals("") || getMobileNumber.length() == 0
				|| finalBdate.equals("") || finalBdate.length() == 0
				|| getPassword.equals("") || getPassword.length() == 0
				|| getConfirmPassword.equals("")
				|| getConfirmPassword.length() == 0)

			MDToast.makeText(getContext(),getResources().getString(R.string.AllFieldsRequired),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();


			// Check if email id valid or not
		else if (!m.find())
			MDToast.makeText(getContext(),getResources().getString(R.string.InvalidEmail),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();


			// Check if both password should be equal
		else if (!getConfirmPassword.equals(getPassword))
			MDToast.makeText(getContext(),getResources().getString(R.string.PasswordsDontMatch),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();


			// Make sure user should check Terms and Conditions checkbox
		else if (!terms_conditions.isChecked())
			MDToast.makeText(getContext(),getResources().getString(R.string.SelectTermsAndConditions),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();


			// Else do signup or do your stuff
		else
		{

			FoldingCube fc = new FoldingCube();
			progressBar.setIndeterminateDrawable(fc);
			progressBar.setVisibility(View.VISIBLE);
			Register register = new Register(getFullName,getEmailId,getPassword,getMobileNumber,finalBdate);
			Log.d("REGISTER_RESPOSE__CODE","before register call");
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
					editor.putInt("Profile_Completed",0);
					editor.apply();
           progressBar.setVisibility(View.GONE);
         if(response.body()!=null) {
           Intent goToProfileEdit = new Intent(getActivity(), CompleteProfileActivity.class);
           goToProfileEdit.putExtra("Id", response.body());
           goToProfileEdit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
           startActivity(goToProfileEdit);
           getActivity().finish();
         }
			}
				else if(response.code()==302)
					{
						MDToast.makeText(getContext(),getResources().getString(R.string.UserAlreadyExists),4000,MDToast.TYPE_ERROR).show();
						progressBar.setVisibility(View.INVISIBLE);
					}
				}

				@Override public void onFailure(Call<String> call, Throwable t)
       {
       	progressBar.setVisibility(View.INVISIBLE);
         MDToast.makeText(getContext(),getResources().getString(R.string.NetworkConnectionFailure),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
      	}
			});
		}

	}

  @Override public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
  {
    year_final = year;
    month_final = month+1;
    day_final = dayOfMonth;
    finalBdate = day_final+"-"+month_final+"-"+year_final;
  }
}
