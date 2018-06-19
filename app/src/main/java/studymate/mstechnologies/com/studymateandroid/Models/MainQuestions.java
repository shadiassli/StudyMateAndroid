package studymate.mstechnologies.com.studymateandroid.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by xboxp on 5/23/2018.
 */

public class MainQuestions {
  @SerializedName("question")
  @Expose private Questions question;
  @SerializedName("user")
  @Expose private Users user;

  public MainQuestions(Questions question, Users user) {
    super();
    this.question = question;
    this.user = user;
  }

  public Questions getQuestion() {
    return question;
  }

  public Users getUser() {
    return user;
  }

  public MainQuestions() {}
}
