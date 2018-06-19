package studymate.mstechnologies.com.studymateandroid.Models;

/**
 * Created by xboxp on 5/24/2018.
 */

public class Answer {
  private int id;
  private String text;
  private int questionId;
  private int userId;
  public Answer(int id, String text, int questionId, int userId) {
    super();
    this.id = id;
    this.text = text;
    this.questionId = questionId;
    this.userId = userId;
  }
  public Answer() {}
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getText() {
    return text;
  }
  public void setText(String text) {
    this.text = text;
  }
  public int getQuestionId() {
    return questionId;
  }
  public void setQuestionId(int questionId) {
    this.questionId = questionId;
  }
  public int getUserId() {
    return userId;
  }
  public void setUserId(int userId) {
    this.userId = userId;
  }



}