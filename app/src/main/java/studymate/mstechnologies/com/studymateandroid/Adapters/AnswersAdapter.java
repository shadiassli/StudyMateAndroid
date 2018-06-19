package studymate.mstechnologies.com.studymateandroid.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.ArrayList;
import studymate.mstechnologies.com.studymateandroid.Models.Answer;
import studymate.mstechnologies.com.studymateandroid.Models.User;
import studymate.mstechnologies.com.studymateandroid.Models.UserAnswer;
import studymate.mstechnologies.com.studymateandroid.R;

/**
 * Created by xboxp on 5/24/2018.
 */

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.AnswersViewHolder>
{
  ArrayList<UserAnswer> answersList;
  public Context mContext;

  public AnswersAdapter(ArrayList<UserAnswer> answersList, Context mContext) {
    this.answersList = answersList;
    this.mContext = mContext;
  }

  public AnswersAdapter(Context mContext) {
    this.mContext = mContext;
  }

  @NonNull @Override
  public AnswersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_card,parent,false);
    AnswersViewHolder answersViewHolder = new AnswersViewHolder(v);
    return answersViewHolder;
  }

  @Override public void onBindViewHolder(@NonNull AnswersViewHolder holder, int position) {
    holder.answerText.setText(answersList.get(position).getAnswer().getText());
    holder.userName.setText(answersList.get(position).getUser().getFullName());

    holder.itemView.setTag(answersList.get(position));

  }
  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public int getItemViewType(int position) {
    return position;
  }
  @Override public int getItemCount() {
    return answersList.size();
  }

  @Override public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
    super.onAttachedToRecyclerView(recyclerView);
  }

  public static class AnswersViewHolder extends RecyclerView.ViewHolder {

    CircleImageView userImage;
    TextView userName,answerText;

    public AnswersViewHolder(View itemView) {
      super(itemView);
      userImage = (CircleImageView)itemView.findViewById(R.id.ans_card_image);
      userName = (TextView)itemView.findViewById(R.id.ans_card_user_name);
      answerText = (TextView)itemView.findViewById(R.id.ans_card_ans_txt);
    }
  }
}
