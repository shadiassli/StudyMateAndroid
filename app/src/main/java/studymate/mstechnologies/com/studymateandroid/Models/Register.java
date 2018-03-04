package studymate.mstechnologies.com.studymateandroid.Models;

/**
 * Created by xboxp on 3/4/2018.
 */

public class Register
{
  private String fullName;
  private String email;
  private String password;
  private String phone;
  private String birthdate;

  public Register(String fullName, String email, String password, String phone, String birthdate) {
    super();
    this.fullName = fullName;
    this.email = email;
    this.password = password;
    this.phone = phone;
    this.birthdate = birthdate;
  }

  public Register() {}



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

}
