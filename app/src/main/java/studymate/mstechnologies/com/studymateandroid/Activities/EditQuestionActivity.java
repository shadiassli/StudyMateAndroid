/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package studymate.mstechnologies.com.studymateandroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.valdesekamdem.library.mdtoast.MDToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import studymate.mstechnologies.com.studymateandroid.Models.Questions;
import studymate.mstechnologies.com.studymateandroid.R;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIClientRetrofit;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIinterfaceRetrofit;
import studymate.mstechnologies.com.studymateandroid.Retrofit.Requests;

public class EditQuestionActivity extends AppCompatActivity {

  Intent intent;
  Button post;
  EditText questionText;
  boolean res = false;
  Retrofit retrofit = APIClientRetrofit.getClient();
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_question);
    intent = getIntent();
    WidgetsInit();
    ClickListener();
  }

  private void ClickListener()
  {
    post.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if(!questionText.getText().toString().isEmpty()||questionText.getText().toString().length()<10) {
          Questions question = new Questions();
          question.setId(intent.getIntExtra("Question_Id", -1));
          question.setText(questionText.getText().toString());
          Requests requests = new Requests(EditQuestionActivity.this);
          if(editQuestion(question))
          {
            MDToast.makeText(getApplicationContext(),getResources().getString(R.string.questionEditted),
                Toast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show();
            finish();
          }
          else
            MDToast.makeText(getApplicationContext(),getResources().getString(R.string.SomethingWentWrong),
                Toast.LENGTH_LONG,MDToast.TYPE_ERROR).show();

        }
        else questionText.setError(getResources().getString(R.string.question_lenght_error));
      }
    });
  }

  private void WidgetsInit()
  {
    post = (Button)findViewById(R.id.edit_question_Post_BTN);
    questionText = (EditText)findViewById(R.id.edit_question_text);
    questionText.setText(intent.getStringExtra("Question_Text"));
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
}
