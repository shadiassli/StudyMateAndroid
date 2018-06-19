package studymate.mstechnologies.com.studymateandroid.Retrofit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import com.valdesekamdem.library.mdtoast.MDToast;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import studymate.mstechnologies.com.studymateandroid.Activities.CompleteProfileActivity;
import studymate.mstechnologies.com.studymateandroid.Activities.HomeActivity;
import studymate.mstechnologies.com.studymateandroid.Models.Answer;
import studymate.mstechnologies.com.studymateandroid.Models.Login;
import studymate.mstechnologies.com.studymateandroid.Models.MainQuestions;
import studymate.mstechnologies.com.studymateandroid.Models.Questions;
import studymate.mstechnologies.com.studymateandroid.Models.Register;
import studymate.mstechnologies.com.studymateandroid.R;
import studymate.mstechnologies.com.studymateandroid.Utils.Utils;

/**
 * Created by xboxp on 5/22/2018.
 */

public class Requests{

  ArrayList<String> majorsList = new ArrayList<>();
  ArrayList<String> departmentsList = new ArrayList<>();
  ArrayList<Answer> answersList;
  public Context ctx;
  ArrayList<MainQuestions> result = new ArrayList<>();
  Retrofit retrofit = APIClientRetrofit.getClient();
  MainQuestions questions;
  public static boolean res = false;
  public Requests(Context ctx) {
    this.ctx = ctx;
  }


  //INITIALIZE COMPONENTS

  public void AnswersINIT()
  {
    answersList = new ArrayList<>();
  }

