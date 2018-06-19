

package studymate.mstechnologies.com.studymateandroid.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by xboxp on 5/31/2018.
 */

public class EditProfile
{
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
  @SerializedName("imageBytes")
  private byte[] imageBytes;
  @SerializedName("QuestionNum")
  private int QuestionNum;
  @SerializedName("AnswerNum")
  private int AnswerNum;

  public EditProfile(){}

  public EditProfile(int id, String fullName, String email, String password, String phone,
      String birthdate, int majorId, int type, byte[] imageBytes, int questionNum, int answerNum) {
    this.id = id;
    this.fullName = fullName;
    this.email = email;
    this.password = password;
    this.phone = phone;
    this.birthdate = birthdate;
    this.majorId = majorId;
    this.type = type;
    this.imageBytes = imageBytes;
    QuestionNum = questionNum;
    AnswerNum = answerNum;
  }

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

  public byte[] getImageBytes() {
    return imageBytes;
  }

  public void setImageBytes(byte[] imageBytes) {
    this.imageBytes = imageBytes;
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

}
