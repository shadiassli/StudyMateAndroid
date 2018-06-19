package studymate.mstechnologies.com.studymateandroid.Models;

/**
 * Created by xboxp on 5/24/2018.
 */

public class UserAnswer {

  Users user;
  Answer answer;

  public UserAnswer(Users user, Answer answer) {
    super();
    this.user = user;
    this.answer = answer;
  }

  public Users getUser() {
    return user;
  }

  public Answer getAnswer() {
    return answer;
  }

  public UserAnswer() {}

}