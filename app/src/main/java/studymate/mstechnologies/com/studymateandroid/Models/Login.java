package studymate.mstechnologies.com.studymateandroid.Models;

/**
 * Created by xboxp on 3/4/2018.
 */

public class Login
{
  private String user_name,user_password,id;

  public Login(String user_name, String user_password) {

    this.user_name = user_name;
    this.user_password = user_password;
  }

  public Login(String user_name, String user_password, String id) {
    this.user_name = user_name;
    this.user_password = user_password;
    this.id = id;
  }

  public Login() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUser_name() {
    return user_name;
  }

  public void setUser_name(String user_name) {
    this.user_name = user_name;
  }

  public String getUser_password() {
    return user_password;
  }

  public void setUser_password(String user_password) {
    this.user_password = user_password;
  }
}
