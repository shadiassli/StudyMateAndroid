package studymate.mstechnologies.com.studymateandroid.Models;

import java.util.Date;

/**
 * Created by xboxp on 3/6/2018.
 */

public class User
{
  private String fullName;
  private String email;
  private String password;
  private String phone;
  private Date birthdate;
  private int majorId;
  private int type;
  private byte[] imageFile;

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


  public Date getBirthdate() {
    return birthdate;
  }


  public void setBirthdate(Date birthdate) {
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


  public  byte[] getImageFile() {
    return this.imageFile;
  }


  public void setImageFile(byte[] imageFile) {
    this.imageFile = imageFile;
  }


  public User(String fullName, String email, String password, String phone, Date birthdate, int majorId, int type,
      byte[] imageFile) {
    super();
    this.fullName = fullName;
    this.email = email;
    this.password = password;
    this.phone = phone;
    this.birthdate = birthdate;
    this.majorId = majorId;
    this.type = type;
    this.imageFile = imageFile;
  }






  public User() {}

}
