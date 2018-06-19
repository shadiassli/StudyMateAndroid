package studymate.mstechnologies.com.studymateandroid.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by xboxp on 5/23/2018.
 */

public class Users {
  private int id;
  @SerializedName("fullName")
  private String fullName;
  @SerializedName("email")
  private String email;
  @SerializedName("password")
  private String password;
  @SerializedName("phone")
  private String phone;
  @SerializedName("birthdate")
  private String birthdate;
  @SerializedName("majorId")
  private int majorId;
  @SerializedName("type")
  private int type;
  @SerializedName("imagePath")
  private String imagePath;
  @SerializedName("QuestionNum")
  private int QuestionNum;
  @SerializedName("AnswerNum")
  private int AnswerNum;


  public int getId() {
    return id;
  }


  public void setId(int id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }


  public void setFullName(String fullName) {
    this.fullName = fullName;
  }


  public String getEmail() {
    return email;
  }


  public void setEmail(String email) {
    this.email = email;
  }


  public String getPassword() {
    return password;
  }


  public void setPassword(String password) {
    this.password = password;
  }


  public String getPhone() {
    return phone;
  }


  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getBirthdate() {
    return birthdate;
  }


  public void setBirthdate(String birthdate) {
    this.birthdate = birthdate;
  }


  public int getMajorId() {
    return majorId;
  }


  public void setMajorId(int majorId) {
    this.majorId = majorId;
  }


  public int getType() {
    return type;
  }


  public void setType(int type) {
    this.type = type;
  }



  public String getImagePath() {
    return imagePath;
  }


  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }


  public int getQuestionNum() {
    return QuestionNum;
  }


  public void setQuestionNum(int questionNum) {
    QuestionNum = questionNum;
  }


  public int getAnswerNum() {
    return AnswerNum;
  }


  public void setAnswerNum(int answerNum) {
    AnswerNum = answerNum;
  }


  public Users(String fullName, String email, String password, String phone, String birthdate, int majorId, int type,
      String imageFile) {
    super();
    this.fullName = fullName;
    this.email = email;
    this.password = password;
    this.phone = phone;
    this.birthdate = birthdate;
    this.majorId = majorId;
    this.type = type;
    this.imagePath = imageFile;
  }






  public Users() {}


}