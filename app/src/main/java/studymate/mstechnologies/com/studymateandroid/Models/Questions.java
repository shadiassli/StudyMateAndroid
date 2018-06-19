package studymate.mstechnologies.com.studymateandroid.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by xboxp on 5/21/2018.
 */

public class Questions {
  @SerializedName("id")
  @Expose private int id;
  @SerializedName("userId")
  @Expose private int userId;
  @SerializedName("text")
  @Expose private String text;
  @SerializedName("date")
  @Expose private String date;
  @SerializedName("noOfAnswers")
  @Expose private int noOfAnswers;

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

  public int getUserId() {
    return userId;
  }
  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getDate() {
    return date;
  }
  public void setDate(String date) {
    this.date = date;
  }


  public int getNoOfAnswers() {
    return noOfAnswers;
  }
  public void setNoOfAnswers(int noOfAnswers) {
    this.noOfAnswers = noOfAnswers;
  }



  public Questions(int id, int userId, String text, String date, int noOfAnswers) {
    super();
    this.id = id;
    this.userId = userId;
    this.text = text;
    this.date = date;
    this.noOfAnswers = noOfAnswers;
  }

  public Questions() {};

}