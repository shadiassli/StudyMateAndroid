package studymate.mstechnologies.com.studymateandroid.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.valdesekamdem.library.mdtoast.MDToast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import studymate.mstechnologies.com.studymateandroid.Models.Questions;
import studymate.mstechnologies.com.studymateandroid.R;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIClientRetrofit;
import studymate.mstechnologies.com.studymateandroid.Retrofit.APIinterfaceRetrofit;
import studymate.mstechnologies.com.studymateandroid.Retrofit.Requests;
import studymate.mstechnologies.com.studymateandroid.Utils.Utils;

public class askQuestionActivity extends AppCompatActivity {

  EditText questionET;
  Button finishBTN;
  Questions q= new Questions();

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ask_question);

    SharedPreferences prefs = this.getSharedPreferences(Utils.Login_Preferences, Context.MODE_PRIVATE);
    q.setUserId(prefs.getInt("Id", -1));
    WidgetsInit();
    ClickListener();
  }

  private void ClickListener() {
    finishBTN.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {

        if(questionET.getText().toString().length()<10)
        {
          questionET.setError(getString(R.string.question_lenght_error));
        }
        else
        {

          Date c = Calendar.getInstance().getTime();
          SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
          String formattedDate = df.format(c);
          q.setDate(formattedDate);
          q.setText(questionET.getText().toString().trim());
          Requests req = new Requests(askQuestionActivity.this);
          req.addQuestion(q);

        }

      }
    });
  }

  private void WidgetsInit()
  {
    questionET = (EditText)findViewById(R.id.etQuestiontxt);
    finishBTN = (Button)findViewById(R.id.addQuestionFinishBtn);

  }

}
