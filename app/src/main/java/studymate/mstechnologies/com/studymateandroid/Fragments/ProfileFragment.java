package studymate.mstechnologies.com.studymateandroid.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import studymate.mstechnologies.com.studymateandroid.Activities.EditProfileActivity;
import studymate.mstechnologies.com.studymateandroid.Models.Users;
import studymate.mstechnologies.com.studymateandroid.R;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIClientRetrofit;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIinterfaceRetrofit;
import studymate.mstechnologies.com.studymateandroid.Retrofit.BaseUrl;
import studymate.mstechnologies.com.studymateandroid.Utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

  Context ctx;
  View view;
  CircleImageView userImage;
  TextView answersCount,questionsCount,userName,emailAddress,phoneNumber;
  Button editBtn;
  Users user = new Users();
  Retrofit retrofit;
  int ID;
  public ProfileFragment() {
  ctx=getContext();
  retrofit = APIClientRetrofit.getClient();



  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view= inflater.inflate(R.layout.fragment_profile, container, false);
    return view;
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    WidgetsInit(view);
    getUserProfileCall();
    ClickListener();
  }

  private void ClickListener()
  {
    editBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent editProfileIntent = new Intent(getContext(), EditProfileActivity.class);
        startActivity(editProfileIntent);
      }
    });
  }

  private void WidgetsInit(View view)
  {
    userImage = (CircleImageView) view.findViewById(R.id.profile_user_image);
    userName=(TextView)view.findViewById(R.id.profile_user_name);
    answersCount = (TextView)view.findViewById(R.id.profile_ans_count);
    questionsCount = (TextView)view.findViewById(R.id.profile_ques_count);
    emailAddress = (TextView)view.findViewById(R.id.profile_email_address);
    phoneNumber = (TextView)view.findViewById(R.id.profile_phone_number);
    editBtn = (Button)view.findViewById(R.id.profile_edit_btn);
  }

  private void getUserProfileCall()
  {
    SharedPreferences prefs = getActivity().getSharedPreferences(Utils.Login_Preferences, Context.MODE_PRIVATE);
    final int userId = prefs.getInt("Id", -1);
    APIinterfaceRetrofit getUserProfile = retrofit.create(APIinterfaceRetrofit.class);
    Call<Users> getUserProfileCall = getUserProfile.getUser(userId);
    getUserProfileCall.enqueue(new Callback<Users>() {
      @Override public void onResponse(Call<Users> call, Response<Users> response) {
        if (response.code()==200) {
          user = response.body();
          Log.d("USER_ID_PROFILE",userId+"");
          Picasso.get().load(BaseUrl.IMAGES_URL+userId+".jpg").placeholder(R.drawable.ic_default_avatar)
              .error(R.drawable.ic_default_avatar).into(userImage);
          userName.setText(user.getFullName());
          answersCount.setText(user.getAnswerNum()+"");
          questionsCount.setText(user.getQuestionNum()+"");
          emailAddress.setText(user.getEmail());
          phoneNumber.setText(user.getPhone());
        }
        else MDToast.makeText(getActivity(),getActivity().getResources().getString(R.string.SomethingWentWrong),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

      }

      @Override public void onFailure(Call<Users> call, Throwable t) {
        MDToast.makeText(getActivity(),getActivity().getResources().getString(R.string.SomethingWentWrong),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
      }
    });

  }


}
