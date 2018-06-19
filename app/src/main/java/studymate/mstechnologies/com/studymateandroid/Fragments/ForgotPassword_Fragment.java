package studymate.mstechnologies.com.studymateandroid.Fragments;

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.valdesekamdem.library.mdtoast.MDToast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import studymate.mstechnologies.com.studymateandroid.Activities.MainActivity;
import studymate.mstechnologies.com.studymateandroid.R;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIClientRetrofit;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIinterfaceRetrofit;
import studymate.mstechnologies.com.studymateandroid.Utils.CustomToast;
import studymate.mstechnologies.com.studymateandroid.Utils.Utils;

public class ForgotPassword_Fragment extends Fragment implements OnClickListener {
	private static View view;

	private static EditText emailId;
	private static TextView submit, back;
	private ProgressBar progressBar;
  Retrofit retrofit = APIClientRetrofit.getClient();
	private FragmentManager fragmentManager;
	public ForgotPassword_Fragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragmentManager = getActivity().getSupportFragmentManager();
		view = inflater.inflate(R.layout.forgotpassword_layout, container,
				false);
		initViews();
		setListeners();
		return view;
	}

	// Initialize the views
	private void initViews() {
		progressBar = (ProgressBar)view.findViewById(R.id.res_pass_spin_kit_pb);
		emailId = (EditText) view.findViewById(R.id.registered_emailid);
		submit = (TextView) view.findViewById(R.id.forgot_button);
		back = (TextView) view.findViewById(R.id.backToLoginBtn);

		// Setting text selector over textviews
		XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
		try {
			ColorStateList csl = ColorStateList.createFromXml(getResources(),
					xrp);

			back.setTextColor(csl);
			submit.setTextColor(csl);

		} catch (Exception e) {
		}

	}

	// Set Listeners over buttons
	private void setListeners() {
		back.setOnClickListener(this);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backToLoginBtn:

			// Replace Login Fragment on Back Presses
			new MainActivity().replaceLoginFragment();
			break;

		case R.id.forgot_button:

			// Call Submit button task
			submitButtonTask();
			break;

		}

	}

	private void submitButtonTask() {
		String getEmailId = emailId.getText().toString();

		// Pattern for email id validation
		Pattern p = Pattern.compile(Utils.regEx);

		// Match the pattern
		Matcher m = p.matcher(getEmailId);

		// First check if email id is not null else show error toast
		if (getEmailId.equals("") || getEmailId.length() == 0)

			new CustomToast().Show_Toast(getActivity(), view,
					"Please enter your Email Id.");

		// Check if email id is valid or not
		else if (!m.find())
			new CustomToast().Show_Toast(getActivity(), view,
					"Your Email Id is Invalid.");

		// Else submit email id and fetch passwod or do your stuff
		else
		{
						FoldingCube fc = new FoldingCube();
			progressBar.setIndeterminateDrawable(fc);
			progressBar.setVisibility(View.VISIBLE);
			APIinterfaceRetrofit forgotPassword = retrofit.create(APIinterfaceRetrofit.class);
			Call<String> forgotPasswordCall = forgotPassword.forgotPassword(emailId.getText().toString().trim());
			forgotPasswordCall.enqueue(new Callback<String>() {
				@Override public void onResponse(Call<String> call, Response<String> response) {
					Toast.makeText(getContext(), response.code()+"", Toast.LENGTH_SHORT).show();

					Log.d("FORGOT PASSWORD CODE",response.code()+"");
					if (response.code() == 200)
					{
						progressBar.setVisibility(View.INVISIBLE);
						MDToast.makeText(getContext(),getResources().getString(R.string.emailSent),MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show();
						fragmentManager
								.beginTransaction()
								.setCustomAnimations(R.anim.left_enter, R.anim.right_out)
								.replace(R.id.frameContainer, new Login_Fragment(),
										Utils.Login_Fragment).commit();
					}

				}

				@Override public void onFailure(Call<String> call, Throwable t) {
          progressBar.setVisibility(View.INVISIBLE);
				}
			});

		}
	}
}