package studymate.mstechnologies.com.studymateandroid.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import studymate.mstechnologies.com.studymateandroid.Adapters.AnswersAdapter;
import studymate.mstechnologies.com.studymateandroid.Models.Answer;
import studymate.mstechnologies.com.studymateandroid.Models.MainQuestions;
import studymate.mstechnologies.com.studymateandroid.Models.UserAnswer;
import studymate.mstechnologies.com.studymateandroid.R;
import studymate.mstechnologies.com.studymateandroid.Recycler_Tools.DividerItemDecoration;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIClientRetrofit;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIinterfaceRetrofit;
import studymate.mstechnologies.com.studymateandroid.Retrofit.BaseUrl;
import studymate.mstechnologies.com.studymateandroid.Retrofit.Requests;
import studymate.mstechnologies.com.studymateandroid.Utils.Utils;

public class ViewQuestionActivity extends AppCompatActivity {

  CircleImageView userImage;
  TextView userName, questionTxt;
  RecyclerView answersRecycler;
  Button sendBtn;
  EditText ansET;
  int questionId;
  Retrofit retrofit = APIClientRetrofit.getClient();
  public static MainQuestions mainQuestions = new MainQuestions();
  ArrayList<UserAnswer> answersList = new ArrayList<>();



  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_question);
    getIncomingIntent();
    WidgetsInit();
    ClickListeners();

  }

  private void ClickListeners()
  {
    sendBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if(!ansET.getText().toString().isEmpty()){
          Answer answer = new Answer();
          answer.setText(ansET.getText().toString().trim());
          answer.setQuestionId(questionId);
          SharedPreferences prefs= getSharedPreferences(Utils.Login_Preferences,Context.MODE_PRIVATE);
          int id=prefs.getInt("Id",-1);
          answer.setUserId(id);
          Requests requests = new Requests(ViewQuestionActivity.this);
          requests.addAnswer(answer);
          ansET.setText("");
          APIinterfaceRetrofit loadAnswers = retrofit.create(APIinterfaceRetrofit.class);
          Call<ArrayList<UserAnswer>> loadAnswersCall = loadAnswers.getAnswers(questionId);
          loadAnswersCall.enqueue(new Callback<ArrayList<UserAnswer>>() {
            @Override public void onResponse(Call<ArrayList<UserAnswer>> call,
                Response<ArrayList<UserAnswer>> response) {
              if(response.code()==200)
              {
                answersList=response.body();
                AnswersAdapter answersAdapter = new AnswersAdapter(answersList,ViewQuestionActivity.this);
                answersRecycler.setAdapter(answersAdapter);
              }
              else
              {
                MDToast.makeText(ViewQuestionActivity.this,getResources().getString(R.string.SomethingWentWrong),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
              }
            }

            @Override public void onFailure(Call<ArrayList<UserAnswer>> call, Throwable t) {
              MDToast.makeText(ViewQuestionActivity.this,getResources().getString(R.string.NetworkConnectionFailure),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

            }
          });
        }
        else {
          ansET.setError(getResources().getString(R.string.type_down_an_answer));
        }
      }
    });
  }

  private void WidgetsInit()
  {
    //Widgets
    userImage=(CircleImageView)findViewById(R.id.vq_user_image);
    userName =(TextView) findViewById(R.id.vq_user_name);
    questionTxt = (TextView)findViewById(R.id.vq_question_txt);
    answersRecycler = (RecyclerView)findViewById(R.id.vq_rv);
    sendBtn = (Button)findViewById(R.id.vq_ans_btn);
    ansET = (EditText)findViewById(R.id.vq_ans_et);

    LinearLayoutManager layoutManager = new LinearLayoutManager(this);
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    answersRecycler.setLayoutManager(layoutManager);
    Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.bg_gradient_two);
    RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(dividerDrawable);
    answersRecycler.addItemDecoration(dividerItemDecoration);
    //LOAD QUESTION CALL
    APIinterfaceRetrofit loadQuestion = retrofit.create(APIinterfaceRetrofit.class);
    Call<MainQuestions> loadQuestionCall = loadQuestion.getQuestion(questionId);
    loadQuestionCall.enqueue(new Callback<MainQuestions>() {
      @Override public void onResponse(Call<MainQuestions> call, Response<MainQuestions> response) {
         if(response.code()==200)
        {
          mainQuestions= response.body();
          userName.setText(mainQuestions.getUser().getFullName());
          questionTxt.setText(mainQuestions.getQuestion().getText());
        }
        else
          MDToast.makeText(ViewQuestionActivity.this,"LOAD QUESTON REQUEST CODE + " +response.code(),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
      }

      @Override public void onFailure(Call<MainQuestions> call, Throwable t) {
        MDToast.makeText(ViewQuestionActivity.this,"LOAD QUESTON REQUEST Failure + " +t.getMessage(),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

      }
    });

      //LOAD ANSWERS CALL
    APIinterfaceRetrofit loadAnswers = retrofit.create(APIinterfaceRetrofit.class);
    Call<ArrayList<UserAnswer>> loadAnswersCall = loadAnswers.getAnswers(questionId);
    loadAnswersCall.enqueue(new Callback<ArrayList<UserAnswer>>() {
      @Override public void onResponse(Call<ArrayList<UserAnswer>> call,
          Response<ArrayList<UserAnswer>> response) {
        if(response.code()==200)
        {
          answersList=response.body();
          AnswersAdapter answersAdapter = new AnswersAdapter(answersList,ViewQuestionActivity.this);
          answersRecycler.setAdapter(answersAdapter);
        }
        else
        {
          MDToast.makeText(ViewQuestionActivity.this,getResources().getString(R.string.SomethingWentWrong),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();
        }
      }

      @Override public void onFailure(Call<ArrayList<UserAnswer>> call, Throwable t) {
        MDToast.makeText(ViewQuestionActivity.this,getResources().getString(R.string.NetworkConnectionFailure),MDToast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

      }
    });



  }

  private void getIncomingIntent(){
    if (getIntent().hasExtra("Question_Id"))
    {
      questionId = getIntent().getIntExtra("Question_Id",0);
    }
}


}