   //Add Question Request
   public void addQuestion (Questions q)
   {

    APIinterfaceRetrofit askQuestion = retrofit.create(APIinterfaceRetrofit.class);
    Call<String> AskQCall = askQuestion.addQuestion(q);
    AskQCall.enqueue(new Callback<String>() {
      @Override public void onResponse(Call<String> call, Response<String> response) {
        if(response.code()==200)
        {
          if(!response.body().isEmpty())
          {
            MDToast.makeText(ctx,"Your question was successfully posted",MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show();
            Intent homeIntent = new Intent(ctx,HomeActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ctx.startActivity(homeIntent);


          }
        }
        else
        {
         MDToast.makeText(ctx,ctx.getResources().getString(R.string.NetworkConnectionFailure),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

        }
      }

      @Override public void onFailure(Call<String> call, Throwable t) {

      }
    });
  }
   //Get Questions Request
   public ArrayList<MainQuestions> getQuestions(int id)
   {

  APIinterfaceRetrofit getQues = retrofit.create(APIinterfaceRetrofit.class);
  Call<ArrayList<MainQuestions>> getQuesCall = getQues.getQuestions(id);
  getQuesCall.enqueue(new Callback<ArrayList<MainQuestions>>() {
    @Override public void onResponse(Call<ArrayList<MainQuestions>> call,
        Response<ArrayList<MainQuestions>> response) {
      if (response.code()==200||response.code()==204)
      {
      result = response.body();
      }
      else {
        MDToast.makeText(ctx,ctx.getResources().getString(R.string.SomethingWentWrong),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

      }
    }

    @Override public void onFailure(Call<ArrayList<MainQuestions>> call, Throwable t) {
      MDToast.makeText(ctx,ctx.getResources().getString(R.string.NetworkConnectionFailure),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
    }
  });
  return result;
}

   // Login Request
   public void loginRequest (final Login login)
   {

  final String getEmailId = login.getEmail();
  APIinterfaceRetrofit _login = retrofit.create(APIinterfaceRetrofit.class);
  Call<Login> loginCall = _login.LoginUser(login);
  loginCall.enqueue(new Callback<Login>() {
    @Override public void onResponse(Call<Login> call, Response<Login> response)
    {

      if(response.code()==200 || response.code()==204)
      {
        Log.d("OnResponse : ","Success");
        MDToast.makeText(ctx,ctx.getResources().getString(R.string.HoldOn),MDToast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show();
        SharedPreferences
            settings = ctx.getSharedPreferences(Utils.Login_Preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Email", getEmailId);
        editor.putInt("Type",response.body().getType());
        editor.putInt("Profile_Completed",0);
        editor.putInt("Id",response.body().getId());

        editor.commit();

        int firstLogin = settings.getInt("First_Login",1);
        if(firstLogin == 1)
        {
          Intent goToProfileEdit = new Intent(ctx, CompleteProfileActivity.class);
          goToProfileEdit.putExtra("Id",response.body().getId());
          goToProfileEdit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
          ctx.startActivity(goToProfileEdit);
          ((Activity) ctx).finish();


        }

      }
      else if (response.code()==404)
      {
         MDToast.makeText(ctx,ctx.getResources().getString(R.string.NoSuchUser),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR)
             .show();

      }

    }

    @Override public void onFailure(Call<Login> call, Throwable t)
    {

      MDToast.makeText(ctx,ctx.getResources().getString(R.string.NetworkConnectionFailure),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
    }
  });


}

   // Register Request
   public  void registerRequest(Register register)
   {
  final  String getEmailId = register.getEmail();
  APIinterfaceRetrofit _register = retrofit.create(APIinterfaceRetrofit.class);
  Call<String> registerCall = _register.Register(register);
  registerCall.enqueue(new Callback<String>() {
    @Override public void onResponse(Call<String> call, Response<String> response)
    {

      if(response.code()==200 || response.code()==204)
      {

        SharedPreferences
            settings = ctx.getSharedPreferences(Utils.Login_Preferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Email",getEmailId);
        editor.putInt("Type",0);
        editor.putInt("Id",Integer.parseInt(response.body()));
        editor.putInt("First_Login",1);
        editor.putInt("Profile_Completed",0);
        editor.apply();

        if(response.body()!=null) {
          Intent goToProfileEdit = new Intent(ctx, CompleteProfileActivity.class);
          goToProfileEdit.putExtra("Id", response.body());
          goToProfileEdit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
          ctx.startActivity(goToProfileEdit);
          ((Activity) ctx).finish();
        }
      }
      else if(response.code()==302)
      {

        MDToast.makeText(ctx,ctx.getResources().getString(R.string.UserAlreadyExists),4000,MDToast.TYPE_ERROR).show();
      }
    }

    @Override public void onFailure(Call<String> call, Throwable t)
    {

      MDToast.makeText(ctx,ctx.getResources().getString(R.string.NetworkConnectionFailure),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

    }
  });

}
   // Update Profile Request
   public void updateProfile (
       studymate.mstechnologies.com.studymateandroid.Models.CompleteProfile cp)
   {
  final APIinterfaceRetrofit updateProfile = retrofit.create(APIinterfaceRetrofit.class);
  Call<String> updateProfileCall = updateProfile.updateProfile(cp);
  updateProfileCall.enqueue(new Callback<String>() {
    @Override public void onResponse(Call<String> call, Response<String> response) {

      if (response.code()==200)
      {


        ctx.getSharedPreferences(Utils.Login_Preferences, Context.MODE_PRIVATE)
            .edit()
            .putInt("First_Login",0)
            .apply();
        Intent homeIntent = new Intent(ctx,HomeActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ctx.startActivity(homeIntent);
       // ((Activity)ctx).finish();
      }

    }

    @Override public void onFailure(Call<String> call, Throwable t) {
      Log.e("Edit Profile : " , t.getMessage());
    }
  });
}

   // GET Majors Request
   public ArrayList<String> loadMajors(String selectedDep)
  {

    final APIinterfaceRetrofit  getMajors = retrofit.create(APIinterfaceRetrofit.class);
    Call<ArrayList<String>> getMajorsCall = getMajors.getMajors(selectedDep);
    getMajorsCall.enqueue(new Callback<ArrayList<String>>() {
      @Override
      public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
        if (response.code()==200)
        {
          majorsList=response.body();


        }
      }

      @Override public void onFailure(Call<ArrayList<String>> call, Throwable t)
      {
        MDToast.makeText(ctx,ctx.getResources().getString(R.string.SomethingWentWrong),
            Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
      }
    });
    return majorsList;
  }

   //GET Departments Request
   public ArrayList<String> loadDepartments()
   {
    final  APIinterfaceRetrofit getDepartments = retrofit.create(APIinterfaceRetrofit.class);
    Call<ArrayList<String>> getDepartmentsCall = getDepartments.getDepartments();
    getDepartmentsCall.enqueue(new Callback<ArrayList<String>>() {
      @Override
      public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
        if (response.code()==200)
        {
          departmentsList = response.body();

        }
      }

      @Override public void onFailure(Call<ArrayList<String>> call, Throwable t) {
        MDToast.makeText(ctx,ctx.getResources().getString(R.string.SomethingWentWrong),Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
      }
    });
    return  departmentsList;
  }

   //ADD ANSWER REQUEST
  public void addAnswer(Answer ans)
  {
    final  APIinterfaceRetrofit addAnswer = retrofit.create(APIinterfaceRetrofit.class);
    Call<String> addAnswerCall = addAnswer.addAnswer(ans);
    addAnswerCall.enqueue(new Callback<String>() {
      @Override public void onResponse(Call<String> call, Response<String> response) {
        if (response.code()==200) {
          MDToast.makeText(ctx, ctx.getResources().getString(R.string.ans_added), MDToast.LENGTH_LONG,
              MDToast.TYPE_SUCCESS).show();
        }
        else
          MDToast.makeText(ctx,ctx.getResources().getString(R.string.SomethingWentWrong),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

      }

      @Override public void onFailure(Call<String> call, Throwable t) {
        MDToast.makeText(ctx,ctx.getResources().getString(R.string.SomethingWentWrong),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

      }
    });
  }

  //GET QUESTION REQUEST
  public MainQuestions loadQuestion (int id)
  {
    questions = new MainQuestions();
    APIinterfaceRetrofit getQuestion = retrofit.create(APIinterfaceRetrofit.class);
    Call<MainQuestions> getQuestionCall = getQuestion.getQuestion(id);
    getQuestionCall.enqueue(new Callback<MainQuestions>() {
      @Override public void onResponse(Call<MainQuestions> call, Response<MainQuestions> response) {
        Toast.makeText(ctx, "INSIDE LOAD QUESTION REQUEST , WITH RESPONSE CODE = " + response.code(), Toast.LENGTH_SHORT).show();
        if (response.code()==200)
        {

          questions=response.body();

        }
        else questions=null;

      }

      @Override public void onFailure(Call<MainQuestions> call, Throwable t) {

      }
    });
    return  questions;
  }

  public Boolean editQuestion(Questions q)
  {
    APIinterfaceRetrofit editQuestion = retrofit.create(APIinterfaceRetrofit.class);
    Call<Void> editQuestionCall = editQuestion.updateQuestion(q);
    editQuestionCall.enqueue(new Callback<Void>() {
      @Override public void onResponse(Call<Void> call, Response<Void> response) {
        if(response.code()==200||response.code()==204)
        {
          res = true;
        }
        else
        {
          res = false;
        }
      }

      @Override public void onFailure(Call<Void> call, Throwable t) {
        res =false;
      }
    });
    return res;
  }
  public Boolean deleteQuestion(int id)
  {
    res = false;
    APIinterfaceRetrofit deleteQuestion = retrofit.create(APIinterfaceRetrofit.class);
    Call<Void> deleteQuestionCall = deleteQuestion.deleteQuestion(id);
   deleteQuestionCall.enqueue(new Callback<Void>() {
     @Override public void onResponse(Call<Void> call, Response<Void> response) {
       if(response.code()==200|| response.code()==204)
       {
         res = true;
       }
       else
         res = false;
     }

     @Override public void onFailure(Call<Void> call, Throwable t) {
      res = false;
     }
   });
    return res;
  }
  //GET ANSWERS REQUEST
 /* public ArrayList<Answer> loadAnswers(int id)
  {
    AnswersINIT();
    APIinterfaceRetrofit getAnswers = retrofit.create(APIinterfaceRetrofit.class);
    Call<ArrayList<UserAnswer>> getAnswersCall = getAnswers.getAnswers(id);
    getAnswersCall.enqueue(new Callback<ArrayList<Answer>>() {
      @Override
      public void onResponse(Call<ArrayList<Answer>> call, Response<ArrayList<Answer>> response) {
        if (response.code()==200)
        {
          answersList=response.body();

        }
        else
        {
          answersList=null;
        }
      }

      @Override public void onFailure(Call<ArrayList<Answer>> call, Throwable t) {
        answersList=null;
      }
    });
    return answersList;
  }*/
}
