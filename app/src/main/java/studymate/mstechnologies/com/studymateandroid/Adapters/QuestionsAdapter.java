package studymate.mstechnologies.com.studymateandroid.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.valdesekamdem.library.mdtoast.MDToast;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Text;
import studymate.mstechnologies.com.studymateandroid.Activities.EditQuestionActivity;
import studymate.mstechnologies.com.studymateandroid.Activities.ViewQuestionActivity;
import studymate.mstechnologies.com.studymateandroid.Models.Answer;
import studymate.mstechnologies.com.studymateandroid.Models.MainQuestions;
import studymate.mstechnologies.com.studymateandroid.Models.Question;
import studymate.mstechnologies.com.studymateandroid.Models.Questions;
import studymate.mstechnologies.com.studymateandroid.R;
import studymate.mstechnologies.com.studymateandroid.Retrofit.BaseUrl;
import studymate.mstechnologies.com.studymateandroid.Retrofit.Requests;
import studymate.mstechnologies.com.studymateandroid.Utils.Utils;

/**
 * Created by xboxp on 3/19/2018.
 */

public class QuestionsAdapter extends RecyclerView.Adapter<QuestionsAdapter.QuestionViewHolder>
{

  static ArrayList<MainQuestions> questionsList;
  public static Context mContext;

  public Context getmContext() {
    return mContext;
  }

  public QuestionsAdapter(Context mContext) {
    this.mContext = mContext;
  }

  public QuestionsAdapter(ArrayList<MainQuestions> questions , Context context){
    this.questionsList = questions;
    this.mContext = context;
  }

  @NonNull @Override
  public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_home, parent, false);
    QuestionViewHolder questionViewHolder = new QuestionViewHolder(v);
    return questionViewHolder;
  }

  @Override public void onBindViewHolder(@NonNull final QuestionViewHolder holder, final int position) {
    holder.userName.setText(questionsList.get(position).getUser().getFullName()+" "+mContext.getResources().getString(R.string.asked));
    holder.questionTxt.setText(questionsList.get(position).getQuestion().getText());
    holder.ansCount.setText(questionsList.get(position).getQuestion().getNoOfAnswers()+" "+mContext.getResources().getString(R.string.answer));
    Picasso.get().load(BaseUrl.IMAGES_URL+questionsList.get(position).getUser().getId()+".jpg").placeholder(R.drawable.ic_default_avatar)
        .error(R.drawable.ic_default_avatar).into(holder.userPhoto);
        holder.sendBtn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if(!holder.ansET.getText().toString().isEmpty()){
        Answer answer = new Answer();
        answer.setText(holder.ansET.getText().toString().trim());
        answer.setQuestionId(questionsList.get(position).getQuestion().getId());
        SharedPreferences prefs= mContext.getSharedPreferences(Utils.Login_Preferences,Context.MODE_PRIVATE);
        int id=prefs.getInt("Id",-1);
        answer.setUserId(id);
        Requests requests = new Requests(mContext);
        requests.addAnswer(answer);
        holder.ansET.setText("");
        }
        else {
          holder.ansET.setError(mContext.getResources().getString(R.string.type_down_an_answer));
        }

        //answer.setQuestionId();
      }
    });
    holder.questionTxt.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Intent intent = new Intent(mContext, ViewQuestionActivity.class);
        intent.putExtra("Question_Id", questionsList.get(position).getQuestion().getId());
        mContext.startActivity(intent);

      }
    });
    holder.menuButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        //MENU CLICK LISTENER
        if(questionsList.get(position).getQuestion().getUserId()==mContext.getSharedPreferences(Utils.Login_Preferences,Context.MODE_PRIVATE).getInt("Id",-1))
        {

          PopupMenu menu = new PopupMenu(mContext,holder.menuButton);
          menu.getMenuInflater().inflate(R.menu.question_owner_options_menu,menu.getMenu());
          menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override public boolean onMenuItemClick(MenuItem item) {
              if (item.getTitle().equals(mContext.getResources().getString(R.string.edit)))
              {
                Intent intent = new Intent(mContext, EditQuestionActivity.class);
                intent.putExtra("Question_Id", questionsList.get(position).getQuestion().getId());
                intent.putExtra("Question_Text",questionsList.get(position).getQuestion().getText());
                mContext.startActivity(intent);

              }
              else if (item.getTitle().equals(mContext.getResources().getString(R.string.delete)))
              {
               Requests requests = new Requests(mContext);
               if(requests.deleteQuestion(questionsList.get(position).getQuestion().getId()))
               {
                 MDToast.makeText(mContext,mContext.getResources().getString(R.string.questionDeleted),
                     Toast.LENGTH_LONG,MDToast.TYPE_SUCCESS).show();
               }


              }
              return true;
            }
          });



          menu.show();
        }
      }
    });

  }

  @Override public int getItemCount() {


 return questionsList.size();
  }

  @Override
  public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
  }

  public static class QuestionViewHolder extends RecyclerView.ViewHolder {


    CircleImageView userPhoto;
    ImageView menuButton;
    TextView questionTxt,userName,ansCount;
    EditText ansET;
    Button sendBtn;

   public QuestionViewHolder(View itemView) {
      super(itemView);
      menuButton =(ImageView)itemView.findViewById(R.id.card1_menu);
      userPhoto = (CircleImageView)itemView.findViewById(R.id.card1_image);
      questionTxt = (TextView)itemView.findViewById(R.id.card1_question_txt);
      userName = (TextView)itemView.findViewById(R.id.card1_user_name);
      ansCount = (TextView)itemView.findViewById(R.id.card1_ans_count_tv);
      ansET = (EditText)itemView.findViewById(R.id.card1_ans_et);
      sendBtn = (Button)itemView.findViewById(R.id.card1_post_ans_btn);


    }


    }
  }

